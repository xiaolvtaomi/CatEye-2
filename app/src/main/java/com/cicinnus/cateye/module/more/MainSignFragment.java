package com.cicinnus.cateye.module.more;

import com.cicinnus.cateye.view.CommonDialog;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.cicinnus.cateye.R;
import com.cicinnus.cateye.base.BasicLocationFragment;
import com.cicinnus.cateye.base.Constants;
import com.cicinnus.cateye.base.MainActivity;
import com.cicinnus.cateye.module.home.HomeFragment;
import com.cicinnus.cateye.module.more.bean.RecentSignin;
import com.cicinnus.cateye.module.more.bean.SignPoint;
import com.cicinnus.cateye.tools.GsonUtil;

@SuppressWarnings("deprecation")
public class MainSignFragment extends BasicLocationFragment implements
		BasicLocationFragment.OnLocationResultListener, OnMarkerClickListener, OnClickListener,
		OnDrawerOpenListener, OnDrawerCloseListener, MySlidingDrawer.OnMySlidingDrawerClick,
		MainActivity.OnKeyDownPressListener, OnItemClickListener {

	private Context context;
	private final String TAG = "MainSignFragment";
	private View rootView = null;
	private MapView mapview;
	private BaiduMap mBaiduMap;
	private LatLng myLoc;
	private String cur_address;// 真实的地址
	private MySlidingDrawer slidingDrawer;
	private GridView gvSigners;
	private TextView tv_signin;
	private CustomDigitalClock late_clock;
	private TextView tv_signers;
	private ImageView iv_signers_arrow;
	private ImageView iv_location_back ;
	private ArrayList<SignPoint> points;
	private SignPoint choicePoint;
	private double nearestDistance = 0 ;
	private SigninMemberAdapter sMemberAdapter;
	private ArrayList<RecentSignin> recentSignins = new ArrayList<RecentSignin>();
	private static final int ADD_SIGNIN_SUCCESS = 321;
	private static final int SHOW_CHOOSE_POINT = 322;
	private boolean isOtherPlace = false;// 是否异地
	private MainActivity mainActivity;
	BitmapDescriptor mIconMaker;
	private YDPopupWindows pop_ydreason;
    
    
    public static MainSignFragment newInstance() {
        return new MainSignFragment();
    }
    
    private AlertDialog choose_point_ad = null ;
	private ArrayList<SignPoint> availablePoints = null ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "onCreate ");
		super.onCreate(savedInstanceState);
		context = getActivity();
		mainActivity = (MainActivity)getActivity();
		mIconMaker = BitmapDescriptorFactory
		.fromResource(R.drawable.signin_paopao_b2);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v(TAG, "onCreateView ");
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_tab_signin, null);
			points = new ArrayList<>();
			
			mapview = (MapView) rootView.findViewById(R.id.mapview);
			mBaiduMap = mapview.getMap();
			/** 移除底部缩放按钮开始 ***/
			// int count = mapview.getChildCount();
			// for (int i = 0; i < count; i++) {
			// View child = mapview.getChildAt(i);
			// // 隐藏百度logo ZoomControl
			// // if (child instanceof ImageView || child instanceof
			// ZoomControls)
			// // {
			// if (child instanceof ZoomControls) {
			// child.setVisibility(View.INVISIBLE);
			// break;
			// }
			// }
			/** 移除底部缩放按钮结束 ***/
			MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
			// 开启定位图层
			mBaiduMap.setMyLocationEnabled(true);
			mBaiduMap.setMapStatus(msu);
			mBaiduMap.setOnMarkerClickListener(this);
			setOnLocationResultListener(this);
			late_clock = (CustomDigitalClock) rootView
					.findViewById(R.id.late_clock);
			tv_signers = (TextView) rootView.findViewById(R.id.tv_signers);
			tv_signin = (TextView) rootView.findViewById(R.id.tv_signin);
			iv_location_back = (ImageView) rootView.findViewById(R.id.iv_location_back);
			iv_signers_arrow = (ImageView) rootView
					.findViewById(R.id.iv_signers_arrow);
			initPannel();
