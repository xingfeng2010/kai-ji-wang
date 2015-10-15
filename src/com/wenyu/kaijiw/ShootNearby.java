package com.wenyu.kaijiw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.wenyu.Data.Find_yingshi;
import com.wenyu.Data.ScreenManager;
import com.wenyu.Data.Urls;
import com.wenyu.kjw.adapter.HomeYing;

public class ShootNearby  extends FragmentActivity{
	
	private ViewPager viewpager;
	private ImageView image,back,share,shouc,shey;
	private ListView listview;
	private  String ss,url = Urls.Url_addEnshrine;
	private Find_yingshi fys ;
	private List<Find_yingshi> fyslist;
	private HomeYing hy;
	private ScreenManager sm;
	private Map<String,String>params;
	final UMSocialService mController =UMServiceFactory.getUMSocialService("com.umeng.share");
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				Toast.makeText(ShootNearby.this, "����������", 1000).show();
				break;
			case 1:
				
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stores_xiangqing);
	params = new HashMap<String, String>();
		initView();
		shareview();
			
		
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
				mController.openShare(ShootNearby.this, false);
			}
		});

		//����΢��
		String appID = "1104528162";
		String appSecret = "S8L9EagzQX5ga3ID";
		// ���΢��ƽ̨
		UMWXHandler wxHandler = new UMWXHandler(ShootNearby.this,appID,appSecret);
		wxHandler.addToSocialSDK();
		//qq�ռ�
		//����1Ϊ��ǰActivity������2Ϊ��������QQ���������APP ID������3Ϊ��������QQ���������APP kEY.
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(ShootNearby.this, "1104528162",
				"S8L9EagzQX5ga3ID");
		qZoneSsoHandler.addToSocialSDK();
		//��������SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		//������Ѷ΢��SSO handler
		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
	}

	private void initView() {
		sm =  ScreenManager.gerScreenManager();
		sm.pushActivity(ShootNearby.this);
		back = (ImageView) findViewById(R.id.storeImageView1);
		shouc = (ImageView) findViewById(R.id.storeImageView3);
		share = (ImageView) findViewById(R.id.storeImageView2);
		fyslist = new ArrayList<Find_yingshi>();

		listview = (ListView) findViewById(R.id.storelistview);
		shey = (ImageView) findViewById(R.id.storesImageView01);
		Intent it = getIntent();
		fys =(Find_yingshi) it.getSerializableExtra("key");
		fyslist.add(fys);
		hy=new HomeYing(fyslist, ShootNearby.this);
		listview.setAdapter(hy);
		shouc.setOnClickListener(ocl);
		back.setOnClickListener(ocl);
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
			case R.id.storeImageView3:
				 Toast.makeText(ShootNearby.this, "����ղسɹ�", Toast.LENGTH_SHORT).show();
				break;
			case R.id.storesImageView01:
				Intent intents = new Intent(ShootNearby.this,ShootProduct.class);
				startActivity(intents);
				break;
			}

		}
	};

	
}
