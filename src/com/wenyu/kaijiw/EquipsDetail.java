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
import com.wenyu.Data.IntronameData;
import com.wenyu.Data.IntrovalueData;
import com.wenyu.Data.ScreenManager;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.db.CustomerSQLiteOpenHelper;
import com.wenyu.kjw.adapter.ShopIntroduceAdapter;

public class EquipsDetail  extends FragmentActivity{
	private String url = ConstantClassField.SERVICE_URL+"service/theequip";
	private ImageView back,share,shouc;
	private RelativeLayout phone;
	private RelativeLayout mapid,intentpro;
	private ListView listview2;
	private ArrayList<String>data,data2;
	private ScreenManager sm;
	private List<IntroData> li;
	private String ss,intentid1,intentid2,category,intentcity;
	private Map<String,String>params;
	private TextView loacaladdress,count,textView4,name;
	private Button lastname;
	private MyGallery gallery;
	private ImageAdapter imageAdapter;
	private List<IntronameData> inname;
	private List<IntrovalueData> invalue;
	private CustomerSQLiteOpenHelper helper;
	private SQLiteDatabase db;
	final UMSocialService mController =UMServiceFactory.getUMSocialService("com.umeng.share");
	private String removecurl =ConstantClassField.SERVICE_URL+"service/removeEnshrine";
	private String shoucurl =ConstantClassField.SERVICE_URL+"service/addEnshrine";
	private int customer_id;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				Toast.makeText(EquipsDetail.this, "����������", 1000).show();
				break;
			case 1:
				//DisplayMetrics dm = new DisplayMetrics();
				//getWindowManager().getDefaultDisplay().getMetrics(dm);
				//int width = dm.widthPixels;
				//	data2 = new ArrayList<String>();
				//				String pic =getIntent().getStringExtra("picture");
				//				data2.add(pic);
				imageAdapter = new ImageAdapter(EquipsDetail.this, data);
				gallery.setAdapter(imageAdapter);
				if(li.size()>0){
					count.setText("��"+data.size()+"��ͼƬ");	
					loacaladdress.setText(li.get(0).getAddress());
					lastname.setText(li.get(0).getContact());
					name.setText(li.get(0).getCdname());
					ShopIntroduceAdapter sa = new ShopIntroduceAdapter(li, EquipsDetail.this);
					listview2.setAdapter(sa);
				}else{

				}
				break;