//			initClockDefaultValue();
			initEvents();
		}
		// 缓存的rootView需要判断是否已经被加过parent，
		// 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;

	};

	public void initEvents() {
		tv_signin.setOnClickListener(this);
		tv_signin.setClickable(false);
		gvSigners.setOnItemClickListener(this);
		iv_location_back.setOnClickListener(this);
	}

	public void initPannel() {
		slidingDrawer = (MySlidingDrawer) rootView
				.findViewById(R.id.slidingDrawer_sign);
		slidingDrawer.setOnDrawerOpenListener(this);
		slidingDrawer.setOnDrawerCloseListener(this);
		slidingDrawer.setHandleId(R.id.ll_signers_container);
		slidingDrawer.setOnMySlidingDrawerClick(this);
		gvSigners = (GridView) rootView.findViewById(R.id.gv_sign);
		sMemberAdapter = new SigninMemberAdapter(context);
		sMemberAdapter.setData(recentSignins);
		gvSigners.setAdapter(sMemberAdapter);
	}

	/**
	 * 
	 * @author CBB 描述：初始话当前点的所有签到人
	 */
	public void initRecentSigner() {
		if (choicePoint == null) {
			return;
		}
		
//		ArrayList<RecentSignin> temp = GsonUtil
//				.getGson().fromJson(result.getData(),
//						dataType);
//		if (temp != null) {
//			if (recentSignins.size() > 0) {
//				recentSignins.clear();
//			}
//			recentSignins.addAll(temp);
//			msg.what = Common.REFRESH;
//			msg.obj = "查询成功";
//			handler.sendMessage(msg);
//		} else {
//			recentSignins.clear();
//			msg.what = Common.REFRESH;
//			msg.obj = "没有签到人";
//			handler.sendMessage(msg);
//		}
		
		
	}

	public void initClockDefaultValue() {
		String signtime = "08:30";
		int signTime = (8 * 60 + 30) * 60000;
		Calendar cal = Calendar.getInstance();
		int todayh = cal.get(Calendar.HOUR_OF_DAY);
		int todaym = cal.get(Calendar.MINUTE);
		int todays = cal.get(Calendar.SECOND);
//		int nowTime = todayh
		late_clock.setEndTime(System.currentTimeMillis() + 30 * 1000);
	}

	@Override
	public void onResume() {
		mapview.onResume();
		if (choicePoint != null) {
			initRecentSigner();
		}
		super.onResume();

	}

	@Override
	public void onPause() {
		mapview.onPause();
		super.onPause();
	}

	@Override
	public void onDestroy() {
		// 关闭定位图层
		if(mBaiduMap != null){
			mBaiduMap.setMyLocationEnabled(false);
		}
		if(mapview != null){
			mapview.onDestroy();
			mapview = null;
		}
		if(mIconMaker != null){
			mIconMaker.recycle();
		}
		super.onDestroy();
	}

	private InfoWindow mInfoWindow = null;

	@Override
	public boolean onMarkerClick(final Marker marker) {
		
//		if(marker != signermarker){
//			View view = LayoutInflater.from(context).inflate(
//					R.layout.item_signin_current_loc, null);
//			TextView tv_company = (TextView) view.findViewById(R.id.company_name);
//			TextView tv_company_address = (TextView) view
//					.findViewById(R.id.company_address);
//
//			OnInfoWindowClickListener listener = new OnInfoWindowClickListener() {
//				@Override
//				public void onInfoWindowClick() {
//					mBaiduMap.hideInfoWindow();
//				}
//			};
//			LatLng ll = marker.getPosition();
//			Point p = mBaiduMap.getProjection().toScreenLocation(ll);
//			Log.e(TAG, "--!" + p.x + " , " + p.y);
//			p.y -= 47;
//			LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
//
//			Bundle bundle = marker.getExtraInfo();
//			SignPoint signPoint = (SignPoint) bundle.getSerializable("info");
//			if (signPoint != null) {// 点击更换签到点
//				if (!choicePoint.getId().equals(signPoint.getId())) {
//					choicePoint = signPoint;
//					initRecentSigner();
//				}
//				signerLatLng = null ;
//				addInfosOverlay(signPoint, false);
//				double tempdis = DistanceUtil.getDistance(myLoc,
//						new LatLng(Double.parseDouble(signPoint.getLatitude()),
//								Double.parseDouble(signPoint.getLongitude())));
//				Logger.v("nearestDistance", tempdis + "");
//				if (tempdis > 1000) {
//					tv_signin.setText("异地");
//					isOtherPlace = true;
//				} else if (tempdis < 1000 && tempdis > 500) {
//					tv_signin.setText("距签到点" + (int) tempdis + "米");
//					isOtherPlace = true;
//				} else {
//					isOtherPlace = false;
//					tv_signin.setText("签到");
//				}
//				tv_company.setText(signPoint.getPointName());
//				tv_company_address.setText(signPoint.getAddressName());
//			}
//
//			mInfoWindow = new InfoWindow(view, llInfo, listener);
//			mBaiduMap.showInfoWindow(mInfoWindow);
//		}else{
//			signermarker.remove();
//			signerLatLng = null;
//		}
		
		
		return true;
	}

	@Override
	public void onFirstGetLocationLatLon(BDLocation location) {
		Log.v(TAG, "onFirstGetLocationLatLon-------");
		Log.v("location.getCityCode()", location.getCityCode() + "," + location.getCity() + "," + location.getProvince() + location.getDistrict() );
		MyLocationData locData = new MyLocationData.Builder()
				.accuracy(location.getRadius())
				// 此处设置开发者获取到的方向信息，顺时针0-360
				.direction(100).latitude(location.getLatitude())
				.longitude(location.getLongitude()).build();
		mBaiduMap.setMyLocationData(locData);

		myLoc = new LatLng(location.getLatitude(), location.getLongitude());
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(myLoc);
		mBaiduMap.animateMapStatus(u);
		refreshSignView(null);
	}

	/**
	 * 
	 * @author CBB
	 * @param pSignPoint
	 *            描述：pSignPoint 用户自己点击选择的签到点
	 */
	public void refreshSignView(SignPoint pSignPoint) {
		tv_signin.setClickable(true);
		LatLng latLng = null;
		SignPoint nearestPoint = null;
		nearestDistance = 0.0;
		if (pSignPoint == null) {// 系统自己选择的最近的点
			if (points == null || points.size() == 0)
				return;

			TreeMap<Double, SignPoint> map = new TreeMap<Double, SignPoint>();
			availablePoints = new ArrayList<SignPoint>();
			for (SignPoint point : points) {// 目的是为了得到最近的点
				latLng = new LatLng(Double.parseDouble(point.getLatitude()),
						Double.parseDouble(point.getLongitude()));
				double tempdis = DistanceUtil.getDistance(myLoc, latLng);
				map.put(tempdis, point);
				if(tempdis < 500){
					availablePoints.add(point);
				}
			}
			
			if(availablePoints != null && availablePoints.size() > 1){
				// 如果有多个在有效范围内，就提示点选签到点；
				handler.sendEmptyMessage(SHOW_CHOOSE_POINT);
			}else{
				Entry<Double, SignPoint> nearest = map.pollFirstEntry();
				nearestDistance = nearest.getKey();// 得到最近的一个距离
				// double nearestDistance = 800;//得到最近的一个距离
				choicePoint = nearestPoint = nearest.getValue();// 得到最近距离的点
				initRecentSigner();
			}
			
		} else {// 用户点击选择的点
			nearestPoint = pSignPoint;
			nearestDistance = DistanceUtil.getDistance(myLoc,
					new LatLng(Double.parseDouble(nearestPoint.getLatitude()),
							Double.parseDouble(nearestPoint.getLongitude())));
		}
		
		if(nearestPoint != null){
			addInfosOverlay(nearestPoint, false);// 添加覆盖物
		}
		
		if (nearestPoint != null) {
			if (nearestDistance > 1000) {
				tv_signin.setText("异地");
				isOtherPlace = true;
			} else if (nearestDistance < 1000 && nearestDistance > 500) {
				tv_signin.setText("距考勤点" + (int) nearestDistance + "米");
				isOtherPlace = true;
			} else {
				isOtherPlace = false;
				tv_signin.setText("考勤");
			}
			
		}
	}
	private View infoview ;
	private TextView tv_company ;
	private TextView tv_company_address ;
	public void addInfosOverlay(SignPoint nearestPoint, boolean isaddsigner) {
		LatLng latLng = null;
		OverlayOptions overlayOptions = null;
		Marker marker = null;
		mBaiduMap.clear();// 先清除所有覆盖物
		if(nearestPoint != null){
			OverlayOptions ooCircle = new CircleOptions()
					.fillColor(0x00000000)
					.center(new LatLng(Double.parseDouble(nearestPoint
							.getLatitude()), Double.parseDouble(nearestPoint
							.getLongitude()))).stroke(new Stroke(2, 0xAA008EE9))
					.radius(500);
			mBaiduMap.addOverlay(ooCircle);// 再添加圆形覆盖物
		}
		for (SignPoint point : points) {// 添加泡泡覆盖物点
			// 图标
			latLng = new LatLng(Double.parseDouble(point.getLatitude()),
					Double.parseDouble(point.getLongitude()));
			overlayOptions = new MarkerOptions().position(latLng)
					.icon(mIconMaker);
			marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));
			Bundle bundle = new Bundle();
			bundle.putSerializable("info", point);
			marker.setExtraInfo(bundle);
			
			if(nearestPoint != null && point.getLatitude().equals(nearestPoint.getLatitude()) 
					&& point.getLongitude().equals(nearestPoint.getLongitude())){
				if(infoview == null){
					infoview = LayoutInflater.from(context).inflate(
							R.layout.item_signin_current_loc, null);
					tv_company = (TextView) infoview.findViewById(R.id.company_name);
					tv_company_address = (TextView) infoview
							.findViewById(R.id.company_address);
				}

				OnInfoWindowClickListener listener = new OnInfoWindowClickListener() {
					@Override
					public void onInfoWindowClick() {
						mBaiduMap.hideInfoWindow();
					}
				};
				LatLng ll = marker.getPosition();
				Point p = mBaiduMap.getProjection().toScreenLocation(ll);
				Log.e(TAG, "--!" + p.x + " , " + p.y);
				p.y -= 47;
				LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
				tv_company.setText(nearestPoint.getPointName());
				tv_company_address.setText(nearestPoint.getAddressName());
				mInfoWindow = new InfoWindow(infoview, llInfo ,0);
				if(!isaddsigner && signerLatLng == null){
					mBaiduMap.showInfoWindow(mInfoWindow);
				}
			}
		}
		
		//
		if( signerLatLng != null 
				&& signerview != null
//				&& isaddsigner
				){
			
//			signerIconMaker = BitmapDescriptorFactory.fromView(signerview);
			signerIconMaker = BitmapDescriptorFactory.fromBitmap(signerview);
			signerOverlayOptions = new MarkerOptions().position(signerLatLng)
					.icon(signerIconMaker);
			signermarker = (Marker) mBaiduMap.addOverlay(signerOverlayOptions);
			if(isaddsigner){
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(signerLatLng);
				mBaiduMap.animateMapStatus(u);
			}
			
		}
	}

	@Override
	public void onGetLocationLatLon(BDLocation location) {
		Log.v(TAG, "onGetLocationLatLon-------");
		// lat = location.getLatitude();
		// lon = location.getLongitude();
		// tv_gps.setText("("+location.getLatitude()+","+location.getLongitude()+")");
		myLoc = new LatLng(location.getLatitude(), location.getLongitude());
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(myLoc);
		mBaiduMap.animateMapStatus(u);

		MyLocationData locData = new MyLocationData.Builder()
				.accuracy(location.getRadius())
				// 此处设置开发者获取到的方向信息，顺时针0-360
				.direction(100).latitude(location.getLatitude())
				.longitude(location.getLongitude()).build();
		mBaiduMap.setMyLocationData(locData);
		if (choicePoint != null) {// 自己选择的签到点
			refreshSignView(choicePoint);
		} else {// 系统选择最近的签到点
			refreshSignView(null);
		}
	}

	@Override
	public void onGetLocationAddress(String address) {
		cur_address = address;
		Log.v(TAG, "address=" + address);
		// tv_address.setText(StringUtil.isNullOrEmpty(cur_address)?"正在定位...":cur_address);
	}

	private void addSignIn(String reason) {
		tv_signin.setClickable(false);
		if(pop_ydreason != null && pop_ydreason.isShowing()){
			pop_ydreason.dismiss();
		}
		if (choicePoint != null) {
			
			// --------20150413---用新的接口---begin
//			ContactDBUtils contactDBUtils = new ContactDBUtils(context);
//			ContactBean temp = contactDBUtils.getContactByUid(AppApplication.preferenceProvider.getUid());
//			SignHistoryNew sh = new SignHistoryNew();
//			sh.setBossId(AppApplication.preferenceProvider.getSjid());
//			sh.setBz(StringUtil.isNullOrEmpty(reason)?"签到":reason);
//			sh.setCompanyCode(AppApplication.preferenceProvider.getCompanyCode());
//			// 如果新增的同事，还没更新下来通讯录种的数据，那他的dept是查不到，查不到就传companyBmid，不能让接口出错；
//			sh.setDepart(temp==null?AppApplication.preferenceProvider.getCompanyBmid():temp.getDept());
//			sh.setDistance(String.valueOf(nearestDistance));
//			sh.setIcon("");
//			sh.setLateReason("");
//			sh.setLatitude(String.valueOf(myLoc.latitude));
//			sh.setLongitude(String.valueOf(myLoc.longitude));
//			sh.setPointAddress(choicePoint.getAddressName());
//			sh.setPointName(choicePoint.getPointName());
//			sh.setSignAddress(StringUtil.isNullOrEmpty(cur_address)?"未知地理位置":cur_address);
//			sh.setSignPointUid(choicePoint.getId());
//			sh.setSignTime(DateUtil.getCurrentDateString());
//			sh.setType("2");
//			sh.setUid(AppApplication.preferenceProvider.getUid());
//			sh.setUserName(AppApplication.preferenceProvider.getUsername());
//			sh.setUserUid(AppApplication.preferenceProvider.getUid());
//			// --------20150413---用新的接口---end
//			AppApplication.dataProvider.saveSignHistory2(sh,
//					new AjaxCallBack<Object>() {
//						@Override
//						public void onStart() {
//							super.onStart();
//							handler.sendEmptyMessage(Common.SHOWPROGRESS);
//						}
//
//						@Override
//						public void onSuccess(Object t) {
//							handler.sendEmptyMessage(Common.HIDEPROGRESS);
//							Logger.v(TAG, "------------");
//							Logger.v(TAG, ""+t.toString());
//							Message msg = handler.obtainMessage();
//							try {
//								ResultBaseBean result = (ResultBaseBean) GsonUtil
//										.getObject(t.toString(),
//												ResultBaseBean.class);
//								if (result != null
//										&& result.getCode().equals(
//												Common.RESULT_OK)) {
//									msg.what = ADD_SIGNIN_SUCCESS;
//									msg.obj = result.getDescription();
//									handler.sendMessage(msg);
//								} else {
//									msg.what = Common.ERROR;
//									msg.obj = result==null?"签到失败":result.getDescription();
//									handler.sendMessage(msg);
//								}
//							} catch (Exception e) {
//								msg.what = Common.ERROR;
//								msg.obj = "签到失败";
//								handler.sendMessage(msg);
//							} finally {
//								tv_signin.setClickable(true);
//							}
//						}
//
//						@Override
//						public void onFailure(Throwable t, int errorNo,
//								String strMsg) {
//							Logger.e(TAG, ""+strMsg);
//							tv_signin.setClickable(true);
//							handler.sendEmptyMessage(Common.HIDEPROGRESS);
//							Message msg = handler.obtainMessage();
//							msg.what = Common.ERROR;
//							msg.obj = "签到失败";
//							handler.sendMessage(msg);
//						}
//
//					});
		}else{
		}

		CommonDialog.showSignNo(MainSignFragment.this);
		tv_signin.setClickable(true);
	}
