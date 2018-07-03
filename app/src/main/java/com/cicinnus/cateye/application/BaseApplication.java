package com.cicinnus.cateye.application;

import android.app.Application;

//import com.amap.api.location.AMapLocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.cicinnus.cateye.net.Api;
import com.cicinnus.cateye.net.OkHttpManager;
import com.cicinnus.cateye.net.RetrofitClient;
import com.cicinnus.retrofitlib.utils.SPUtils;
//import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator on 2017/1/18.
 */

public class BaseApplication extends Application {

    private static BaseApplication instance;
//    public LocationClient mLocationClient;
//    public BDLocationListener mMyLocationListener;
    
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        LeakCanary.install(this);
        RetrofitClient.initClient_BaseUrl(OkHttpManager.getInstance(), Api.BASE_URL);
//        AMapLocationClient.setApiKey("979bfef3d3226a0a475200e5dd269a56");


        SDKInitializer.initialize(getApplicationContext());
//        mLocationClient = new LocationClient(getApplicationContext());
//        LocationClientOption option = new LocationClientOption();
//        option.setIsNeedAddress(true);
//        option.setOpenGps(true);// 打开gps
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(5000);
//        mLocationClient.setLocOption(option);
//        mMyLocationListener = new BDLocationListener() {
//            @Override
//            public void onReceiveLocation(BDLocation location) {
//                SPUtils.getInstance(getApplicationContext(), "bd").setBDTime(location.getTime());
//                SPUtils.getInstance(getApplicationContext(), "bd").setBDLat(location.getLatitude());
//                SPUtils.getInstance(getApplicationContext(), "bd").setBDLon(location.getLongitude());
//                SPUtils.getInstance(getApplicationContext(), "bd").setBDProvince(location.getProvince());
//                SPUtils.getInstance(getApplicationContext(), "bd").setBDCity(location.getCity());
//                SPUtils.getInstance(getApplicationContext(), "bd").setBDCountry(location.getDistrict());
//                SPUtils.getInstance(getApplicationContext(), "bd").setBDStreet(location.getStreet());
//                SPUtils.getInstance(getApplicationContext(), "bd").setBDStreetNumber(location.getStreetNumber());
//                mLocationClient.stop();
//            }
//        };
//        mLocationClient.registerLocationListener(mMyLocationListener);
//        mLocationClient.start();
    }

    public static BaseApplication getInstance() {
        return instance;
    }

}
