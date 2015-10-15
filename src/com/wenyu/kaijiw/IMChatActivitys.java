package com.wenyu.kaijiw;

import imsdk.data.mainphoto.IMSDKMainPhoto;
import imsdk.views.IMChatView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.wenyu.Utils.ConstantClassField;


public class IMChatActivitys extends BaseActivity {
	private String url =ConstantClassField.SERVICE_URL +"service/getIMIDBySendToID";
	private String mCustomUserID,name,image;
    private Bitmap bitmap = null;
	private IMChatView mChatView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initBase(0);
		initView();
	}


	private void initView() {
		mCustomUserID = getIntent().getStringExtra("CustomUserID");
		name = getIntent().getStringExtra("name");
		image = getIntent().getStringExtra("image");
		bitmap = IMSDKMainPhoto.get(mCustomUserID); 
		mChatView = new IMChatView(IMChatActivitys.this,mCustomUserID);
		// 为IMChatView实例配置参数
		mChatView.setMaxGifCountInMessage(10);
		mChatView.setUserMainPhotoVisible(true);
		mChatView.setUserMainPhotoCornerRadius(10);
		mChatView.setTitleBarVisible(true);
		mChatView.setTitle(name);
		// 添加到当前activity
		setContentView(mChatView);
		//添加头像点击事件监听
		mChatView.setOnHeadPhotoClickListener(new IMChatView.OnHeadPhotoClickListener() {
			
			@Override
			public void onClick(View v, String customUserID) {
				Toast.makeText(IMChatActivitys.this, "您点击了"+customUserID, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 为了实现捕获用户选择的图片
		mChatView.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 为了实现点击返回键隐藏表情栏
		mChatView.onKeyDown(keyCode, event);
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View arg0) {
		
	}

}
