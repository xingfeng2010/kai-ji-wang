package com.wenyu.kaijiw;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.wenyu.Data.Myone;
import com.wenyu.kjw.adapter.Myoneadpater;

public class FindTwoXuan extends Activity {
	private ListView listview;
	private Myoneadpater hla;
	private List<Myone> arrlist;
	private int [] images={R.drawable.production,R.drawable.director,R.drawable.photography,R.drawable.light,R.drawable.recordsound,R.drawable.clothing,R.drawable.makeup,R.drawable.writer,R.drawable.prop};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_two);
		initView();
	}

	private void initView() {
		listview = (ListView) findViewById(R.id.listView1);
		arrlist = new ArrayList<Myone>();
		for (int i = 0; i < images.length; i++) {
			Myone mh =	new Myone( images[i]);
			arrlist.add(mh);
			
		}
		hla =new Myoneadpater(arrlist, FindTwoXuan.this);
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
				Intent it0= new Intent(FindTwoXuan.this,FindFouractivity.class);
//				it0.putExtra("key", str[0]);
				startActivity(it0);
				break;
			case 1:
				Intent it1= new Intent(FindTwoXuan.this,FindFouractivity.class);
//				it1.putExtra("key", str[1]);
				startActivity(it1);
				break;
			case 2:
				Intent it2= new Intent(FindTwoXuan.this,FindFouractivity.class);
//				it2.putExtra("key", str[2]);
				startActivity(it2);
				break;
			}
		}
	};
}
