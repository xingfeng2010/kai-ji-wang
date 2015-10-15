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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.HomeSurround;
import com.wenyu.Data.HttpP;
import com.wenyu.kaijiw.fragment.Home_IntroFragment;
import com.wenyu.kjw.adapter.HomeSurroundAdapter;

public class Home_SurroundActivity extends Activity {
	private ListView listview;
	private String TAG = Home_IntroFragment.class.getSimpleName();
	private String url = "http://api.map.baidu.com/place/v2/search";
	private Map<String,String>params;
	private List<HomeSurround> hss ;
	private  String ss,x,y,name,fname;
	private String pic;
	private TextView names;
	private ImageView back;
	private int[] images = {R.drawable.rest,R.drawable.surmaket,R.drawable.jiayou,R.drawable.hotel,R.drawable.yaodian,R.drawable.yiyuan,R.drawable.yinhang};
	View view;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				Toast.makeText(Home_SurroundActivity.this, "请连接网络", 1000).show();
				break;
			case 1:
				int intpic = Integer.parseInt(pic);
				HomeSurroundAdapter sa = new HomeSurroundAdapter(hss, Home_SurroundActivity.this,images[intpic]+"");
				listview.setAdapter(sa);
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopintro_lists);
		initView();
		initThread();
	}

	private void initView() {
		name = getIntent().getStringExtra("name");
		fname = getIntent().getStringExtra("fname");
		pic = getIntent().getStringExtra("pic");
		x = getIntent().getStringExtra("x");
		y =  getIntent().getStringExtra("y");
		names = (TextView)findViewById(R.id.textView1);
		back = (ImageView)findViewById(R.id.imageView1);
		names.setText(name);
		listview = (ListView)findViewById(R.id.listView1);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Home_SurroundActivity.this.finish();
				
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent  myma = new Intent(Home_SurroundActivity.this,MyNavigationActivity.class);
				String ing = hss.get(arg2).getIng();
				String lat = hss.get(arg2).getLat();
				String tname = hss.get(arg2).getName();
				myma.putExtra("fname", fname);
				myma.putExtra("fx", x);
				myma.putExtra("fy", y);
				myma.putExtra("name", tname.toString());
				myma.putExtra("x", lat.toString());
				myma.putExtra("y", ing.toString());
				startActivity(myma);
			}

	});
}
	private void initThread() {
		x = getIntent().getStringExtra("x");
		y =  getIntent().getStringExtra("y");
		params = new HashMap<String,String>();
		params.put("","");
		String strWord = name;
		String strlocation = x+","+y;
		url = url +"?ak=b6hmuehxg8vd0RpkWVMQ7rBw&radius=2000&output=json&location="+strlocation+"&query="+strWord;
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					if(NetWorkUtil.isNetAvailable(Home_SurroundActivity.this)){
						ss = new HttpP().sendPOSTRequestHttpClient(url, params);
						hss =getJSon(ss);
						handler.sendEmptyMessage(1);
					}else {
						handler.sendEmptyMessage(0);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}).start();

	}

	public List<HomeSurround> getJSon(String str){
		List<HomeSurround> hss = new ArrayList<HomeSurround>();
		try {
			JSONObject jobj = new JSONObject(str);
			JSONArray  jarr = jobj.optJSONArray("results");
			for (int i = 0; i < jarr.length(); i++) {
				JSONObject  obj = jarr.optJSONObject(i);
				String name = obj.optString("name");
				JSONObject localtion = obj.optJSONObject("location");
				String lat = localtion.optString("lat");
				String lng = localtion.optString("lng");
//				System.out.println("我是lat"+lat+"我是lng"+lng);
				HomeSurround hs = new HomeSurround(lat, lng, name);
				hss.add(hs);
			}
		} catch (JSONException e) {
			Log.d(TAG, e.getMessage()+"");
		}
		
		return hss;
	}
}
