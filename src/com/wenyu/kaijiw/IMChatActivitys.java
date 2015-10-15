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
		// ΪIMChatViewʵ�����ò���
		mChatView.setMaxGifCountInMessage(10);
		mChatView.setUserMainPhotoVisible(true);
		mChatView.setUserMainPhotoCornerRadius(10);
		mChatView.setTitleBarVisible(true);
		mChatView.setTitle(name);
		// ��ӵ���ǰactivity
		setContentView(mChatView);
		//���ͷ�����¼�����
		mChatView.setOnHeadPhotoClickListener(new IMChatView.OnHeadPhotoClickListener() {
			
			@Override
			public void onClick(View v, String customUserID) {
				Toast.makeText(IMChatActivitys.this, "�������"+customUserID, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// Ϊ��ʵ�ֲ����û�ѡ���ͼƬ
		mChatView.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Ϊ��ʵ�ֵ�����ؼ����ر�����
		mChatView.onKeyDown(keyCode, event);
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View arg0) {
		
	}

}
