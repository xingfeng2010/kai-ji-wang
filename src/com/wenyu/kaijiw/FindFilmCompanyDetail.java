package com.wenyu.kaijiw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.IntroData;
import com.wenyu.Data.IntronameData;
import com.wenyu.Data.IntrovalueData;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.kjw.adapter.ShopIntroduceAdapter;

public class FindFilmCompanyDetail extends Activity {
	private Map<String, String> paramsValue;
	private ListView listview;
	private String ss;
	private TextView text;
	 private List<IntroData> li;
	private String url = ConstantClassField.SERVICE_URL + "service/project";
	Handler  hanler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				ShopIntroduceAdapter sa = new ShopIntroduceAdapter(li, FindFilmCompanyDetail.this);
				listview.setAdapter(sa);
				break;
			}
			
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_two);
		initView();
		initThread();
	}

	private void initView() {
		paramsValue =new HashMap<String,String>();
		paramsValue.put("id", getIntent().getStringExtra("id"));
		listview = (ListView) findViewById(R.id.listView1);
		text = (TextView) findViewById(R.id.textView1);
	}
	private void initThread() {

		new Thread(new Runnable(){

			@Override
			public void run() {
				if(NetWorkUtil.isNetAvailable(FindFilmCompanyDetail.this)){
					try {
						ss = new HttpP().sendPOSTRequestHttpClient(url, paramsValue);
						li = getJSon(ss);
//						System.out.println("Â·¾¶"+url+"²ÎÊý"+paramsValue+li);
						hanler.sendEmptyMessage(1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {

				}
			}

		}).start();;

	}
	public List<IntroData> getJSon(String str){
		li = new ArrayList<IntroData>();
		List<IntronameData> inname = new ArrayList<IntronameData>();
		List<IntrovalueData> invalue = new ArrayList<IntrovalueData>();
		JSONObject objj;
		try {
			objj = new JSONObject(str);
				String name =objj.optString("nameCn");
				String value = objj.optString("nameEn");
				IntronameData intrname= new IntronameData(name);
				IntrovalueData intrvalue= new IntrovalueData(value);
				inname.add(intrname);
				invalue.add(intrvalue);
				IntroData id= new IntroData(inname, invalue);
				li.add(id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return li;

	}
}
