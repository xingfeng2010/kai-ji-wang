package com.wenyu.kaijiw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

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
import com.wenyu.kjw.adapter.ShopIntroduceAdapter;

public class EquipsNearby  extends FragmentActivity{
	//	private RelativeLayout simimar;
	private ImageView back,share,shouc;
	private ListView listview;
	private Button havelook;
	private ScreenManager sm;
	private List<IntroData> li;
	private String url = Urls.LightNearbyActivityEquip;
	private String ss,intentid,category,intentcity;
	private Map<String,String>params;
	private MyGallery gallery;
	private ImageAdapter imageAdapter;
	private ArrayList<String> data,data2;
	final UMSocialService mController =UMServiceFactory.getUMSocialService("com.umeng.share");
	private String shoucurl =ConstantClassField.SERVICE_URL+"service/addEnshrine";
	private String removecurl =ConstantClassField.SERVICE_URL+"service/removeEnshrine";
	private CustomerSQLiteOpenHelper helper;
	private SQLiteDatabase db;
	private TextView address,count;
	private int customer_id;
	private TextView counts;

	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				Toast.makeText(EquipsNearby.this, "请连接网络", 1000).show();
				break;
			case 1:
				data2 = new ArrayList<String>();
				data2.add(getIntent().getStringExtra("pic"));
				imageAdapter = new ImageAdapter(EquipsNearby.this,data2);
				gallery.setAdapter(imageAdapter);
				address.setText(li.get(0).getAddress());
				counts.setText(li.get(0).getCount()+"家店出租");
				count.setText("共"+data.size()+"张图片");
				ShopIntroduceAdapter sa = new ShopIntroduceAdapter(li, EquipsNearby.this);
				listview.setAdapter(sa);
				break;
			case 3:
				Toast.makeText(EquipsNearby.this, "收藏成功", 1000).show();
				break;
			case 4:
				Toast.makeText(EquipsNearby.this, "取消不成功", 1000).show();
				break;
			case 5:
				Toast.makeText(EquipsNearby.this, "取消收藏", 1000).show();
				break;

			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.equips_details);
		initView();
		shareview();
		initThread();
	}


	private void initThread() {
		params = new HashMap<String,String>();
		params.put("id", intentid);
		params.put("userid", customer_id+"");
		params.put("category",category);
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					if(NetWorkUtil.isNetAvailable(EquipsNearby.this)){
						ss = new HttpP().sendPOSTRequestHttpClient(url, params);
						li =getJSon(ss);
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
	private void shareview() {
		// 设置分享内容
		mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
		//人人网分享时
		mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
		share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 是否只有已登录用户才能打开分享选择页
				mController.openShare(EquipsNearby.this, false);
			}
		});

		//分享到微信
		String appID = "1104528162";
		String appSecret = "S8L9EagzQX5ga3ID";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(EquipsNearby.this,appID,appSecret);
		wxHandler.addToSocialSDK();
		//qq空间
		//参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(EquipsNearby.this, "1104528162",
				"S8L9EagzQX5ga3ID");
		qZoneSsoHandler.addToSocialSDK();
		//设置新浪SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		//设置腾讯微博SSO handler
		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());



	}

	private void initView() {
		sm =  ScreenManager.gerScreenManager();
		sm.pushActivity(EquipsNearby.this);
		helper = new CustomerSQLiteOpenHelper(EquipsNearby.this);
		customer_id= BaoyzApplication.getInstance().customer_id;
		db = helper.getWritableDatabase();
		counts = (TextView) findViewById(R.id.equipDetailTextView3);
		gallery = (MyGallery) findViewById(R.id.gy);
		data = new ArrayList<String>();
		address =(TextView) findViewById(R.id.textView2);
		count =(TextView) findViewById(R.id.textView3);
		back = (ImageView) findViewById(R.id.equipDetailImageView1);
		shouc = (ImageView) findViewById(R.id.equipDetailImageView3);
		share = (ImageView) findViewById(R.id.equipDetailImageView2);
		listview = (ListView) findViewById(R.id.equipDetailListview);
		listview.setFocusable(false);
		havelook = (Button) findViewById(R.id.equipDetailButton1);
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent  pictures = new Intent(EquipsNearby.this,PictureActivity.class);
				pictures.putExtra("pictures", data);
				startActivity(pictures);
			}
		});
		shouc.setOnClickListener(new OnClickListener() {

			int countnum=1;
			@Override
			public void onClick(View arg0) {
				try{
					if(countnum%2==0){
						String dbid = li.get(0).getTempid();
						canclConnect(customer_id+"",dbid);
					}else{
						String dbid = li.get(0).getTempid();
						String type ="器材";
						getConnect(customer_id+"",dbid,type);
					}

				}finally{

				}	 
				countnum++;
			}
		});


		Intent it1 = getIntent();
		intentid = it1.getStringExtra("id");
		category = it1.getStringExtra("category");
		intentcity = it1.getStringExtra("name");

		back.setOnClickListener(ocl);
		havelook.setOnClickListener(ocl);
	}

	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/**使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
		if(ssoHandler != null){
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}
	OnClickListener ocl = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			switch(arg0.getId()){
			case R.id.equipDetailImageView1:
				sm.popActivity(sm.currentActivity());
				break;
			case R.id.equipDetailButton1:
				Intent intent2 = new Intent(EquipsNearby.this,EquipsProductActivity.class);
				String intentid = li.get(0).getTempid();
				if(("").equals(intentid)){

				}else{
					intent2.putExtra("category", category);
					intent2.putExtra("id",intentid);
					intent2.putExtra("name", intentcity);
					startActivity(intent2);
				}

				break;
			}

		}

	};
	private  void canclConnect(String customer_id,String id){
		params = new HashMap<String,String>();
		params.put("customer_id",customer_id);
		params.put("recordid", id);
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					if(NetWorkUtil.isNetAvailable(EquipsNearby.this)){
						ss = new HttpP().sendPOSTRequestHttpClient(removecurl, params);
						if("success".equals(ss)){
							handler.sendEmptyMessage(5);
						}else{
							handler.sendEmptyMessage(4);	
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
	private  void getConnect(String customerid,String id,String type){
		params = new HashMap<String,String>();
		if(customerid!=null && id!=null && type!=null){


			params.put("customer_id",customerid);
			params.put("reference_id",id);
			params.put("type",type);
			new Thread(new Runnable(){

				@Override
				public void run() {
					try {
						if(NetWorkUtil.isNetAvailable(EquipsNearby.this)){
							ss = new HttpP().sendPOSTRequestHttpClient(shoucurl, params);
							if("success".equals(ss)){
								handler.sendEmptyMessage(3);
							}else{
								handler.sendEmptyMessage(4);	
							}
						}else {
							handler.sendEmptyMessage(0);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}).start();
		}else{

		}
	}
	public List<IntroData> getJSon(String str){
		li = new ArrayList<IntroData>();
		List<IntronameData> inname = new ArrayList<IntronameData>();
		List<IntrovalueData> invalue = new ArrayList<IntrovalueData>();
		JSONObject jo;
		try {
			jo = new JSONObject(str);
			String count = jo.optString("count");
			String cdname = jo.optString("name");
			JSONArray jna = jo.optJSONArray("image");
			String gid = jo.optString("id");
			for (int i = 0; i < jna.length(); i++) {
				String image = jna.optString(i);
				data.add(image);
			}
			JSONArray   ja = jo.optJSONArray("fields");
			for (int j = 0; j < ja.length(); j++) {
				JSONObject  objj = ja.optJSONObject(j);

				JSONArray arr = objj.optJSONArray("array");
				for (int i = 0; i < arr.length(); i++) {
					JSONObject ob = arr.optJSONObject(i);
					String name =ob.optString("name");
					String value = ob.optString("value");
					IntronameData intrname= new IntronameData(name);
					IntrovalueData intrvalue= new IntrovalueData(value);
					inname.add(intrname);
					invalue.add(intrvalue);
					IntroData id = 	new IntroData(inname, invalue, gid, cdname,count);
					li.add(id);
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return li;

	}
}