//	private AlertDialog ad_canntsignin = null ;

	@Override
	protected void handleMsg(Message msg) {
		switch (msg.what) {
		case Constants.ERROR:
			if (msg.obj == null)
				return;
			showCustomToast(msg.obj.toString());
			break;
		case Constants.SUCCESS:
			if (msg.obj == null)
				return;
			showCustomToast(msg.obj.toString());
			break;
		case Constants.REFRESH:
			sMemberAdapter.setData(recentSignins);
			sMemberAdapter.notifyDataSetChanged();
			tv_signers.setText(recentSignins.size() == 0 ? "已签到(0)人" : "已签到("
					+ String.valueOf(recentSignins.size()) + ")人");
			// showCustomToast(msg.obj.toString());
			break;
		case ADD_SIGNIN_SUCCESS:
			showSigninSuccessToast("签到成功！");
			initRecentSigner();
			
			break;
		case SHOW_CHOOSE_POINT:
			LatLng latLng = null ;
			String[] items = new String[availablePoints.size()];
			int chooseitem = 0 ;
			for (int i = 0; i < availablePoints.size(); i++) {
				latLng = new LatLng(Double.parseDouble(availablePoints.get(i).getLatitude()),
						Double.parseDouble(availablePoints.get(i).getLongitude()));
				double tempdis = DistanceUtil.getDistance(myLoc, latLng);
				items[i] = availablePoints.get(i).getPointName()+" ("+((int)tempdis)+"米)";
			}
			if(choose_point_ad == null){
				Builder builder = new Builder(context,R.style.Translucent_NoTitle);
				builder.setTitle("请选择考勤点").setCancelable(false);
				builder.setSingleChoiceItems(items, chooseitem, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						choicePoint = availablePoints.get(which);
						if(choicePoint != null){
							signerLatLng = null ;
							addInfosOverlay(choicePoint, false);
							initRecentSigner();
						}
						dialog.dismiss();
					}
				});
				choose_point_ad = builder.create();
				choose_point_ad.show();
			}else if(!choose_point_ad.isShowing()){
				choose_point_ad.show();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_location_back:
			if(myLoc != null){
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(myLoc);
				mBaiduMap.animateMapStatus(u);
			}
			break;
		case R.id.tv_signin:
			if (isOtherPlace) {
				pop_ydreason = new YDPopupWindows(context, rootView);
			} else {
				addSignIn("");
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onDrawerOpened() {
		mainActivity.setPressListener(this);
		iv_signers_arrow
				.setImageResource(R.drawable.common_pullrefresh_droparrow);
	}

	@Override
	public void onDrawerClosed() {
		mainActivity.setPressListener(null);
		iv_signers_arrow
				.setImageResource(R.drawable.common_pullrefresh_droparrow_up);
	}

	@Override
	public void onMySlidingDrawerClick() {
		slidingDrawer.bringToFront();
	}

	private void showSigninSuccessToast(String text) {
		View toastRoot = LayoutInflater.from(getActivity()).inflate(
				R.layout.signin_success_toast, null);
		Toast toast = new Toast(getActivity());
		toast.setGravity(Gravity.FILL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

	public class YDPopupWindows extends PopupWindow {

		public YDPopupWindows(Context mContext, View parent) {

			View view = View
					.inflate(mContext, R.layout.signin_reason_pop, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins_newwork));
			setWidth(LayoutParams.MATCH_PARENT);
			setHeight(LayoutParams.MATCH_PARENT);
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			setBackgroundDrawable(new BitmapDrawable());
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			TextView tv_submit = (TextView) view.findViewById(R.id.tv_submit);
			final EditText et_reason = (EditText) view
					.findViewById(R.id.et_reason);
			TextView tv_loc = (TextView) view.findViewById(R.id.tv_loc);
			tv_submit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String reason = et_reason.getText().toString();
					addSignIn(reason == null ? "" : reason);
				}
			});
			tv_loc.setText(choicePoint == null ? "未知" : choicePoint
					.getAddressName());
		}
	}

	
	@Override
	public boolean onKeyDownPress(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
			if(slidingDrawer.isOpened()){
				slidingDrawer.animateToggle();
				mainActivity.setPressListener(null);
			}
			return true;
		}
		return false;
	}

	
	private LatLng signerLatLng = null ;
	private Marker signermarker = null ;
	private BitmapDescriptor signerIconMaker;
	private OverlayOptions signerOverlayOptions;
//	private View signerview = null ;
	private Bitmap signerview = null ;
	private TextView tv_signername ;
//	private CircularImageViewX iv_signericon ;
//	private ContactBean tempsigner = null ;
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
//		signerview = convertViewToBitmap(view, view.getWidth(), view.getHeight()) ;
//
//		if(slidingDrawer.isOpened()){
//			slidingDrawer.animateToggle();
//		}
//
//		ContactDBUtils butils = new ContactDBUtils(context);
//		tempsigner = butils.getContactByUid(recentSignins.get(position).getUid());
//
//		signerLatLng = new LatLng(Double.parseDouble(recentSignins.get(position).getLatitude()),
//				Double.parseDouble(recentSignins.get(position).getLongitude()));
//
//
//		addInfosOverlay(choicePoint, true);// 添加覆盖物
	}
	


	public Bitmap convertViewToBitmap(View view){
	       view.buildDrawingCache(); 
	       Bitmap bitmap = view.getDrawingCache(); 
	       return bitmap; 
	}
	
	public Bitmap convertViewToBitmap(View view, int bitmapWidth, int bitmapHeight){
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        
        return bitmap;
    }


}
