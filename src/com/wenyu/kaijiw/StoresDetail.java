package com.wenyu.kaijiw;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.wenyu.kaijiw.fragment.Home_IntroFragment;
import com.wenyu.kaijiw.fragment.Home_MatingFragment;
import com.wenyu.kjw.adapter.MySadapter;

public class StoresDetail  extends FragmentActivity{
	private ViewPager viewpager;
	private List<Fragment> frags;
	private Home_IntroFragment hl;
	private Home_MatingFragment hmf;
	private MySadapter mzf ;
	private ImageView image,simimar,camcorder;
	private Context context;
	final UMSocialService mController =UMServiceFactory.getUMSocialService("com.umeng.share");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stores_xiangqing);
		context =StoresDetail.this;
		initView();
		shareview();
	}

	private void shareview() {
		// ���÷�������
		mController.setShareContent("������ữ�����SDK�����ƶ�Ӧ�ÿ��������罻�����ܣ�http://www.umeng.com/social");
		//����������ʱ
		mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
		image.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        // �Ƿ�ֻ���ѵ�¼�û����ܴ򿪷���ѡ��ҳ
		        mController.openShare(StoresDetail.this, false);
		    }
		});
		
		//����΢��
		String appID = "1104528162";
		String appSecret = "S8L9EagzQX5ga3ID";
		// ���΢��ƽ̨
		UMWXHandler wxHandler = new UMWXHandler(StoresDetail.this,appID,appSecret);
		wxHandler.addToSocialSDK();
		//qq�ռ�
		//����1Ϊ��ǰActivity������2Ϊ��������QQ���������APP ID������3Ϊ��������QQ���������APP kEY.
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(StoresDetail.this, "1104528162",
		                "S8L9EagzQX5ga3ID");
		qZoneSsoHandler.addToSocialSDK();
		//��������SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		//������Ѷ΢��SSO handler
		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
		
		
		
	}

	private void initView() {
		
		image = (ImageView) findViewById(R.id.storeImageView2);
		camcorder =  (ImageView) findViewById(R.id.storesImageView01);
		frags = new ArrayList<Fragment>();
		hl = new Home_IntroFragment();
		hmf = new Home_MatingFragment();
		frags.add(hl);
		frags.add(hmf);
		mzf =  new MySadapter(getSupportFragmentManager(),frags);
//		simimar.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(StoresDetail.this,SimilarActivity.class);
//				startActivity(intent);
//				
//			}
//		});
		image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(StoresDetail.this ,HomeShareActivity.class);
				startActivity(it);
			}
		});
		camcorder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(StoresDetail.this,ProductActivity.class);
				it.putExtra("store", "0");
				startActivity(it);
				
			}
		});
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
}
