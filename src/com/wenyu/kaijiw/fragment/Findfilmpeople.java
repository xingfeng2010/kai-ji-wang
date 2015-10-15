package com.wenyu.kaijiw.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wenyu.Data.Myone;
import com.wenyu.Data.ScreenManager;
import com.wenyu.kaijiw.FindThree;
import com.wenyu.kaijiw.R;
import com.wenyu.kjw.adapter.Myoneadpater;

/**
 * 影视人才
 * @author shasha
 *
 */
public class Findfilmpeople extends Activity {
	private ListView listview;
	private Myoneadpater hla;
	private ImageView back;
	ScreenManager sm;
	private List<Myone> arrlist;
	Map<String, String> paramsValue;
	private TextView text;
	private  String [] str={"制片组","导演组","美术组","摄影组","灯光组","录音组","服装组","化妆组","道具组","艺术院校"};
	private int [] images={R.drawable.production,R.drawable.director,R.drawable.photography,R.drawable.light,R.drawable.recordsound,R.drawable.clothing,R.drawable.makeup,R.drawable.writer,R.drawable.prop};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_people);
		initView();

	}

	private void initView() {
		sm =  ScreenManager.gerScreenManager();
		sm.pushActivity(Findfilmpeople.this);
		back= (ImageView) findViewById(R.id.imageView1);
		text = (TextView) findViewById(R.id.textView1);
		String name = getIntent().getStringExtra("it");
		text.setText(name);
		listview = (ListView) findViewById(R.id.listView1);
		arrlist = new ArrayList<Myone>();
		for (int i = 0; i < images.length; i++) {
			Myone mh =	new Myone( images[i]);
			arrlist.add(mh);

		}
		hla =new Myoneadpater(arrlist, Findfilmpeople.this);
		listview.setAdapter(hla);
		listview.setDivider(null);
		listview.setOnItemClickListener(ocl);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sm.finishAllActivity();
			}
		});
	}

	OnItemClickListener ocl =new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent it0= new Intent(Findfilmpeople.this,FindThree.class);
			it0.putExtra("key", str[arg2]);
			startActivity(it0);
		}

	};
}
