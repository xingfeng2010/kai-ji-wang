package com.wenyu.kaijiw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.FindFour;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.ScreenManager;
import com.wenyu.Data.Similar;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.Utils.UrlToImage;
import com.wenyu.kaijiw.fragment.FindIntroFragment;
import com.wenyu.kaijiw.fragment.FindVistFragment;
import com.wenyu.kjw.adapter.MySadapter;

public class FindFouractivity extends FragmentActivity {
	private  ViewPager viewpager;
	private String TAG = FindFouractivity.class.getSimpleName();
	private String url = ConstantClassField.SERVICE_URL+"service/organization";
	private Map<String,String> paramsValue;
	private FindIntroFragment fi ;
	private FindVistFragment fv ;
	private List<Fragment> frags;
	private MySadapter mf;
	private ScreenManager sm;
	private ImageView back,myphoto;
	private String intentid;
	private RadioButton btn1,btn2;
	private  View v,f;
	private List<com.wenyu.Data.Similar> fyslist;
	private Similar fys;
	private RequestQueue queue;
	private String ss;
	private FindFour  ff;
	private TextView connact,namecompany,address;
	Handler  handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				try {
					queue =  Volley.newRequestQueue(FindFouractivity.this);	
					UrlToImage.getImage(ff.getImage(),queue,myphoto);
					connact.setText(ff.getContact());
					namecompany.setText(ff.getName());
					address.setText(ff.getAddress());

					Bundle bundle = new Bundle();
					bundle.putString("id", ff.getId());
					fi.setArguments(bundle);
					fv.setArguments(bundle);
					frags.add(fi);
					frags.add(fv);
					mf = new MySadapter(getSupportFragmentManager(),frags);
					viewpager.setAdapter(mf);
				} catch (Exception e) {
					Log.d(TAG, e.getMessage()+"");
				}	     
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_four);
		initView();
		movesPeople();
	}

	private void movesPeople() {
		new Thread(new Runnable(){

			@Override
			public void run() {

				try {
					paramsValue=new HashMap<String, String>();  
					paramsValue.put("id", intentid);
					paramsValue.put("userid","2");
					paramsValue.put("category","影视公司");

//					System.out.println("我是详情界面"+intentid);
					if(NetWorkUtil.isNetAvailable(FindFouractivity.this)){
						ss = new HttpP().sendPOSTRequestHttpClient(url,paramsValue);
						if(("").equals(ss)){
							Toast.makeText(FindFouractivity.this, "正在加载。。。", 1000).show();
						}else{
							ff = getJson(ss);
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

	private void initView() {
		back =(ImageView) findViewById(R.id.imageView1);
		sm =  ScreenManager.gerScreenManager();
		sm.pushActivity(FindFouractivity.this);
		fyslist = new ArrayList<Similar>();
		Intent it = getIntent();
		fys =(Similar) it.getSerializableExtra("key");
		intentid = it.getStringExtra("id");
		fyslist.add(fys);
		myphoto = (ImageView)findViewById(R.id.mycountimageView2);
		address = (TextView)findViewById(R.id.address);
		namecompany = (TextView)findViewById(R.id.namecompany);
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		connact = (TextView) findViewById(R.id.textView2);
		btn1 = (RadioButton) findViewById(R.id.rbtn1);
		btn2 = (RadioButton) findViewById(R.id.rbtn2);
		btn1.setOnClickListener(ocl);
		btn2.setOnClickListener(ocl);
		back.setOnClickListener(ocl);
		frags =	new ArrayList<Fragment>();
		fi  =	new FindIntroFragment();
		fv  =	new FindVistFragment();




		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				switch(arg0){
				case 0:
					btn1.setChecked(true);
					btn1.setBackgroundResource(R.drawable.redline30);
					btn2.setBackground(null);
					break;
				case 1:
					btn2.setChecked(true);
					btn2.setBackgroundResource(R.drawable.redline30);
					btn1.setBackground(null);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}
	OnClickListener  ocl =	new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch(arg0.getId()){
			case R.id.rbtn1:
				viewpager.setCurrentItem(0);
				btn1.setChecked(true);
				btn1.setBackgroundResource(R.drawable.redline30);
				btn1.setWidth(30);
				break;
			case R.id.rbtn2:
				viewpager.setCurrentItem(1);
				btn2.setChecked(true);
				btn2.setBackgroundResource(R.drawable.redline30);
				btn2.setWidth(40);
				btn1.setBackground(null);
				break;
			case R.id.imageView1:
				sm.popActivity(sm.currentActivity());
				break;
			}

		}
	};

	public  FindFour  getJson(String str){
		JSONObject jo;
		try {
			jo = new JSONObject(str);
			String id = jo.optString("id");
			String name = 	jo.optString("name");
			String address = jo.optString("address");
			String phone = jo.optString("telephone");
			String  image = jo.optString("image");
			String contact =jo.optString("contact");
			ff =  new  FindFour(id,name,address,contact,image,phone);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ff;

	}

}
