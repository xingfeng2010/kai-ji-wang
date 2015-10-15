package com.wenyu.kaijiw;

import imsdk.data.IMMyself;
import imsdk.data.IMMyself.OnActionListener;
import imsdk.data.IMMyself.OnReceiveTextListener;
import imsdk.data.mainphoto.IMSDKMainPhoto;
import imsdk.views.IMChatView;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.HttpP;
import com.wenyu.Utils.ConstantClassField;


public class IMChatActivity extends BaseActivity {
	private String url =ConstantClassField.SERVICE_URL +"service/getIMIDBySendToID";
	private String mCustomUserID,name,image;
	private Bitmap bitmap = null;
	private IMChatView mChatView;
	private String ss,mess;
	private Map<String,String>params;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				// ����һ��IMChatViewʵ��
				//				System.out.println("����mess��ʲô"+mess);
				mChatView = new IMChatView(IMChatActivity.this,mess);
				// ΪIMChatViewʵ�����ò���
				mChatView.setMaxGifCountInMessage(10);
				mChatView.setUserMainPhotoVisible(true);
				mChatView.setUserMainPhotoCornerRadius(10);
				mChatView.setTitleBarVisible(true);
				mChatView.setTitle(name);
				//				mChatView.set
				// ��ӵ���ǰactivity
				setContentView(mChatView);
				//���ͷ�����¼�����
				mChatView.setOnHeadPhotoClickListener(new IMChatView.OnHeadPhotoClickListener() {

					@Override
					public void onClick(View v, String customUserID) {
						Toast.makeText(IMChatActivity.this, "�������"+customUserID, Toast.LENGTH_SHORT).show();
					}
				});
				break;
			case 5:
				Toast.makeText(IMChatActivity.this, "�û�δ�ҵ�", 1000).show();
				break;
			}

		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initBase(0);
		initView();
		initThread();
	}

	private void initThread() {
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					params = new HashMap<String,String>();
					params.put("send_to_id", mCustomUserID);

					if(("").equals(mCustomUserID)||("null").equals(mCustomUserID)){
						Toast.makeText(IMChatActivity.this, "�û�������", 1000).show();
					}else{
						if(NetWorkUtil.isNetAvailable(IMChatActivity.this)){
							ss = new HttpP().sendPOSTRequestHttpClient(url, params);
							mess = getMessage(ss);
							if(("�û�δ�ҵ�").equals(mess)){
								handler.sendEmptyMessage(5);
							}else{
								handler.sendEmptyMessage(1); 
							}

						}else{
							Toast.makeText(IMChatActivity.this, "���粻����", 1000).show();
						}
					}

				}


				catch (Exception e) {
					e.printStackTrace();
				}

			}

		}).start();

	}

	private void initView() {
		mCustomUserID = getIntent().getStringExtra("CustomUserID");
		name = getIntent().getStringExtra("name");
		image = getIntent().getStringExtra("image");
		bitmap = IMSDKMainPhoto.get(mCustomUserID); 


		// ��̬���ó�ʱʱ��



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

		back();

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View arg0) {

	}

	private String getMessage(String sendto_id){
		String message="";
		try {
			JSONObject jsonObject = new JSONObject(sendto_id);
			message = jsonObject.optString("message");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return message;
	}
	public void back(){
		IMChatActivity.this.finish();
	}

}
