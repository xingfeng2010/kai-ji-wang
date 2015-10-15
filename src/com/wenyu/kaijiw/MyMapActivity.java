package com.wenyu.kaijiw;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

/**
 * 演示MapView的基本用法
 */
public class MyMapActivity extends Activity implements OnGetGeoCoderResultListener {
	@SuppressWarnings("unused")
	private static final String LTAG = MyMapActivity.class.getSimpleName();
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	// 搜索模块，也可去掉地图模块独立使用
	GeoCoder mSearch = null; 
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.mymapview);
		mMapView=(MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
		SearchButtonProcess();
	}

	
	/**
	 * 发起搜索
	 * 
	 * @param v
	 */
	public void SearchButtonProcess() {
		
			//lat/lon经度纬度
			String x = getIntent().getStringExtra("x");
			String y = getIntent().getStringExtra("y");
			if(("").equals(x) || ("").equals(y)||x==null ||y ==null){
				Toast.makeText(MyMapActivity.this, "坐标为空", 500).show();
			}else{
				LatLng ptCenter = new LatLng((Float.valueOf(
						x)), (Float.valueOf(y)));
				mSearch.reverseGeoCode(new ReverseGeoCodeOption()
				.location(ptCenter));
			}
			
			
	}
	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MyMapActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
					.show();
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.loc)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		Toast.makeText(MyMapActivity.this, result.getAddress(),
				Toast.LENGTH_LONG).show();

	}
	@Override
	protected void onPause() {
		super.onPause();
		// activity 暂停时同时暂停地图控件
		mMapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// activity 恢复时同时恢复地图控件
		mMapView.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// activity 销毁时同时销毁地图控件
		mMapView.onDestroy();
	}


	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {
		// TODO Auto-generated method stub
		
	}

}
