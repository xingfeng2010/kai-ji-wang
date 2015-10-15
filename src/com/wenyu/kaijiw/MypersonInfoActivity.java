package com.wenyu.kaijiw;

import imsdk.data.IMSDK.OnActionProgressListener;
import imsdk.data.mainphoto.IMMyselfMainPhoto;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.renn.rennsdk.http.HttpRequest.Base64;
import com.wenyu.Data.Urls;
import com.wenyu.Utils.CacheUtils;
import com.wenyu.Utils.CropHandler;
import com.wenyu.Utils.CropHelper;
import com.wenyu.Utils.CropParams;
import com.wenyu.Utils.UrlToImage;

public class MypersonInfoActivity extends Activity  implements CropHandler{
	private ImageView myphoto,imageAddress,imageGender;
	private RelativeLayout changephoto,imageName;
	private String name,address,gender,image,img64="",picPath,urlUpdate = Urls.Url_PersonInfoUpdate;
	private RequestQueue queue;
	private TextView dialogCancel,dialogSelect,dialogPhoto,textName,textAddress,textGender;
	private CropParams mCropParams; // 截图的参数


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {   
		super.onActivityResult(requestCode, resultCode, data);
		CropHelper.handleResult(this, requestCode, resultCode, data);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_infor2);
		initView();
		PerImage();

	}

	private void initView() {
		myphoto= (ImageView) findViewById(R.id.perinfo2imageView2);	
		changephoto = (RelativeLayout) findViewById(R.id.myinfor2relative01);
		textName = (TextView) findViewById(R.id.perinfo2textView4);
		textAddress = (TextView) findViewById(R.id.perinfo2textView7);
		textGender = (TextView) findViewById(R.id.perinfo2textView8);
		imageName = (RelativeLayout) findViewById(R.id.myinfor2relative02);
		mCropParams = new CropParams(
				CacheUtils.getCacheDirectory(MypersonInfoActivity.this),
				"Photo.jpg");
		name = getIntent().getStringExtra("name"); 
		textName.setText(name);
		address = getIntent().getStringExtra("address"); 
		textAddress.setText(address);
		gender = getIntent().getStringExtra("gender"); 
		textGender.setText(gender);

		//  Toast.makeText(MypersonInfoActivity.this, "名字:"+name,Toast.LENGTH_SHORT).show(); 
		changephoto.setOnClickListener(ol);
		imageName.setOnClickListener(ol);
	}

	private void PerImage() {
		try {
			queue =  Volley.newRequestQueue(MypersonInfoActivity.this);
			image = getIntent().getStringExtra("image");    
			// Toast.makeText(MypersonInfoActivity.this, "图片:"+image,Toast.LENGTH_SHORT).show(); 
			UrlToImage.getImage(image,queue,myphoto);	                      
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(MypersonInfoActivity.this, "无法加载图片",Toast.LENGTH_SHORT).show();  
		}
	}
	OnClickListener ol = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.myinfor2relative01:
				initDialog();
				break;
			case R.id.myinfor2relative02:
				Intent it1 = new Intent(MypersonInfoActivity.this,PersonInfoUpdateActivity.class);
				it1.putExtra("title", "名字");
				it1.putExtra("content", name);
				startActivity(it1);
				break;
			default:
				break;
			}		
		}
	};

	private void initDialog() {
		//第一步获得视图对象：
		View myview=LayoutInflater.from(getApplicationContext()).inflate(R.layout.myperinfo2_dialog, null);
		//第二步实例化dialog类：
		final Dialog tc= new Dialog(MypersonInfoActivity.this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);//第二个参数可以使用系统弹窗风格，也可以使用自定义风格
		//第三步填充视图：
		tc.setContentView(myview);
		//第四步展示视图：
		tc.show();
		dialogPhoto = (TextView) myview.findViewById(R.id.perphoto2TextView01);
		dialogSelect = (TextView) myview.findViewById(R.id.perphoto2TextView02);
		dialogCancel = (TextView) myview.findViewById(R.id.perphoto2TextView03);
		dialogPhoto.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				tc.dismiss();
				Intent intent = CropHelper.buildCaptureIntent(mCropParams.temUri);
				startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
			}
		});	
		dialogSelect.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				tc.dismiss();
				startActivityForResult(
						CropHelper.buildCropFromGalleryIntent(mCropParams),
						CropHelper.REQUEST_CROP);

			}
		});	

		dialogCancel.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				tc.dismiss();
			}
		});	


	}

	String imagurl;
	@Override
	public void onPhotoCropped(Uri uri) {
		Bitmap bitmap = null;

		try {
			imagurl = uri.toString();
			bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
			byte[] ba = bao.toByteArray();
			img64 = Base64.encodeBytes(ba);
			System.out.println(img64);

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		// 向IMSDK服务器，上传图片信息
		IMMyselfMainPhoto.upload(bitmap, new OnActionProgressListener() {
			@Override
			public void onSuccess() {
				Toast.makeText(MypersonInfoActivity.this, "上传成功",Toast.LENGTH_SHORT).show(); 
			}

			@Override
			public void onProgress(double progress) {
				// 上传进度的回调
				// progress为取值范围从0到1的浮点数
//				Toast.makeText(MypersonInfoActivity.this, "上传进度 : " + (int) (progress * 100) + "%",Toast.LENGTH_SHORT).show(); 
			}

			@Override
			public void onFailure(String error) {
				Toast.makeText(MypersonInfoActivity.this, "上传失败 : " + error,Toast.LENGTH_SHORT).show(); 
			}
		});

	}

	@Override
	public void onCropCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCropFailed(String message) {
		Toast.makeText(MypersonInfoActivity.this, message, Toast.LENGTH_SHORT).show();

	}

	@Override
	public CropParams getCropParams() {
		return mCropParams;
	}

	@Override
	public Activity getContext() {
		return MypersonInfoActivity.this;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(!TextUtils.isEmpty(imagurl)){
				Intent data = new Intent();
				data.putExtra("persion_imag", imagurl);
				setResult(RESULT_OK, data);
			}

			MypersonInfoActivity.this.finish();

		}
		return super.onKeyDown(keyCode, event);
	}


}
