package com.wenyu.kaijiw;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Release extends Activity {
	private ListView listView;
	private List<String> arrlist;
	private String customer_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_release);
		initView();
	}
	private void initView() {
		customer_id = getIntent().getStringExtra("customer_id");
		listView = (ListView) findViewById(R.id.singlelistView2);
		arrlist = new ArrayList<String>();
		arrlist.add("发布影棚");
		arrlist.add("发布器材");
		arrlist.add("发布实景");
		ArrayAdapter<String> myArrayAdapter =
			     new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrlist);
		listView.setAdapter(myArrayAdapter);
		listView.setOnItemClickListener(oic);
		
	}
	OnItemClickListener oic = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			switch(arg2){
			case 0:
				Intent it = new Intent(Release.this,PublistStudio.class);
				it.putExtra("equ_type", arrlist.get(arg2));
				it.putExtra("customer_id", customer_id);
				arrlist.get(arg2);
				startActivity(it);
				break;
			case 1:
				Intent it1 = new Intent(Release.this,ExpandableList.class);
				it1.putExtra("equ_type", arrlist.get(arg2));
				it1.putExtra("customer_id", customer_id);
				arrlist.get(arg2);
				startActivity(it1);
				break;
			case 2:
				Intent it2 = new Intent(Release.this,Publistissue.class);
				it2.putExtra("customer_id", customer_id);
				it2.putExtra("equ_type", arrlist.get(arg2));
				arrlist.get(arg2);
				startActivity(it2);
				break;
			}
		}
	};

}
