package com.wenyu.kaijiw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.wenyu.kjw.adapter.ShopIntroduceAdapter;
import com.wenyu.kjw.adapter.SliAdapter;

public class LightNearby  extends FragmentActivity{
	private String url = Urls.LightNearbyActivity,urlIM = Urls.Url_getIMID,urlInfo = Urls.Url_getIMCustomerInfo;
	private ImageView back,share;
	private RelativeLayout phone,sendmessage,mapid;
	private ListView listview2;
	private ArrayList<String>data,data2;
	private ScreenManager sm;
	private List<IntroData> li;
	private String ss,intentid,category,intentcity,im,imID,imInfo,imResults,customer_id;
	private Map<String,String>params,paramValue;
	private  ShopIntroduceAdapter sia ; 
	private TextView loacaladdress,lastname,shopname, count,textView4;
	private MyGallery gallery;
	private ImageAdapter imageAdapter;
	private List<IntronameData> inname;
	private List<IntrovalueData> invalue;
	private List<String> strname;
	private CustomerSQLiteOpenHelper helper;
	private SQLiteDatabase db;
	private ImageView connect;
	private String shouc =ConstantClassField.SERVICE_URL+"service/addEnshrine";
	private String removecurl =ConstantClassField.SERVICE_URL+"service/removeEnshrine";
	final UMSocialService mController =UMServiceFactory.getUMSocialService("com.umeng.share");
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				Toast.makeText(LightNearby.this, "请连接网络", 1000).show();
				break;
			case 1:
				data2 = new ArrayList<String>();
				data2.add(getIntent().getStringExtra("pic"));
				imageAdapter = new ImageAdapter(LightNearby.this,data);
				gallery.setAdapter(imageAdapter);
				loacaladdress.setText(li.get(0).getAddress());
				lastname.setText(li.get(0).getContact());
				String name = li.get(0).getCdname();
				shopname.setText(name);
				for (int i = 0; i < li.size(); i++) {
					count.setText("共"+"/"+data.size()+"图片");
					SliAdapter sa = new SliAdapter(strname,LightNearby.this);
					listview2.setAdapter(sa);
				}
				break;
			case 2:
				Intent it = new Intent(LightNearby.this, IMChatActivity.class);
				it.putExtra("CustomUserID", imID);
				it.putExtra("name", imResults.split(",")[0]);
				it.putExtra("image",imResults.split(",")[1]);
				startActivity(it);
				break;
			case 3:
				Toast.makeText(LightNearby.this, "收藏成功", 1000).show();
				break;
			case 4:
				Toast.makeText(LightNearby.this, "取消不成功", 1000).show();
				break;
			case 5:
				Toast.makeText(LightNearby.this, "取消收藏", 1000).show();
				break;
			case 6:
				Toast.makeText(LightNearby.this, "收藏不成功", 1000).show();
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stores_xiangqing);
		initView();
		initValue();
		shareview();
		initThread();
	}


	/**
	 * 控件设置监听
	 */
	private void initValue() {
		/**
		 * 收藏接口的调用
		 */
		connect.setOnClickListener(new OnClickListener() {
			int countnum=1;
			@Override
			public void onClick(View arg0) {

				try{
					if(countnum%2==0){
						String dbid = li.get(0).getId();
						canclConnect(customer_id+"",dbid);
					}else{
						String dbid = li.get(0).getId();
						String type ="店家";
						getConnect(customer_id+"",dbid,type);
					}

				}finally{

				}	 
				countnum++;
			}
		});
		/**
		 * 聊天的监听
		 */
		textView4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				/**
				 * 判断是否登陆
				 */
				if(BaoyzApplication.getInstance().isApprove){
					if(("null").equals(li.get(0).getSend_to_id())){
						Toast.makeText(LightNearby.this, "用户不存在", 1000).show();
					}else{
						// 聊天界面
						Intent it = new Intent(LightNearby.this, IMChatActivity.class);
						it.putExtra("CustomUserID",li.get(0).getSend_to_id());
						startActivity(it);
					}


				}else{
					new AlertDialog.Builder(LightNearby.this)
					.setMessage("请先认证")
					.setPositiveButton("确定", null)
					.show().setCanceledOnTouchOutside(false);
				}



			}
		});
		//		地图的监听
		mapid.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {


				if(BaoyzApplication.getInstance().isApprove){

					if(("").equals(li.get(0).getX()) && ("".equals(li.get(0).getY()))){

					}else{
						// 地图界面
						Intent  it = new Intent(LightNearby.this,MyMapActivity.class);
						it.putExtra("x",li.get(0).getX() );
						it.putExtra("y",li.get(0).getY());
						startActivity(it);
					}

				}else{
					new AlertDialog.Builder(LightNearby.this)
					.setMessage("请先认证")
					.setPositiveButton("确定", null)
					.show().setCanceledOnTouchOutside(false);
				}


			}
		});
		//	图片滚动监听
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				count.setText(position+1+"/"+data.size());	
				Intent  pictures = new Intent(LightNearby.this,PictureActivity.class);
				pictures.putStringArrayListExtra("pictures", data);
				startActivity(pictures);

			}
		});
		//		拨打电话监听
		lastname.setOnClickListener(new View.OnClickListener() {  

			@SuppressWarnings("deprecation")  
			@Override  
			public void onClick(View v) { 


				if(BaoyzApplication.getInstance().isApprove){

					new AlertDialog.Builder(LightNearby.this)
					.setMessage(li.get(0).getTelephone())
					.setPositiveButton("呼叫", new DialogInterface.OnClickListener() {		
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + li.get(0).getTelephone()));  
							startActivity(intent);
						}
					})
					.setNegativeButton("取消", null)
					.show();


				}else{
					new AlertDialog.Builder(LightNearby.this)
					.setMessage("请先认证")
					.setPositiveButton("确定", null)
					.show().setCanceledOnTouchOutside(false);
				}

			}  
		});
		back.setOnClickListener(ocl);
		Intent it1 = getIntent();
		intentid = it1.getStringExtra("id"); 
		category = it1.getStringExtra("category");
		intentcity = it1.getStringExtra("name");
		customer_id =it1.getStringExtra("customer_id");
		// listview列表的监听
		listview2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 跳转到产品列表界面
				Intent intent1 = new Intent(LightNearby.this,ProductActivity.class);
				intent1.putExtra("id", li.get(arg2).getId());
				intent1.putExtra("category", category);
				intent1.putExtra("type", inname.get(arg2).getName());
				startActivity(intent1);

			}
		});
	}

	/**
	 * 调用数据接口
	 */
	private void initThread() {
		params = new HashMap<String,String>();
		params.put("id", intentid);
		params.put("userid", customer_id);
		params.put("category", category);
		//		paramValue = new HashMap<String,String>();
		//		//*********************sendtoid 的值
		//		paramValue.put("send_to_id", "56");
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					if(NetWorkUtil.isNetAvailable(LightNearby.this)){
						ss = new HttpP().sendPOSTRequestHttpClient(url, params);
						//						im =  new HttpP().sendPOSTRequestHttpClient(urlIM, paramValue);
						//						//************************IM也为1
						//						imID = getIMID(im,1);
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
				mController.openShare(LightNearby.this, false);
			}
		});

		//分享到微信
		String appID = "1104528162";
		String appSecret = "S8L9EagzQX5ga3ID";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(LightNearby.this,appID,appSecret);
		wxHandler.addToSocialSDK();
		//qq空间
		//参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(LightNearby.this, "1104528162",
				"S8L9EagzQX5ga3ID");
		qZoneSsoHandler.addToSocialSDK();
		//设置新浪SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		//设置腾讯微博SSO handler
		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());



	}
	/**
	 * 初始化控件
	 */
	private void initView() {
		sm =  ScreenManager.gerScreenManager();
		sm.pushActivity(LightNearby.this);
		helper = new CustomerSQLiteOpenHelper(LightNearby.this);
		db = helper.getWritableDatabase();
		shopname = (TextView)findViewById(R.id.name);
		count = (TextView)findViewById(R.id.textView3);
		textView4 =(TextView)findViewById(R.id.textView4);
		mapid = (RelativeLayout) findViewById(R.id.mapid);
		connect = (ImageView) findViewById(R.id.storeImageView3);
		gallery = (MyGallery) findViewById(R.id.gy);
		sendmessage = (RelativeLayout) findViewById(R.id.stores_sendmessage);
		back = (ImageView) findViewById(R.id.storeImageView1);
		share = (ImageView) findViewById(R.id.storeImageView2);
		loacaladdress = (TextView) findViewById(R.id.storeTextView2);
		lastname = (TextView) findViewById(R.id.storeTextView3);
		sendmessage = (RelativeLayout) findViewById(R.id.sendmessage);
		phone = (RelativeLayout) findViewById(R.id.phoneon);
		listview2 = (ListView) findViewById(R.id.storelistview);

	}
	//取消收藏接口调用
	private  void canclConnect(String id,String customer_id){
		params = new HashMap<String,String>();
		params.put("customer_id", customer_id);
		params.put("recordid", id);
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					if(NetWorkUtil.isNetAvailable(LightNearby.this)){
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
	/**
	 * 收藏接口
	 * @param customer_id 用户id
	 * @param id 
	 * @param type 
	 */
	private  void getConnect(String customer_id,String id, String type){
		if(customer_id!=null && id!=null && type!=null){
			params = new HashMap<String,String>();
			params.put("customer_id", customer_id);
			params.put("reference_id", id);
			params.put("type",type);
			new Thread(new Runnable(){

				@Override
				public void run() {
					try {
						if(NetWorkUtil.isNetAvailable(LightNearby.this)){
							ss = new HttpP().sendPOSTRequestHttpClient(shouc, params);
							if("success".equals(ss)){
								handler.sendEmptyMessage(3);
							}else{
								handler.sendEmptyMessage(6);	
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
			case R.id.storeImageView1:
				sm.popActivity(sm.currentActivity());
				break;
			}

		}

	};

	/**
	 * 页面数据解析
	 * @param 传入url
	 * @return  List<IntroData>集合
	 */
	public List<IntroData> getJSon(String str){
		li = new ArrayList<IntroData>();
		data = new ArrayList<String>();
		strname = new ArrayList<String>();
		inname = new ArrayList<IntronameData>();
		invalue = new ArrayList<IntrovalueData>();
		JSONObject jo;
		try {
			jo = new JSONObject(str);
			String id = jo.optString("id");
			String cdname = jo.optString("name");
			String address = jo.optString("address");
			String regional = jo.optString("regional");
			String contact = jo.optString("contact");
			String teampid = jo.optString("teampid");
			String send_to_id = jo.optString("send_to_id");
			String telephone= jo.optString("telephone");
			String x = jo.optString("coordinate_Y");
			String y = jo.optString("coordinate_X");
			JSONArray   ja = jo.optJSONArray("type");
			JSONArray  ja1 = jo.optJSONArray("image");
			for (int i = 0; i < ja1.length(); i++) {
				String image = ja1.optString(i);
				data.add(image);
			}

			for (int j = 0; j < ja.length(); j++) {
				JSONObject  objj = ja.optJSONObject(j);
				String name =objj.optString("name");
				String value = objj.optString("id");
				strname.add(name);
				IntronameData intrname= new IntronameData(name);
				IntrovalueData intrvalue= new IntrovalueData(value);
				inname.add(intrname);
				invalue.add(intrvalue);
				IntroData ids= new IntroData(inname, invalue,contact,telephone,address,regional,teampid,cdname,id,x,y,send_to_id);
				li.add(ids);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return li;

	}
	//通过IMid得到用户头像名字
	//	private void getCustomerInfoByIMID(){
	//		if(!paramValue.isEmpty()){
	//			paramValue.clear();
	//		}
	//		paramValue.put("imsdk_customUserID", imID);
	//		new Thread(new Runnable(){
	//
	//			@Override
	//			public void run() {
	//				try {
	//					if(NetWorkUtil.isNetAvailable(LightNearby.this)){
	//						imInfo =  new HttpP().sendPOSTRequestHttpClient(urlInfo, paramValue);
	//						imResults = getIMID(imInfo,2);
	//						//					    System.out.println("测试结果"+imResults);
	//						handler.sendEmptyMessage(2);
	//					}else {
	//						handler.sendEmptyMessage(0);
	//					}
	//
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//
	//			}
	//
	//
	//		}).start();
	//
	//	} 
	//	private String getIMID(String im,int flag) {
	//		JSONObject jo;
	//		try{
	//			jo = new JSONObject(im);
	//			if(flag==1){
	//				String result =  jo.optString("message");
	//				return result;	 
	//			}else if(flag ==2){
	//				String name = jo.optString("name");
	//				String image = jo.optString("image");
	//				String result = name+","+image;
	//				return result;
	//			}
	//		}catch(Exception e){
	//			e.printStackTrace();
	//		}
	//		return null;
	//	}
}
