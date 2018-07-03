package com.cicinnus.cateye.base;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.baidu.mapapi.search.geocode.GeoCoder;

public abstract class BasicLocationFragment extends MyBaseFragment{
	private static final  String TAG = "BasicLocationFragment";
	
	private LocationClient mLocClient ;
	
	private MyLocationListenner myListener = new MyLocationListenner();
	private OnLocationResultListener myonLocationResult = null;
	boolean isRequest = false;//是否手动触发请求定位
	boolean isFirstLoc = true;//是否首次定位
	private String addressInfo = "";
	
	private GeoCoder mSearch;
	
	private ProgressDialog progressbar = null;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prepareLocation();
	}
	
	@Override
	public void onResume() {
		if (mLocClient != null && !mLocClient.isStarted()) {
			mLocClient.start();
		}
		super.onResume();
	}
	
	@Override
	public void onPause() {
		mLocClient.stop();
		super.onPause();
	}
	
	@Override
	public void onDestroy() {
		//退出时销毁定位
       	mLocClient.stop();
        mSearch.destroy();
		super.onDestroy();
	}
	
	/**
     * 手动触发一次定位请求
     */
    public void requestLocClick(){
    	isRequest = true;
        mLocClient.requestLocation();
        Toast.makeText(getActivity(), "正在定位……", Toast.LENGTH_SHORT).show();
    }
	
	private void prepareLocation(){
        //定位初始化
        mLocClient = new LocationClient( getActivity() );
        mLocClient.registerLocationListener( myListener );
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setOpenGps(true);//打开gps
        option.setCoorType("bd09ll");     //设置坐标类型
        option.setScanSpan(5000);
        mLocClient.setLocOption(option);
		mLocClient.start();
        
		// 初始化搜索模块，注册事件监听
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
			@Override
			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
					Log.e(TAG,"抱歉，未能找到结果");
					showCustomToast("抱歉，未能找到结果");
				}else{
					if(myonLocationResult != null){
						myonLocationResult.onGetLocationAddress(result==null?"":result.getAddress());
					}
				}
			}
			@Override
			public void onGetGeoCodeResult(GeoCodeResult arg0) {
			}
		});
        
		if(getOpenShowProgress()){
			handler.sendEmptyMessage(Constants.SHOWPROGRESS);
		}else{
		}
	}
	
	/**
	 * 获取地理位置的时候是否需要提示ProgressDialog的提示框</br>
	 * 默认是false不提示ProgressDialog
	 * 
	 */
	protected boolean getOpenShowProgress(){
		return false ;
	}
	
	/**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
    	
        @Override
        public void onReceiveLocation(BDLocation location) {
			if (location == null )
				return;
			if (isRequest || isFirstLoc ) {
				Log.v(TAG,"onFirstGetLocationLatLon-------begin");
				isFirstLoc = false;
				if(myonLocationResult != null){
                	myonLocationResult.onFirstGetLocationLatLon(location);
                }
			}else{
				Log.v(TAG,"onGetLocationLatLon-------begin");
				if(myonLocationResult != null){
	            	myonLocationResult.onGetLocationLatLon(location);
	            }
			}
        	
            if(addressInfo==null || addressInfo.equals("")){
            	LatLng ptCenter = new LatLng(location.getLatitude(), location.getLongitude());
    			// 反Geo搜索
    			mSearch.reverseGeoCode(new ReverseGeoCodeOption()
    					.location(ptCenter));
            }
        }
        
        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
    
    
    /**
     * 设置定位回调的监听
     * @param myonLocationResult
     */
    protected void setOnLocationResultListener(OnLocationResultListener myonLocationResult){
		this.myonLocationResult = myonLocationResult;
	}
	
	/**
     * 继承BasicLocationActivity后要实现回调得实现此接口
     * @author lml
     *
     */
	public interface OnLocationResultListener{
		void onFirstGetLocationLatLon(BDLocation location);
		void onGetLocationLatLon(BDLocation location);
		void onGetLocationAddress(String address);
	}
	
}