			case 3:
				Toast.makeText(EquipsDetail.this, "�ղسɹ�", 1000).show();
				break;
			case 4:
				Toast.makeText(EquipsDetail.this, "ȡ�����ɹ�", 1000).show();
				break;
			case 5:
				Toast.makeText(EquipsDetail.this, "ȡ���ղ�", 1000).show();
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.equipdetail);
		initView();
		initValue();
		shareview();
		initThread();
	}


	/**
	 * �ؼ��ļ���
	 */
	private void initValue() {
		// ����������
		textView4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				if(BaoyzApplication.getInstance().isApprove){
					if(("null").equals(li.get(0).getSend_to_id())){
						Toast.makeText(EquipsDetail.this, "�û�������", 1000).show();
					}else{
						Intent it = new Intent(EquipsDetail.this, IMChatActivity.class);
						it.putExtra("CustomUserID",li.get(0).getSend_to_id());
						startActivity(it);
					}


				}else{
					new AlertDialog.Builder(EquipsDetail.this)
					.setMessage("������֤")
					.setPositiveButton("ȷ��", null)
					.show().setCanceledOnTouchOutside(false);
				}



			}
		});
		//		ͼƬ��������
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {

				Intent  pictures = new Intent(EquipsDetail.this,PictureActivity.class);
				pictures.putExtra("pictures", data);
				startActivity(pictures);

			}
		});

		//		�ղؽӿڵĵ���
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
						String type ="��Ʒ";
						getConnect(customer_id+"",dbid,type);
					}

				}finally{

				}	 
				countnum++;
			}

		});
		//		����绰����
		lastname.setOnClickListener(new View.OnClickListener() {  
			@SuppressWarnings("deprecation")  
			@Override  
			public void onClick(View v) {  
				AlertDialog ad=new AlertDialog.Builder(EquipsDetail.this).create();  
				ad.setTitle(li.get(0).getContact());  
				ad.setIcon(R.drawable.ic_launcher);  
				ad.setMessage(li.get(0).getTelephone());  
				ad.setButton("ȷ��", new DialogInterface.OnClickListener() {  

					@Override  
					public void onClick(DialogInterface dialog, int which) {  
						Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+li.get(0).getTelephone()));
						//ִ����ͼ
						startActivity(intent);
					}  
				});  
				ad.setButton2("ȡ��", new DialogInterface.OnClickListener() {  

					@Override  
					public void onClick(DialogInterface dialog, int which) {  


					}  
				});  
				ad.show();  
			}  
		});

	}

	/**
	 * ���ݽ����ӿ�
	 */
	private void initThread() {
		params = new HashMap<String,String>();
		params.put("storeid", intentid1);
		params.put("userid", customer_id+"");
		params.put("equipid", intentid2);

		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					if(NetWorkUtil.isNetAvailable(EquipsDetail.this)){
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
		// ���÷�������
		mController.setShareContent("������ữ�����SDK�����ƶ�Ӧ�ÿ��������罻�����ܣ�http://www.umeng.com/social");
		//����������ʱ
		mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
		share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �Ƿ�ֻ���ѵ�¼�û����ܴ򿪷���ѡ��ҳ
				mController.openShare(EquipsDetail.this, false);
			}
		});

		//����΢��
		String appID = "1104528162";
		String appSecret = "S8L9EagzQX5ga3ID";
		// ���΢��ƽ̨
		UMWXHandler wxHandler = new UMWXHandler(EquipsDetail.this,appID,appSecret);
		wxHandler.addToSocialSDK();
		//qq�ռ�
		//����1Ϊ��ǰActivity������2Ϊ��������QQ���������APP ID������3Ϊ��������QQ���������APP kEY.
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(EquipsDetail.this, "1104528162",
				"S8L9EagzQX5ga3ID");
		qZoneSsoHandler.addToSocialSDK();
		//��������SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		//������Ѷ΢��SSO handler
		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());



	}
	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		sm =  ScreenManager.gerScreenManager();
		sm.pushActivity(EquipsDetail.this);
		helper = new CustomerSQLiteOpenHelper(EquipsDetail.this);
		db = helper.getWritableDatabase();
		gallery = (MyGallery) findViewById(R.id.gy);
		customer_id= BaoyzApplication.getInstance().customer_id;
		back = (ImageView) findViewById(R.id.storeImageView1);
		shouc = (ImageView) findViewById(R.id.storeImageView3);
		count = (TextView)findViewById(R.id.textView3);
		textView4 = (TextView)findViewById(R.id.textView4);
		share = (ImageView) findViewById(R.id.storeImageView2);
		mapid = (RelativeLayout) findViewById(R.id.mapid);
		name = (TextView)findViewById(R.id.name);
		intentpro = (RelativeLayout) findViewById(R.id.intentpro);
		loacaladdress = (TextView) findViewById(R.id.storeTextView2);
		lastname =(Button) findViewById(R.id.storeTextView3);
		phone = (RelativeLayout) findViewById(R.id.phoneon);
		intentpro.setOnClickListener(ocl);
		mapid.setOnClickListener(ocl);
		listview2 = (ListView) findViewById(R.id.storelistview);
		listview2.setFocusable(false);
		back.setOnClickListener(ocl);
		Intent it1 = getIntent();
		intentid2 = it1.getStringExtra("id"); 
		intentid1 = it1.getStringExtra("intentid");
		category = it1.getStringExtra("category");
		intentcity = it1.getStringExtra("name");

	}
	/**
	 * ȡ���ղ�
	 * @param customer_id
	 * @param id
	 */
	private  void canclConnect(String customer_id,String id){
		params = new HashMap<String,String>();
		params.put("customer_id",customer_id);
		params.put("recordid", id);
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					if(NetWorkUtil.isNetAvailable(EquipsDetail.this)){
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
	 * ����ղ�
	 * @param customerid �û�id
	 * @param id
	 * @param type
	 */
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
						if(NetWorkUtil.isNetAvailable(EquipsDetail.this)){
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
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/**ʹ��SSO��Ȩ����������´��� */
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
				//				��ͼ�������ת
				Intent  it = new Intent(EquipsDetail.this,MyMapActivity.class);
				if(("").equals(li.get(0).getX())||("".equals(li.get(0).getY()))){
				}else{
					it.putExtra("x",li.get(0).getX() );
					it.putExtra("y",li.get(0).getY());
					startActivity(it);
				}
				break;
			case R.id.intentpro:
				//				��Ʒ�б����ת
				Intent  its = new Intent(EquipsDetail.this,ProductActivity.class);
				its.putExtra("id", li.get(0).getId());
				its.putExtra("category", category);
				its.putExtra("name", inname.get(0).getName());
				startActivity(its);
				break;
			}

		}

	};
	public List<IntroData> getJSon(String str){
		List<IntroData> li = new ArrayList<IntroData>();
		data = new ArrayList<String>();
		inname = new ArrayList<IntronameData>();
		invalue = new ArrayList<IntrovalueData>();
		JSONObject jo;
		try {
			jo = new JSONObject(str);
			String id = jo.optString("id");
			String send_to_id = jo.optString("send_to_id");
			String cdname = jo.optString("name");
			String address = jo.optString("address");
			String regional = jo.optString("regional");
			String contact = jo.optString("contact");
			String teampid = jo.optString("teampid");
			String x = jo.optString("coordinate_Y");
			String y = jo.optString("coordinate_X");
			String telephone= jo.optString("telephone");
			JSONArray  ja = jo.optJSONArray("fields");
			JSONArray  ja1 = jo.optJSONArray("image");
			for (int i = 0; i < ja1.length(); i++) {
				String image = ja1.optString(i);
				data.add(image);
			}
			for (int j = 0; j < ja.length(); j++) {
				JSONObject  objj = ja.optJSONObject(j);
				for (int i = 0; i < objj.length(); i++) {
					JSONArray jsonar =	objj.optJSONArray("array");
					for (int k = 0; k < jsonar.length(); k++) {
						JSONObject objec = jsonar.optJSONObject(k);
						String name =objec.optString("name");
						String value = objec.optString("value");
						IntronameData intrname= new IntronameData(name);
						IntrovalueData intrvalue= new IntrovalueData(value);
						inname.add(intrname);
						invalue.add(intrvalue);
						IntroData ids= new IntroData(inname, invalue,contact,telephone,address,regional,teampid,cdname,id,x,y,send_to_id);
						li.add(ids);
					}
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return li;

	}
}
