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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.IntrovalueData;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.kjw.adapter.SliAdapter;

/**
 * 影视项目
 * @author shasha
 *
 */
public class Findfilmproject extends Activity {
	private String url = ConstantClassField.SERVICE_URL + "service/filmproject";
	private Map<String, String> paramsValue;
	private ListView listview;
	private String ss;
	private TextView text;
	private List<String> strs,strvalue;
	Handler  handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				SliAdapter sa = new SliAdapter(strs,Findfilmproject.this);
				listview.setAdapter(sa);
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
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				if(NetWorkUtil.isNetAvailable(Findfilmproject.this)){
					try {
						ss = new HttpP().sendPOSTRequestHttpClient(url, paramsValue);
						getJson(ss);
						handler.sendEmptyMessage(1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {

				}
			}
			
		}).start();;
		
	}

	private void initView() {
		paramsValue =new HashMap<String,String>();
		paramsValue.put("", "");
		listview = (ListView) findViewById(R.id.listView1);
		text = (TextView) findViewById(R.id.textView1);
		String name = getIntent().getStringExtra("it");
		text.setText(name);
		listview.setOnItemClickListener(ocl);
	}
	public void getJson(String ss){
		strs = new ArrayList<String>();
		strvalue = new ArrayList<String>();
		List<IntrovalueData> invalue = new ArrayList<IntrovalueData>();
		try {
			JSONArray  joo = new JSONArray(ss);
			for (int i = 0; i < joo.length(); i++) {
				JSONObject jo = joo.optJSONObject(i);
				String name =jo.optString("name");
				String value =jo.optString("id");
				IntrovalueData  itvalue = new IntrovalueData(value);
				strs.add(name);
				strvalue.add(value);
				invalue.add(itvalue);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	OnItemClickListener ocl =new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
				Intent it0= new Intent(Findfilmproject.this,FindYingCompanyItemActivity.class);
				it0.putExtra("id", strvalue.get(arg2));
				it0.putExtra("name", strs.get(arg2));
				startActivity(it0);
		}

	};
}
