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
import com.wenyu.Data.Find_filmcompanyItem;
import com.wenyu.Data.HttpP;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.kjw.adapter.Find_FilmCompanyAdapter;


/**
 * 影视人才_分组
 * @author huhaoran
 *
 */
public class FindYingCompanyItemActivity extends Activity{
	private Map<String, String> paramsValue;
	private ListView listview;
	private String ss;
	private TextView text;
	private List<Find_filmcompanyItem> filmcompany;
	private String url =ConstantClassField.SERVICE_URL + "service/projects";
	Handler  handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				Find_FilmCompanyAdapter sa = new Find_FilmCompanyAdapter(filmcompany,FindYingCompanyItemActivity.this);
				listview.setAdapter(sa);
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_two);
		initView();
		inThread();
	}
	private void initView() {
		String intentid = getIntent().getStringExtra("id");
		paramsValue =new HashMap<String,String>();
		paramsValue.put("id", intentid);
		paramsValue.put("orderby", "");
		listview = (ListView) findViewById(R.id.listView1);
		text = (TextView) findViewById(R.id.textView1);
		String name = getIntent().getStringExtra("name");
		text.setText(name);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent it = new Intent(FindYingCompanyItemActivity.this,FindFilmCompanyDetail.class);
				it.putExtra("id", filmcompany.get(arg2).getId());
				startActivity(it);
			}
		});
		
	}
	public void  inThread(){
		new Thread(new Runnable(){

			@Override
			public void run() {
				if(NetWorkUtil.isNetAvailable(FindYingCompanyItemActivity.this)){
					try {
						ss = new HttpP().sendPOSTRequestHttpClient(url, paramsValue);
						filmcompany=getJson(ss);
						handler.sendEmptyMessage(1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {

				}
			}
			
		}).start();
	}
	public List<Find_filmcompanyItem> getJson(String str){
		filmcompany = new ArrayList<Find_filmcompanyItem>();
		try {
			JSONArray ja = new JSONArray(str);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject  ob =  ja.optJSONObject(i);
				String id = ob.optString("id");
				String nameCn = ob.optString("nameCn");
				String countV = ob.optString("countV");
				String created_at = ob.optString("created_at");
				Find_filmcompanyItem ffc = new Find_filmcompanyItem(nameCn,countV,created_at,id);
				filmcompany.add(ffc);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return filmcompany;
	}
}
