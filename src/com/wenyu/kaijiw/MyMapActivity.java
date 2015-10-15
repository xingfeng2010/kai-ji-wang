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
 * ��ʾMapView�Ļ����÷�
 */
public class MyMapActivity extends Activity implements OnGetGeoCoderResultListener {
	@SuppressWarnings("unused")
	private static final String LTAG = MyMapActivity.class.getSimpleName();
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	// ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��
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
	 * ��������
	 * 
	 * @param v
	 */
	public void SearchButtonProcess() {
		
			//lat/lon����γ��
			String x = getIntent().getStringExtra("x");
			String y = getIntent().getStringExtra("y");
			if(("").equals(x) || ("").equals(y)||x==null ||y ==null){
				Toast.makeText(MyMapActivity.this, "����Ϊ��", 500).show();
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
			Toast.makeText(MyMapActivity.this, "��Ǹ��δ���ҵ����", Toast.LENGTH_LONG)
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
		// activity ��ͣʱͬʱ��ͣ��ͼ�ؼ�
		mMapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// activity �ָ�ʱͬʱ�ָ���ͼ�ؼ�
		mMapView.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// activity ����ʱͬʱ���ٵ�ͼ�ؼ�
		mMapView.onDestroy();
	}


	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {
		// TODO Auto-generated method stub
		
	}

}
