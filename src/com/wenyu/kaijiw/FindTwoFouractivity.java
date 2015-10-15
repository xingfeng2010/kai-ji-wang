package com.wenyu.kaijiw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.BaoyzApplication;
import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.Find_yingshi;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.ReFindXiang;
import com.wenyu.Data.ScreenManager;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.Utils.NotificationUtil;
import com.wenyu.Utils.UrlToImage;
import com.wenyu.kaijiw.fragment.FindIntroFragment;
import com.wenyu.kaijiw.fragment.FindVistFragment;
import com.wenyu.kjw.adapter.MySadapter;

public class FindTwoFouractivity extends FragmentActivity {
	private  ViewPager viewpager;
	private String url = ConstantClassField.SERVICE_URL+"service/talent/detail"; 
	private Map<String,String> paramsValue;
	private Find_yingshi fy1;
	private FindIntroFragment fi ;
	private FindVistFragment fv ;
	private List<Fragment> frags;
	private List<Find_yingshi>fis;
	private ImageView back;
	private MySadapter mf;
	private ScreenManager sm;
	private RadioButton btn1,btn2;
	private RequestQueue queue;
	private String ss,id;
	TextView namecompany,ageview,cityview,positionview;
	private Button manBut;
	private ImageView myphoto;
	private List<String> namelist,valuelist;
	private List<ReFindXiang> rfxlist;
	private String name;
	private int customer_id;
	Handler  handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				queue =  Volley.newRequestQueue(FindTwoFouractivity.this);	
				try {
					UrlToImage.getImage(getIntent().getStringExtra("image"),queue,myphoto);
					for (int i = 0; i < rfxlist.size(); i++) {
						name = 	rfxlist.get(i).getName();
//						String age = rfxlist.get(i).getAge();
//						String address = rfxlist.get(i).getAddress();
						String position = rfxlist.get(i).getPosition();
						namecompany.setText(name);
						manBut.setText(name);
//						ageview.setText(age);
//						cityview.setText(address);
						positionview.setText(position);
					}

				} catch (Exception e) {
					e.printStackTrace();
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
					paramsValue.put("id", id);
					paramsValue.put("userid", customer_id+"");
					paramsValue.put("category", "影视人才");
					if(NetWorkUtil.isNetAvailable(FindTwoFouractivity.this)){
						ss = new HttpP().sendPOSTRequestHttpClient(url,paramsValue);
						rfxlist = getList(ss);
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

	@SuppressLint("CutPasteId")
	private void initView() {
		customer_id=  BaoyzApplication.getInstance().customer_id;
		id = getIntent().getStringExtra("id");
		sm =  ScreenManager.gerScreenManager();
		sm.pushActivity(FindTwoFouractivity.this);
		back= (ImageView) findViewById(R.id.imageView1);
		myphoto = (ImageView)findViewById(R.id.mycountimageView2);
		namecompany  = (TextView) findViewById(R.id.namecompany);
//		ageview = (TextView)findViewById(R.id.age);
//		cityview = (TextView) findViewById(R.id.city);
		manBut = (Button)findViewById(R.id.textView2);
		manBut.setOnClickListener(ocl);
		findViewById(R.id.textView3).setOnClickListener(ocl);
		positionview = (TextView) findViewById(R.id.potision);
		sm.pushActivity(FindTwoFouractivity.this);
		fis = new ArrayList<Find_yingshi>();
		Intent it = getIntent();
		fy1 =(Find_yingshi) it.getSerializableExtra("key");
		fis.add(fy1);
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		btn1 = (RadioButton) findViewById(R.id.rbtn1);
		btn2 = (RadioButton) findViewById(R.id.rbtn2);
		btn1.setBackgroundResource(R.drawable.redline30);
		btn1.setOnClickListener(ocl);
		btn2.setOnClickListener(ocl);
		back.setOnClickListener(ocl);
		List<Fragment> frags =	new ArrayList<Fragment>();
		fi  =	new FindIntroFragment();
		fv  =	new FindVistFragment();
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.classgroup_fragment, fi);
		ft.commit();
		frags.add(fi);
		frags.add(fv);
		mf = new MySadapter(getSupportFragmentManager(),frags);
		viewpager.setAdapter(mf);
		Bundle bundle = new Bundle();
		bundle.putString("id", id);
		fi.setArguments(bundle);
		fv.setArguments(bundle);
		

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
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			switch(arg0.getId()){
			case R.id.rbtn1:
//				viewpager.setCurrentItem(0);
//				btn1.setChecked(true);
				ft.replace(R.id.classgroup_fragment, fi);
				ft.commit();
				btn1.setBackgroundResource(R.drawable.redline30);
				btn2.setBackground(null);
				break;
			case R.id.rbtn2:
//				viewpager.setCurrentItem(1);
//				btn2.setChecked(true);
				ft.replace(R.id.classgroup_fragment, fv);
				ft.commit();
				btn2.setBackgroundResource(R.drawable.redline30);
				btn1.setBackground(null);
				break;
			case R.id.imageView1:
				sm.popActivity(sm.currentActivity());
				break;
			case R.id.textView2:
//				NotificationUtil.getInstance(FindTwoFouractivity.this).notificationMsgCome();
				if(BaoyzApplication.getInstance().isApprove){
					if(TextUtils.equals(receive_phone, "1")){
						
						new AlertDialog.Builder(FindTwoFouractivity.this)
						.setMessage(telphone)
						.setPositiveButton("呼叫", new DialogInterface.OnClickListener() {		
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + telphone));  
								startActivity(intent);
							}
						})
						.setNegativeButton("取消", null)
						.show();

					}else{
						new AlertDialog.Builder(FindTwoFouractivity.this)
						.setMessage("对方拒绝接电话")
						.setPositiveButton("确定", null)
						.show().setCanceledOnTouchOutside(false);
					}
				}else{
					new AlertDialog.Builder(FindTwoFouractivity.this)
					.setMessage("请先认证")
					.setPositiveButton("确定", null)
					.show().setCanceledOnTouchOutside(false);
				}



