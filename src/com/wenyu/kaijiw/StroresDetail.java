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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
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
import com.wenyu.Data.IntroData2;
import com.wenyu.Data.IntronameData;
import com.wenyu.Data.IntrovalueData;
import com.wenyu.Data.ScreenManager;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.kjw.adapter.ShopIntroduceAdapter2;

public class StroresDetail  extends FragmentActivity{
	private String url = ConstantClassField.SERVICE_URL+"service/theequip";
	private ImageView simimar,back,share,shouc;
	private RelativeLayout phone;
	private ListView listview2;
	private ArrayList<String>data,data2;
	private ScreenManager sm;
	private List<IntroData> li;
	private String ss,intentid1,intentid2,category,intentcity;
	private Map<String,String>params;
	private TextView loacaladdress;
	private Button lastname;
	TextView name,count;
	TextView textView4;
	private MyGallery gallery;
	private ImageAdapter imageAdapter;
	private List<IntronameData> inname;
	private RelativeLayout mapid;
	private List<IntrovalueData> invalue;
	private String shoucs =ConstantClassField.SERVICE_URL+"service/addEnshrine";
	private String removecurl =ConstantClassField.SERVICE_URL+"service/removeEnshrine";
	private int customer_id;
	final UMSocialService mController =UMServiceFactory.getUMSocialService("com.umeng.share");

	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				Toast.makeText(StroresDetail.this, "请连接网络", 1000).show();
				break;
			case 1:
//				DisplayMetrics dm = new DisplayMetrics();
//				getWindowManager().getDefaultDisplay().getMetrics(dm);
//				int width = dm.widthPixels;
//				String pic = getIntent().getStringExtra("picture");
//				data = new ArrayList<String>();
//				data.add(pic);
				imageAdapter = new ImageAdapter(StroresDetail.this,data);
				gallery.setAdapter(imageAdapter);
//				imageAdapter = new ImageAdapter(StroresDetail.this, data, width);
//				gallery.setAdapter(imageAdapter);
				for (int i = 0; i < li.size(); i++) {
					loacaladdress.setText(li.get(i).getAddress());
					lastname.setText(li.get(i).getContact());
					name.setText(li.get(i).getCdname());
					count.setText("共"+data.size()+"张图片");
				}
				ShopIntroduceAdapter2 sa = new ShopIntroduceAdapter2(datas, StroresDetail.this);
				listview2.setAdapter(sa);
				break;
			case 3:
				Toast.makeText(StroresDetail.this, "收藏成功", 1000).show();
				break;
			case 5:
				Toast.makeText(StroresDetail.this, "取消收藏", 1000).show();
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stores_xiangqing);
		initView();
		shareview();
		initThread();
	}


	private void initThread() {
		params = new HashMap<String,String>();
		params.put("storeid", intentid2);
		params.put("userid", customer_id+"");
		params.put("equipid", intentid1);

		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					if(NetWorkUtil.isNetAvailable(StroresDetail.this)){
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
				mController.openShare(StroresDetail.this, false);
			}
		});

		//分享到微信
		String appID = "1104528162";
		String appSecret = "S8L9EagzQX5ga3ID";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(StroresDetail.this,appID,appSecret);
		wxHandler.addToSocialSDK();
		//qq空间
		//参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(StroresDetail.this, "1104528162",
				"S8L9EagzQX5ga3ID");
		qZoneSsoHandler.addToSocialSDK();
		//设置新浪SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		//设置腾讯微博SSO handler
		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());



	}

	private void initView() {
		sm =  ScreenManager.gerScreenManager();
		sm.pushActivity(StroresDetail.this);
		Intent it1 = getIntent();
		intentid2 = it1.getStringExtra("id"); 
		intentid1 = it1.getStringExtra("intentid");
		category = it1.getStringExtra("category");
		intentcity = it1.getStringExtra("name");
		gallery = (MyGallery) findViewById(R.id.gy);
		name =(TextView) findViewById(R.id.name);
		back = (ImageView) findViewById(R.id.storeImageView1);
		shouc = (ImageView) findViewById(R.id.storeImageView3);
		share = (ImageView) findViewById(R.id.storeImageView2);
		loacaladdress = (TextView) findViewById(R.id.storeTextView2);
		lastname = (Button) findViewById(R.id.storeTextView3);
		mapid = (RelativeLayout) findViewById(R.id.mapid);
		count = (TextView) findViewById(R.id.textView3);
		mapid.setOnClickListener(ocl);
		phone = (RelativeLayout) findViewById(R.id.phoneon);
		textView4 =(TextView)findViewById(R.id.textView4);
		customer_id= BaoyzApplication.getInstance().customer_id;
		textView4.setOnClickListener(ocl);
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent  pictures = new Intent(StroresDetail.this,PictureActivity.class);
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
						String type ="商品";
						getConnect(customer_id+"",dbid,type);
					}

				}finally{

				}	 
				countnum++;
			}
		});
		lastname.setOnClickListener(new View.OnClickListener() {  

			@SuppressWarnings("deprecation")  
			@Override  
			public void onClick(View v) {  
				
				if(BaoyzApplication.getInstance().isApprove){
					if(li.get(0).getTelephone()!=null){
						new AlertDialog.Builder(StroresDetail.this)
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
//					Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + li.get(0).getTelephone()));  
//					startActivity(intent);
					}

				}else{
					new AlertDialog.Builder(StroresDetail.this)
					.setMessage("请先认证")
					.setPositiveButton("确定", null)
					.show().setCanceledOnTouchOutside(false);
				}
			}  
		});
		listview2 = (ListView) findViewById(R.id.storelistview);
		listview2.setFocusable(false);
		back.setOnClickListener(ocl);

