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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.BaoyzApplication;
import com.example.mywork.util.NetWorkUtil;
import com.example.test.ImageAdapter;
import com.example.test.MyGallery;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.IntroData;
import com.wenyu.Data.IntronameData;
import com.wenyu.Data.IntrovalueData;
import com.wenyu.Data.ScreenManager;
import com.wenyu.Data.Urls;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.db.CustomerSQLiteOpenHelper;
import com.wenyu.kaijiw.fragment.Home_IntroFragment;
import com.wenyu.kaijiw.fragment.Home_MatingFragment;
import com.wenyu.kaijiw.fragment.Home_SurroundFragment;
import com.wenyu.kjw.adapter.MySadapter;

public class Nearby extends FragmentActivity {
	private String url = ConstantClassField.SERVICE_URL + "service/place",
			urlEnshrine = Urls.Url_addEnshrine;
	private String shouc = ConstantClassField.SERVICE_URL
			+ "service/addEnshrine";
	private ViewPager viewpager;
	private List<Fragment> frags;
	private Home_IntroFragment hl;
	private Home_MatingFragment hmf;
	private Home_SurroundFragment hmfs;
	private ImageView image, back;
	RelativeLayout sendmessage;
	private Context context;
	private ScreenManager sm;
	private RadioButton button1, button2, button3;
	RelativeLayout phone;
	private ImageView connect;
	private Map<String, String> params;
	List<IntroData> li;
	private String ss, intentid, category;
	private CustomerSQLiteOpenHelper helper;
	private int customer_id;
	private SQLiteDatabase db;
	private MyGallery gallery;
	private ImageAdapter imageAdapter;
	private ArrayList<String> data, data2;
	private TextView companyname, address, count, textView4;
	private Button storeTextView3;
	TextView textView2;
	private MySadapter mzf;
	private ScrollView scroll;
	private String removecurl =ConstantClassField.SERVICE_URL+"service/removeEnshrine";
	final UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.share");

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(Nearby.this, "请连接网络", 1000).show();
				break;
			case 2:
				String pic = getIntent().getStringExtra("picture");
				data2 = new ArrayList<String>();
				data2.add(pic);
				if(data2.size()>0){
					imageAdapter = new ImageAdapter(Nearby.this, data2);
				}else{
					imageAdapter = new ImageAdapter(Nearby.this, data);
				}
				gallery.setAdapter(imageAdapter);
				for (int i = 0; i < li.size(); i++) {
					companyname.setText(li.get(i).getCdname());
					address.setText(li.get(i).getAddress());
					count.setText("共" + data.size() + "张图片");
					if(BaoyzApplication.getInstance().isApprove){
						textView2.setText(li.get(i).getAddress());
						textView2.setClickable(true);
					}else{
						textView2.setText("地址详情认证后可见");
						textView2.setClickable(false);
					}

				}
				break;
			case 3:
				Toast.makeText(Nearby.this, "收藏成功", 1000).show();
				break;
			case 5:
				Toast.makeText(Nearby.this, "取消收藏", 1000).show();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_xiangqing);
		context = Nearby.this;
		initView();
		initValue();
		shareview();
		initThread();
		initConnect();
	}
	
	// 给各控件赋值或设置监听
	private void initValue() {
		data = new ArrayList<String>();
		Intent it = getIntent();
		intentid = it.getStringExtra("id");
		category = it.getStringExtra("category");
		customer_id = BaoyzApplication.getInstance().customer_id;
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent pictures = new Intent(Nearby.this, PictureActivity.class);
				pictures.putExtra("pictures", data);
				startActivity(pictures);
			}
		});
		frags = new ArrayList<Fragment>();
		hl = new Home_IntroFragment();
		hmf = new Home_MatingFragment();
		hmfs = new Home_SurroundFragment();
		frags.add(hl);
		frags.add(hmf);
		frags.add(hmfs);
		mzf = new MySadapter(getSupportFragmentManager(), frags);
		viewpager.setAdapter(mzf);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
				case 0:
					button2.setChecked(true);
					button2.setBackgroundResource(R.drawable.redline30);
					button3.setBackground(null);
					button1.setBackground(null);
					break;
				case 1:
					button1.setChecked(true);
					button1.setBackgroundResource(R.drawable.redline30);
					button2.setBackground(null);
					button3.setBackground(null);
					break;
				case 2:
					button3.setChecked(true);
					button3.setBackgroundResource(R.drawable.redline30);
					button1.setBackground(null);
					button2.setBackground(null);
				}
				scroll.smoothScrollTo(0, 0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				scroll.smoothScrollTo(0, 0);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		Bundle bundle = new Bundle();
		bundle.putString("id", intentid);
		hl.setArguments(bundle);
		hmf.setArguments(bundle);
		hmfs.setArguments(bundle);
		handler.sendEmptyMessage(1);
		image.setOnClickListener(ocl);
		back.setOnClickListener(ocl);
		button1.setOnClickListener(ocl);
		button2.setOnClickListener(ocl);
		button3.setOnClickListener(ocl);
		textView2.setOnClickListener(ocl);
		storeTextView3.setOnClickListener(ocl);
		textView4.setOnClickListener(ocl);
	}
	//调用收藏和取消收藏
	private void initConnect() {
		connect.setOnClickListener(new OnClickListener() {
			int countnum = 1;

			@Override
			public void onClick(View arg0) {
				try {
					if (countnum % 2 == 0) {
						String dbid = li.get(0).getId();
						canclConnect(customer_id + "", dbid);
					} else {
						String dbid = li.get(0).getId();
						String type = "场地";
						getConnect(customer_id + "", dbid, type);
					}

				} finally {

				}
				countnum++;
			}
		});


	}
	// 访问网络数据
	private void initThread() {
		params = new HashMap<String, String>();
		params.put("id", intentid);
		params.put("userid", customer_id + "");


		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					if (NetWorkUtil.isNetAvailable(Nearby.this)) {
						ss = new HttpP().sendPOSTRequestHttpClient(url, params);
						li = getJSon(ss);
						handler.sendEmptyMessage(2);
					} else {
						handler.sendEmptyMessage(0);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}).start();

	}

	private void shareview() {
		// 设置分享内容
		mController
		.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
		// 人人网分享时
		mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
				SHARE_MEDIA.DOUBAN);
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 是否只有已登录用户才能打开分享选择页
				mController.openShare(Nearby.this, false);
			}
		});

		// 分享到微信
		String appID = "1104528162";
		String appSecret = "S8L9EagzQX5ga3ID";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(Nearby.this, appID, appSecret);
		wxHandler.addToSocialSDK();
		// qq空间
		// 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(Nearby.this,
				"1104528162", "S8L9EagzQX5ga3ID");
		qZoneSsoHandler.addToSocialSDK();
		// 设置新浪SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		// 设置腾讯微博SSO handler
		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

	}

	@SuppressLint("ShowToast")
	//初始化控件
	private void initView() {
		scroll = (ScrollView) findViewById(R.id.scroll_details);
		scroll.smoothScrollTo(0, 0);
		sm = ScreenManager.gerScreenManager();
		sm.pushActivity(Nearby.this);
		helper = new CustomerSQLiteOpenHelper(context);
		db = helper.getWritableDatabase();
		gallery = (MyGallery) findViewById(R.id.gy);
		companyname = (TextView) findViewById(R.id.companyname);
		address = (TextView) findViewById(R.id.address);
		count = (TextView) findViewById(R.id.textView3);
		button1 = (RadioButton) findViewById(R.id.button1);
		button2 = (RadioButton) findViewById(R.id.button2);
		button2.setBackgroundResource(R.drawable.redline30);
		button3 = (RadioButton) findViewById(R.id.button3);
		storeTextView3 = (Button) findViewById(R.id.storeTextView3);
		textView2 = (TextView) findViewById(R.id.textView2);
		sendmessage = (RelativeLayout) findViewById(R.id.sendmessage);
		back = (ImageView) findViewById(R.id.imageView1);
		image = (ImageView) findViewById(R.id.imageView2);
		connect = (ImageView) findViewById(R.id.imageView3);
		textView4 = (TextView) findViewById(R.id.textView4);
		phone = (RelativeLayout) findViewById(R.id.phoneon);
		viewpager = (ViewPager) findViewById(R.id.viewpager);


	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	OnClickListener ocl = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.button1:
				viewpager.setCurrentItem(1);
				scroll.smoothScrollTo(0, 0);
				button1.setBackgroundResource(R.drawable.redline30);
				button2.setBackground(null);
				button3.setBackground(null);
				break;
			case R.id.button2:
				viewpager.setCurrentItem(0);
				scroll.smoothScrollTo(0, 0);
				button2.setBackgroundResource(R.drawable.redline30);
				button1.setBackground(null);
				button3.setBackground(null);
				break;
			case R.id.button3:
				viewpager.setCurrentItem(2);
				scroll.smoothScrollTo(0, 0);
				button3.setBackgroundResource(R.drawable.redline30);
				button2.setBackground(null);
				button1.setBackground(null);
				break;
			case R.id.imageView1:
				sm.popActivity(sm.currentActivity());
				break;
			case R.id.textView2: 
				Intent it = new Intent(Nearby.this, MyMapActivity.class);
				if (("").equals(li.get(0).getX())
						|| ("").equals(li.get(0).getY())) {
				} else {
					it.putExtra("x", li.get(0).getX());
					it.putExtra("y", li.get(0).getY());
					startActivity(it);
				}

				break;
			case R.id.textView4:
				if (BaoyzApplication.getInstance().isApprove) {
					if (("null").equals(li.get(0).getSend_to_id())) {
						Toast.makeText(Nearby.this, "用户不存在", 1000).show();
					} else {
						Intent its = new Intent(Nearby.this,
								IMChatActivity.class);
						its.putExtra("CustomUserID", li.get(0).getSend_to_id());
						its.putExtra("name", li.get(0).getContact());

						startActivity(its);
					}
				} else {
					new AlertDialog.Builder(Nearby.this).setMessage("请先认证")
					.setPositiveButton("确定", null).show()
					.setCanceledOnTouchOutside(false);
				}

				break;
			case R.id.storeTextView3:
				new AlertDialog.Builder(Nearby.this)
				.setMessage("4001166396")
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						Intent intent = new Intent(
								Intent.ACTION_DIAL, Uri
								.parse("tel:"
										+ "4001166396"));
						startActivity(intent);
					}
				}).setNegativeButton("取消", null).show();
				break;
			}

		}
		//
	};
	//取消收藏
	private void canclConnect(String customer_id, String id) {
		params = new HashMap<String, String>();
		params.put("customer_id", customer_id);
		params.put("recordid", id);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					if (NetWorkUtil.isNetAvailable(Nearby.this)) {
						ss = new HttpP().sendPOSTRequestHttpClient(removecurl,
								params);
						if ("success".equals(ss)) {
							handler.sendEmptyMessage(5);
						} else {
							handler.sendEmptyMessage(4);
						}
					} else {
						handler.sendEmptyMessage(0);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}).start();
	}
	//收藏
	private void getConnect(String customerid, String id, String type) {
		if(customerid!=null && id!=null && type!=null){
			params = new HashMap<String, String>();
			params.put("customer_id", customerid);
			params.put("reference_id",id);
			params.put("type", type);
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						if (NetWorkUtil.isNetAvailable(Nearby.this)) {
							ss = new HttpP().sendPOSTRequestHttpClient(shouc,
									params);
							if ("success".equals(ss)) {
								handler.sendEmptyMessage(3);
							} else {
								handler.sendEmptyMessage(4);
							}
						} else {
							handler.sendEmptyMessage(0);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}).start();
		}
	}
	//解析数据
	public List<IntroData> getJSon(String str) {
		li = new ArrayList<IntroData>();
		List<IntronameData> inname = new ArrayList<IntronameData>();
		List<IntrovalueData> invalue = new ArrayList<IntrovalueData>();
		JSONObject jo;
		try {
			jo = new JSONObject(str);

			String cdname = jo.optString("name");
			JSONArray jna = jo.optJSONArray("image");
			String gid = jo.optString("id");
			String send_to_id = jo.optString("send_to_id");
			String address = jo.optString("address");
			String contact = jo.optString("contact");
			String telephone = jo.optString("telephone");
			String regional = jo.optString("regional");
			String teampid = jo.optString("teampid");
			String type = jo.optString("type");
			String x = jo.optString("coordinate_Y");
			String y = jo.optString("coordinate_X");
			for (int i = 0; i < jna.length(); i++) {
				String image = jna.optString(i);
				data.add(image);
			}
			JSONArray ja = jo.optJSONArray("fields");
			for (int j = 0; j < ja.length(); j++) {
				JSONObject objj = ja.optJSONObject(j);

				JSONArray arr = objj.optJSONArray("array");
				for (int i = 0; i < arr.length(); i++) {
					JSONObject ob = arr.optJSONObject(i);
					String name = ob.optString("name");
					String value = ob.optString("id");
					IntronameData intrname = new IntronameData(name);
					IntrovalueData intrvalue = new IntrovalueData(value);
					inname.add(intrname);
					invalue.add(intrvalue);
					IntroData ids = new IntroData(inname, invalue, contact,
							telephone, address, regional, teampid, cdname, gid,
							x, y, send_to_id, type);
					li.add(ids);
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return li;

	}
}
