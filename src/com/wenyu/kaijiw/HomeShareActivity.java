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
//		// 设置分享内容
//		mController.setShareContent("试试，http://www.umeng.com/social");
//				mController.openShare(HomeShareActivity.this, false);
//				
//				
//				
//		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
//
//		//参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
//		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(HomeShareActivity.this, "1104528162",
//				"S8L9EagzQX5ga3ID");
//		qZoneSsoHandler.addToSocialSDK();
//
//
//		String appID = "1104528162";
//		String appSecret = "S8L9EagzQX5ga3ID";
//		// 添加微信平台d
//		UMWXHandler wxHandler = new UMWXHandler(HomeShareActivity.this,appID,appSecret);
//		wxHandler.addToSocialSDK();
////		// 添加微信朋友圈
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
//		//添加人人网SSO授权功能
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
//	//配置SSO授权回调
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		/**使用SSO授权必须添加如下代码 */
//		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
//		if(ssoHandler != null){
//			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
//		}
	}

}

