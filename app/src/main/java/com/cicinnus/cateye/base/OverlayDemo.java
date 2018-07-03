package com.cicinnus.cateye.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.cicinnus.cateye.R;

/**
 * 演示覆盖物的用法
 */
public class OverlayDemo extends Activity {

  /**
   * MapView 是地图主控件
   */
  private MapView mMapView;
  private BaiduMap mBaiduMap;
  private Marker mMarkerA;
  private InfoWindow mInfoWindow;

  // 初始化全局 bitmap 信息，不用时及时 recycle
  BitmapDescriptor bdA = BitmapDescriptorFactory
      .fromResource(R.drawable.icon_marka);

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_overlay);

    findViewById(R.id.rl_back).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }

    });
    TextView tv_title = findViewById(R.id.tv_title);
    tv_title.setText("新生报到处位置");

    mMapView = (MapView) findViewById(R.id.bmapView);
    mBaiduMap = mMapView.getMap();
    MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
    mBaiduMap.setMapStatus(msu);
    initOverlay();
    mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
      public boolean onMarkerClick(final Marker marker) {
        Button button = new Button(getApplicationContext());
        button.setBackgroundResource(R.drawable.popup);
        OnInfoWindowClickListener listener = null;
        if (marker == mMarkerA) {
          button.setText("报到处");
          button.setTextColor(Color.BLACK);
          button.setWidth(300);

          listener = new OnInfoWindowClickListener() {
            public void onInfoWindowClick() {

            }
          };
          LatLng ll = marker.getPosition();
          mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -47, listener);
          mBaiduMap.showInfoWindow(mInfoWindow);
        }
        return true;
      }
    });
  }

  public void initOverlay() {
    // add marker overlay
    LatLng llA = new LatLng(36.043347, 114.385818);
    MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdA)
        .zIndex(9).draggable(true);
    mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));

    MapStatus mapStatus = new MapStatus.Builder()
        .target(llA)
        .zoom(14)
        .build();
    MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
    mBaiduMap.setMapStatus(mapStatusUpdate);

    mBaiduMap.setOnMarkerDragListener(new OnMarkerDragListener() {
      public void onMarkerDrag(Marker marker) {
      }

      public void onMarkerDragEnd(Marker marker) {
      }

      public void onMarkerDragStart(Marker marker) {
      }
    });
  }


  @Override
  protected void onPause() {
    // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
    mMapView.onPause();
    super.onPause();
  }

  @Override
  protected void onResume() {
    // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
    mMapView.onResume();
    super.onResume();
  }

  @Override
  protected void onDestroy() {
    // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
    mMapView.onDestroy();
    super.onDestroy();
    // 回收 bitmap 资源
    bdA.recycle();
  }

}
