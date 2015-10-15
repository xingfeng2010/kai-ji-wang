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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Myhome;
import com.wenyu.Data.ProductData;
import com.wenyu.Data.Pscd;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.kjw.adapter.ProductAdapter;


public class ProductActivity extends FragmentActivity {
	private String url =ConstantClassField.SERVICE_URL+"service/equipstop";
	private String url1= ConstantClassField.SERVICE_URL+"service/equiplist";
	private Spinner sp1,sp2,sp3;
	private ArrayAdapter<String> bb,cc,dd;
	private ListView listview;
	private List<Myhome> lists;
	private List<ProductData> fyslist;
	private ProductAdapter hla;
	private Map<String,String> paramsValue;
	private List<String>str,str1,str2;
	private String s,s1,s2,intentcity,ss,category,name,cate,type ;
	private Pscd pscd;
	private String[] ws ={"","",""};
	private Context context;
	private String intentid;
	private ImageView back;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){

			case 0:
				Toast.makeText(context, "网络连接异常 ", 1000).show();
				break;
			case 1:
				bb = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,str);
				sp1.setAdapter(bb);
				dd = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, str2);
				sp3.setAdapter(dd);
				break;
			case 3:
				hla = new ProductAdapter(fyslist, context);
				listview.setAdapter(hla);
				break;
			case 5:
				Toast.makeText(context, "返回上级重试 ", 1000).show();
				break;
			case 8:
				bb = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,ws);
				listview.setAdapter(bb);
				break;
			}
		};
	};

	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.productlist);
		initView();
		movesPeople();
		//		左侧spinner监听
		sp1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				s= sp1.getSelectedItem().toString();
				s1= sp3.getSelectedItem().toString();
				initThread();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		// 右侧spinner监听
		sp3.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				s= sp1.getSelectedItem().toString();
				s1= sp3.getSelectedItem().toString();
				initThread();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

	}

	/**
	 * 筛选后数据接口的调用
	 */
	private void initThread() {
		new Thread(new Runnable(){

			@Override
			public void run() {

				try {
					paramsValue=new HashMap<String, String>();  
					paramsValue.put("id",intentid);
					paramsValue.put("type",type);
					paramsValue.put("brand","");
					paramsValue.put("orderby",s1);

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



	/**
	 * 筛选前数据接口的调用
	 */
	private void movesPeople() {

		new Thread(new Runnable(){

			@Override
			public void run() {

				try {
					paramsValue=new HashMap<String, String>(); 
					paramsValue.put("id", intentid);
					paramsValue.put("regional", intentcity);
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
	/**
	 * 初始化控件
	 */
	private void initView() {
		context = ProductActivity.this;
		str= new ArrayList<String>();
		str1= new ArrayList<String>();
		str2= new ArrayList<String>();
		back = (ImageView) findViewById(R.id.imageView1);
		intentid = getIntent().getStringExtra("id");
		type = getIntent().getStringExtra("type");
		category =getIntent().getStringExtra("category");
		sp1 = (Spinner)findViewById(R.id.city);
		sp3 = (Spinner)findViewById(R.id.ty);
		listview = (ListView)findViewById(R.id.listview);
		listview.setFocusable(false);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				ProductActivity.this.finish();
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//				器材详情界面
				Intent it= new Intent(ProductActivity.this,EquipsDetail.class);
				it.putExtra("id",fyslist.get(arg2).getId());
				it.putExtra("intentid", intentid);
				it.putExtra("picture", fyslist.get(arg2).getImage());
				startActivity(it);
			}
		});
	}
	/**
	 * 
	 * @param 筛选后数据的解析
	 * @return List<ProductData> 集合
	 */
	private List<ProductData> initying(String jss) {
		List<ProductData>	fyslist = new ArrayList<ProductData>();
		try {
			JSONArray jaa = 	new JSONArray(jss);
			for (int i = 0; i < jaa.length(); i++) {
				JSONObject jo = jaa.optJSONObject(i);
				String id = jo.optString("id");
				String name = jo.optString("name");
				String storename = jo.optString("countV");
				String pic = jo.optString("image");
				ProductData fys = new ProductData(id,name,storename,pic);
				fyslist.add(fys);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return fyslist;

	}
	/**
	 *  筛选前数据的解析
	 * @param jsonData
	 * @return
	 */
	public  Pscd  fa(String jsonData){
		pscd =null;
		JSONObject jo;
		try {
			jo = new JSONObject(jsonData);
			JSONArray types = jo.optJSONArray("brand");
			for (int i = 0; i < types.length(); i++) {
				str.add(types.optString(i));
			}

			JSONArray orderby = jo.optJSONArray("orderby");
			for (int i = 0; i < orderby.length(); i++) {
				str2.add(orderby.optString(i));
			}
			pscd = new Pscd(str, str2);


		} catch (JSONException e) {
			e.printStackTrace();
		}

		return pscd ;

	}  
}



