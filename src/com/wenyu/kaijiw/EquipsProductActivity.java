package com.wenyu.kaijiw;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.Find_yingshi;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Pscd;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.kjw.adapter.HomeYing;


public class EquipsProductActivity extends FragmentActivity {
	private String url =ConstantClassField.SERVICE_URL+"service/storestop";
	private String url1= ConstantClassField.SERVICE_URL+"service/storelist";
	private Spinner sp1,sp3;
	private ArrayAdapter<String> bb,dd;
	private ListView listview;
	private List<Find_yingshi> fyslist;
	private HomeYing hla;
	private Map<String,String> paramsValue;
	private List<String>str1,str2;
	private String s,s1,s2,ss,name,cate ;
	private Pscd pscd;
	private String[] ws ={"","",""};
	private Context context;
	private String intentid,intentcity,category;
	TextView text;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){

			case 0:
				Toast.makeText(context, "网络连接异常 ", 1000).show();
				break;
			case 1:
				bb = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.spinner5));
				sp1.setAdapter(bb);
				dd = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.spinner4));
				sp3.setAdapter(dd);
				break;
			case 3:
				if(fyslist!=null){
					hla =new HomeYing(fyslist, context);
					listview.setAdapter(hla);
				}else{
				
				}
				
				break;
			case 5:
				Toast.makeText(context, "返回上级重试 ", 1000).show();
				break;
			case 8:
				Toast.makeText(context, "正在加载数据", 1000).show();
				break;
			}
		};
	};

	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.productlist);
		initView();
		context = EquipsProductActivity.this;
		str1= new ArrayList<String>();
		str2= new ArrayList<String>();
		movesPeople();
		if(sp1!=null||sp3!=null){
			initThread();
		}else{
			Toast.makeText(context, "正在加载头部数据", 1000).show();	
		}
		
		
//		sp1.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				s= sp1.getSelectedItem().toString();
//				s2= sp3.getSelectedItem().toString();
//				initThread();
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//
//			}
//		});
//		sp3.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				
//				s= sp1.getSelectedItem().toString();
//				s2= sp3.getSelectedItem().toString();
//				initThread();
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//
//			}
//		});

	}

	private void movesPeople() {



		new Thread(new Runnable(){

			@Override
			public void run() {

				try {
					paramsValue=new HashMap<String, String>(); 
					paramsValue.put("category",category);
					paramsValue.put("city",intentcity);
					if(NetWorkUtil.isNetAvailable(context)){
						ss = new HttpP().sendPOSTRequestHttpClient(url,paramsValue);
						if(("null").equals(ss)&&("").equals(ss)){
							handler.sendEmptyMessage(8);
						}else {
							fa(ss);
							handler.sendEmptyMessage(1);
						}
					}else {
						handler.sendEmptyMessage(0);
					}

				} catch (Exception e) {

					e.printStackTrace();
				}

			}
		}).start();

	}


	private void initThread() {
		new Thread(new Runnable(){

			@Override
			public void run() {

				try {
					paramsValue=new HashMap<String, String>();  
					if(("全部").equals(s)||("品牌").equals(s)){
						s= "" ;
					} if(("综合排序").equals(s1)||("浏览次数").equals(s1)){
						s1= "" ;
					} 
					paramsValue.put("id", intentid);
//					paramsValue.put("type", getIntent().getStringExtra("type"));
					paramsValue.put("regional", intentcity);

					if(NetWorkUtil.isNetAvailable(context)){
						ss = new HttpP().sendPOSTRequestHttpClient(url1,paramsValue);
						if(("").equals(ss)){
							handler.sendEmptyMessage(8);
						}else{
							fyslist = initying(ss);
							handler.sendEmptyMessage(3);
						}
					}
					else {
						handler.sendEmptyMessage(0);
					}

				} catch (Exception e) {

					e.printStackTrace();
				}

			}

		}).start();


	}

	private List<Find_yingshi> initying(String jss) {
		List<Find_yingshi>	fyslist = new ArrayList<Find_yingshi>();
		try {
			JSONArray jaa = 	new JSONArray(jss);
			for (int i = 0; i < jaa.length(); i++) {
				JSONObject jo = jaa.optJSONObject(i);
				String id = jo.optString("id");
				String name = jo.optString("name");
				String pic = jo.optString("image");
				String countV = jo.optString("countV");
				Find_yingshi fys = 	new Find_yingshi(pic, name,id,countV);
				fyslist.add(fys);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return fyslist;

	}	
	
	public  Pscd  fa(String jsonData){
		pscd =null;
		JSONObject jo;
		try {
			jo = new JSONObject(jsonData);
			JSONArray regionals = jo.optJSONArray("regional");
			for (int i = 0; i < regionals.length(); i++) {
				str1.add(regionals.optString(i));
			}
			JSONArray orderby = jo.optJSONArray("orderby");
			for (int i = 0; i < orderby.length(); i++) {
				str2.add(orderby.optString(i));
			}
			pscd = new Pscd(str1, str2);


		} catch (JSONException e) {
			e.printStackTrace();
		}

		return pscd ;

	}  


	
	private void initView() {
		Intent  inte = getIntent();
		intentid = inte.getStringExtra("id");
		intentcity = inte.getStringExtra("name");
		category =inte.getStringExtra("category");
		sp1 = (Spinner)findViewById(R.id.city);
		sp3 = (Spinner)findViewById(R.id.ty);
		ImageView backBu = (ImageView)findViewById(R.id.imageView1);
		backBu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EquipsProductActivity.this.finish();
				
			}
		});
		text = (TextView)findViewById(R.id.textView1);
		text.setText("店家列表");
		listview = (ListView)findViewById(R.id.listview);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent it= new Intent(EquipsProductActivity.this,StroresDetail.class);
				it.putExtra("id",fyslist.get(arg2).getId());
				it.putExtra("intentid", intentid);
				it.putExtra("picture", fyslist.get(arg2).getPicture());
				startActivity(it);
			}
		});
	}



}



