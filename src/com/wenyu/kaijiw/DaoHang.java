package com.wenyu.kaijiw;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.wenyu.kjw.adapter.MyViewPageAdapter;

public class DaoHang extends Activity {
	SharedPreferences preferences;
	ViewPager mViewPager;  
	List<View> mList;
	Boolean isFirstIn;
	Intent intent;
	//µ¼º½Ò³Í¼Æ¬×ÊÔ´  
	public int[] guides = new int[] {R.drawable
			.y1,  
			R.drawable.ic_launcher, R.drawable.ic_launcher,  
			R.drawable.ic_launcher };  

	@Override  
	protected void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.daohang);  

		 preferences = getSharedPreferences("first_pref",
				MODE_PRIVATE);
		isFirstIn = preferences.getBoolean("isFirstIn", true);
		if (isFirstIn) {
			initWithPageGuideMode();  
			preferences.edit().putBoolean("isFirstIn", false).commit();
			intent = new Intent(DaoHang.this, MainActivity.class);
			startActivity(intent);
			finish();
		} else {
			intent = new Intent(DaoHang.this, MainActivity.class);
			startActivity(intent);
		}

	}

	private void initWithPageGuideMode() {
		mViewPager = (ViewPager) findViewById(R.id.viewFlipper1);  
		mList = new ArrayList<View>();  		
		for (int i = 0; i < guides.length; i++) {
			ImageView  v = new ImageView(this);
			v.setBackgroundResource(guides[i]);
			mList.add(v);
			MyViewPageAdapter adapter = new MyViewPageAdapter(mList);  
			mViewPager.setAdapter(adapter); 
		}
	}  
}