//		listview2.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				Intent intent1 = new Intent(StroresDetail.this,ProductActivity.class);
//				intent1.putExtra("id", li.get(arg2).getId());
//				intent1.putExtra("category", category);
//				intent1.putExtra("name", inname.get(arg2).getName());
//				startActivity(intent1);
//
//			}
//		});
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
			case R.id.mapid:
				
				
				
				if(BaoyzApplication.getInstance().isApprove){
					
					if(("").equals(li.get(0).getX()) && ("".equals(li.get(0).getY()))){
						
					}else{
						Intent  it = new Intent(StroresDetail.this,MyMapActivity.class);
						if(("").equals(li.get(0).getX()) && ("".equals(li.get(0).getY()))){
							
						}else{
							it.putExtra("x",li.get(0).getX() );
							it.putExtra("y",li.get(0).getY());
							startActivity(it);
						}
					}

				}else{
					new AlertDialog.Builder(StroresDetail.this)
					.setMessage("请先认证")
					.setPositiveButton("确定", null)
					.show().setCanceledOnTouchOutside(false);
				}

				break;
			case R.id.textView4:
				if(BaoyzApplication.getInstance().isApprove){
					if (("null").equals(li.get(0).getSend_to_id())) {
						Toast.makeText(StroresDetail.this, "用户不存在", 1000).show();
					}else{
						Intent it = new Intent(StroresDetail.this, IMChatActivity.class);
						it.putExtra("CustomUserID",li.get(0).getSend_to_id());
						startActivity(it);
					}
				

				}else{
					new AlertDialog.Builder(StroresDetail.this)
					.setMessage("请先认证")
					.setPositiveButton("确定", null)
					.show().setCanceledOnTouchOutside(false);
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
					if(NetWorkUtil.isNetAvailable(StroresDetail.this)){
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
		params.put("customer_id",customerid);
		params.put("reference_id",id);
		params.put("type",type);
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					if(NetWorkUtil.isNetAvailable(StroresDetail.this)){
						ss = new HttpP().sendPOSTRequestHttpClient(shoucs, params);
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
	}
	private List<IntroData2> datas;
	public List<IntroData> getJSon(String str){
		
		datas = new ArrayList<IntroData2>();
		
		try {
			JSONObject dataJson = new JSONObject(str);
			JSONArray array1 = dataJson.getJSONArray("fields");
			for(int i = 0; i < array1.length(); i++){
				JSONObject  jsonObject = array1.optJSONObject(i);
				JSONArray array2 = jsonObject.optJSONArray("array");
				for(int j = 0; j < array2.length(); j++){
					IntroData2 data2 = new IntroData2();
					JSONObject  jsonObject2 = array2.optJSONObject(j);
					data2.Name = jsonObject2.getString("name");
					data2.Value = jsonObject2.getString("value");
					datas.add(data2);
				}
			}
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		li = new ArrayList<IntroData>();
		data = new ArrayList<String>();
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
			String x = jo.optString("coordinate_Y");
			String send_to_id = jo.optString("send_to_id");
			String y = jo.optString("coordinate_X");
			String teampid = jo.optString("theequip");
			System.out.println(teampid+id);
			String telephone= jo.optString("telephone");
			JSONArray   ja = jo.optJSONArray("fields");
			JSONArray  ja1 = jo.optJSONArray("image");
			for (int i = 0; i < ja1.length(); i++) {
				String image = ja1.optString(i);
				data.add(image);
			}
			for (int j = 0; j < ja.length(); j++) {
				JSONObject  objj = ja.optJSONObject(j);
				for (int i = 0; i < objj.length(); i++) {
					String name =objj.optString("name");
					String value = objj.optString("value");
					IntronameData intrname= new IntronameData(name);
					IntrovalueData intrvalue= new IntrovalueData(value);
					inname.add(intrname);
					invalue.add(intrvalue);
					IntroData ids= new IntroData(inname, invalue,contact,telephone,address,regional,teampid,cdname,id,x,y,send_to_id);
					li.add(ids);
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return li;

	}
}
