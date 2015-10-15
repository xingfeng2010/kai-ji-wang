package com.wenyu.kaijiw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.wenyu.Data.Myone;
import com.wenyu.kjw.adapter.Myoneadpater;

/**
 * 发现_演艺联盟
 * @author huhaoran
 *
 */
public class FindTwoAlliance extends Activity {
	private ListView listview;
	private Myoneadpater hla;
	private List<Myone> arrlist;
	Map<String, String> paramsValue;
	private ImageButton btn1,btn2,btn3,btn4,btn5,btn6;
	private  String [] str={"制片组","导演组","美术组","摄影组","灯光组","录音组","服装组","化妆组","道具组","艺术院校"};
	private int [] images={R.drawable.production,R.drawable.director,R.drawable.photography,R.drawable.light,R.drawable.recordsound,R.drawable.clothing,R.drawable.makeup,R.drawable.writer,R.drawable.prop};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_two);
		initView();

		DisplayMetrics metric = new DisplayMetrics();  
		getWindowManager().getDefaultDisplay().getMetrics(metric);  
		int width = metric.widthPixels;     // 屏幕宽度（像素）  
		int height = metric.heightPixels;   // 屏幕高度（像素）  
//		System.out.println("width:"+width+"height:"+height);
	}
	
	private void initView() {
		listview = (ListView) findViewById(R.id.listView1);
		arrlist = new ArrayList<Myone>();
		for (int i = 0; i < images.length; i++) {
			Myone mh =	new Myone( images[i]);
			arrlist.add(mh);
			
		}
		hla =new Myoneadpater(arrlist, FindTwoAlliance.this);
		listview.setAdapter(hla);
		listview.setDivider(null);
		listview.setOnItemClickListener(ocl);
	}
	
	OnItemClickListener ocl =new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			switch(arg2){
			case 0:
				Intent it0= new Intent(FindTwoAlliance.this,FindThree.class);
				it0.putExtra("key", str[0]);
				startActivity(it0);
				break;
			case 1:
				Intent it1= new Intent(FindTwoAlliance.this,FindThree.class);
				it1.putExtra("key", str[1]);
				startActivity(it1);
				break;
			case 2:
				Intent it2= new Intent(FindTwoAlliance.this,FindThree.class);
				it2.putExtra("key", str[2]);
				startActivity(it2);
				break;
			}
		}
	

};
}
