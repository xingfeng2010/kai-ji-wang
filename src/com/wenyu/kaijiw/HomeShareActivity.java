package com.wenyu.kaijiw;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.RenrenSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class HomeShareActivity extends Activity {
	final UMSocialService mController =UMServiceFactory.getUMSocialService("com.umeng.share");
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		context =HomeShareActivity.this;
//		// ���÷�������
//		mController.setShareContent("���ԣ�http://www.umeng.com/social");
//				mController.openShare(HomeShareActivity.this, false);
//				
//				
//				
//		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
//
//		//����1Ϊ��ǰActivity������2Ϊ��������QQ���������APP ID������3Ϊ��������QQ���������APP kEY.
//		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(HomeShareActivity.this, "1104528162",
//				"S8L9EagzQX5ga3ID");
//		qZoneSsoHandler.addToSocialSDK();
//
//
//		String appID = "1104528162";
//		String appSecret = "S8L9EagzQX5ga3ID";
//		// ���΢��ƽ̨d
//		UMWXHandler wxHandler = new UMWXHandler(HomeShareActivity.this,appID,appSecret);
//		wxHandler.addToSocialSDK();
////		// ���΢������Ȧ
////		UMWXHandler wxCircleHandler = new UMWXHandler(HomeShareActivity.this,appSecret,appSecret);
////		wxCircleHandler.setToCircle(true);
////		wxCircleHandler.addToSocialSDK();
//
//		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(HomeShareActivity.this, "1104528162",
//				"S8L9EagzQX5ga3ID");
//		qqSsoHandler.addToSocialSDK();
//		
//		mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
//
//		//���������SSO��Ȩ����
//		//APPID:201874
//		//API Key:28401c0964f04a72a14c812d6132fcef
//		//Secret:3bf66e42db1e4fa9829b955cc300b737
//		RenrenSsoHandler renrenSsoHandler = new RenrenSsoHandler(context,
//		            "201874", "28401c0964f04a72a14c812d6132fcef",
//		            "3bf66e42db1e4fa9829b955cc300b737");
//		mController.getConfig().setSsoHandler(renrenSsoHandler);
//		
//
//	}
//	//����SSO��Ȩ�ص�
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		/**ʹ��SSO��Ȩ����������´��� */
//		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
//		if(ssoHandler != null){
//			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
//		}
	}

}

