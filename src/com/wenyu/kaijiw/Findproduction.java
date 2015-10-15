package com.wenyu.kaijiw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.ScreenManager;
import com.wenyu.Data.Similar;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.kjw.adapter.CompanyDetailAdapter;

/**
 * 发现_后期制作
 * @author shasha
 *
 */
public class Findproduction extends Activity {
	private String url = ConstantClassField.SERVICE_URL+"service/organizations";
	private ListView listview;
	private Similar  si;
	Map<String, String> paramsValue;
	private List<Similar> lists;
	private String ss;
	private ImageView back;
	private TextView text;
	ScreenManager sm;
	Handler  handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				CompanyDetailAdapter sa =new CompanyDetailAdapter(Findproduction.this, lists);
//				System.err.println("_____________777"+sa+"lists"+lists);
				listview.setAdapter(sa);
				break;
			case 2:
				break;
			}
		};

	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_two);
		initView();
		initThread();

	}

	private void initThread() {
		paramsValue =new HashMap<String,String>();
		paramsValue.put("category", getIntent().getStringExtra("it"));
		paramsValue.put("orderby", "综合排序");
		new  Thread(new Runnable(){	
			@Override
			public void run() {
				if(NetWorkUtil.isNetAvailable(Findproduction.this)){
					try {
						ss = new HttpP().sendPOSTRequestHttpClient(url, paramsValue);
						lists =	getJson(ss);
						handler.sendEmptyMessage(1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {
					Toast.makeText(Findproduction.this, "连接网络", 1000).show();
				}
			}
		}).start();;

	}
	private void initView() {
		sm =  ScreenManager.gerScreenManager();
		sm.pushActivity(Findproduction.this);
		back= (ImageView) findViewById(R.id.imageView1);
		text = (TextView) findViewById(R.id.textView1);
		String name = getIntent().getStringExtra("it");
		text.setText(name);
		listview = (ListView) findViewById(R.id.listView1);
		listview.setDivider(null);
		listview.setOnItemClickListener(ocl);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sm.finishAllActivity();
			}
		});
	}
	public List<Similar> getJson(String ss){
	 List<Similar> lists= new ArrayList<Similar>();
		try {
			JSONArray jso =new JSONArray(ss);
			for (int i = 0; i < jso.length(); i++) {
				JSONObject jo = jso.optJSONObject(i);
				String id = jo.optString("id");
				String name =jo.optString("name");
				String regional = jo.optString("regional");         
				String address  =jo.optString("address");
				String image = jo.optString("image");
				Similar	si = new Similar(name, address, regional,image,id);
				lists.add(si);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return lists;

	}
	OnItemClickListener ocl =new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent it0= new Intent(Findproduction.this,FindFouractivity.class);
			it0.putExtra("id", lists.get(arg2).getId());
			it0.putExtra("key", lists.get(arg2));
			startActivity(it0);
		}
	};
}