				break;

			case R.id.textView3:

				//				if(BaoyzApplication.getInstance().isApprove){
				Intent inent = new Intent(FindTwoFouractivity.this,IMChatActivity.class);
				inent.putExtra("CustomUserID", sendtoid);
				startActivity(inent);
				//				}else{
				//					new AlertDialog.Builder(FindTwoFouractivity.this)
				//					.setMessage("请先认证")
				//					.setPositiveButton("确定", null)
				//					.show().setCanceledOnTouchOutside(false);
				//				}


				break;
			}

		}
	};
	private String telphone;
	private String receive_phone;
	String sendtoid;
	public List<ReFindXiang>  getList(String  str){

		List<ReFindXiang> rfxlist = new ArrayList<ReFindXiang>();
		namelist = new ArrayList<String>();
		valuelist = new ArrayList<String>();
		try {
			JSONObject  jobj = new JSONObject(str);
			String nameCn = jobj.optString("nameCn");
			String age = jobj.optString("age");
			//			String image = jobj.optString("image");
			String address = jobj.optString("address");
			String position = jobj.optString("position");
			telphone = jobj.optString("telphone");
			receive_phone = jobj.optString("receive_phone");

			sendtoid = jobj.optString("send_to_id");
			JSONArray arrfilm = jobj.optJSONArray("film");
			for (int i = 0; i < arrfilm.length(); i++) {
				JSONObject  filmObj = arrfilm.optJSONObject(i);
				String year = filmObj.optString("year");
				String filmtype = filmObj.optString("filmType");
				//String position = filmObj.optString("position");
			}
			JSONArray arrfields = jobj.optJSONArray("fields");
			for (int i = 0; i < arrfields.length(); i++) {
				JSONObject  objfields = arrfields.optJSONObject(i);
				JSONArray introarr = objfields.optJSONArray("array");
				for (int j = 0; j < introarr.length(); j++) {
					JSONObject  job = introarr.optJSONObject(j);
					String name = job.optString("name");
					String value = job.optString("value");
					namelist.add(name);
					valuelist.add(value);
					ReFindXiang rfx = new ReFindXiang(nameCn, age, address, position, telphone, sendtoid, namelist, valuelist);
					rfxlist.add(rfx);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return rfxlist;
	}
}
