package com.wenyu.kaijiw;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywork.util.NetWorkUtil;
import com.renn.rennsdk.http.HttpRequest.Base64;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Urls;
import com.wenyu.Utils.CacheUtils;
import com.wenyu.Utils.CropHandler;
import com.wenyu.Utils.CropHelper;
import com.wenyu.Utils.CropParams;


/**
 * AbsListViewBaseActivity
 */

public class ImageGridActivity extends Activity implements CropHandler {

	private String photoJson,type,jsonData;
	private String img64="",idcard64="";
	private String url=Urls.Url_Certify;
	private ImageView backima;
	private TextView commit,namecom,typecom,comcom,concom,phocom,zhucom,qiycom;
	private RelativeLayout re1,re2,re3,re4,re5,re6,re7;
	private  Intent intent1,intent2,intent3,intent4,intent5,intent6,intent7;
	private int[] arr = {1,2,3,4,5,6,7};
	private int relativeType;
	private String customer_id;
	private CropParams mCropParams;
	private Map<String, String> paramsValue;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_image_grid);
		initView();
		initData();
		
	}
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(ImageGridActivity.this, "提交成功！", Toast.LENGTH_LONG).show();
				ImageGridActivity.this.finish();
				break;

			default:
				break;
			}
		};
		
	};
	private void initThread() {
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					
//					"category": 影视公司、后期制作、宣传发行、店家,//公司类别---必填
//			         "companyname": '',//公司全称
//			         "license": ’‘,//营业执照图片
//			         "number": ’‘,//企业注册号---必填
//			         "letter": ‘’,//企业公函---必填
//			         "contact": ‘’,//联系人必填---必填
					paramsValue=new HashMap<String, String>(); 
					paramsValue.put("customer_id",customer_id);
					paramsValue.put("type","企业");
					paramsValue.put("category",typecom.getText().toString());
					paramsValue.put("name",namecom.getText().toString());
					paramsValue.put("companyname", comcom.getText().toString());
					paramsValue.put("number", zhucom.getText().toString());
					paramsValue.put("contact", concom.getText().toString());
					paramsValue.put("license", img64);
					paramsValue.put("letter", idcard64);
					if(NetWorkUtil.isNetAvailable(ImageGridActivity.this)){
						jsonData = new HttpP().sendPOSTRequestHttpClient(url,paramsValue);
						String ss =getParse(jsonData);
//								System.out.println("我是认证数据元"+ss+url+paramsValue);
						if(TextUtils.equals(ss, "success")){
							handler.sendEmptyMessage(1);
							
						}else if(("").equals(jsonData)){
							Toast.makeText(ImageGridActivity.this, "网络连接异常 ", 1000).show();
						}else if(("fail").equals(jsonData)){
							Toast.makeText(ImageGridActivity.this, "验证信息失败，请检查 ", 1000).show();
							return;
						}
					}

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}).start();
	}
	private  String getParse(String st){
		String name="";
		try {
			JSONObject jo= new JSONObject(st);
			name = jo.optString("message");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return name;
	}
	private boolean checkPic(){
		if(img64.isEmpty()){
			Toast.makeText(ImageGridActivity.this, "个人照片不能为空", Toast.LENGTH_SHORT).show();
		} if(idcard64.isEmpty()){
			Toast.makeText(ImageGridActivity.this, "身份证不能为空", Toast.LENGTH_SHORT).show();
		}else{
			return true;
		}
		return false;
	}
	@Override
	public void onPhotoCropped(Uri uri) {

		Bitmap bitmap = null;
		
		

		try {
			bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
			byte[] ba = bao.toByteArray();
			if(relativeType==0){
				img64 = "";
				img64 += Base64.encodeBytes(ba);
				Toast.makeText(ImageGridActivity.this, "营业执照已上传，点击更换图片", 1000).show();
				phocom.setText("营业执照已上传，点击更换图片");
//				upload.setAlpha(0);

			}else{
				idcard64 = "";
				idcard64 = Base64.encodeBytes(ba); 
				Toast.makeText(ImageGridActivity.this, "企业公函已上传，点击更换图片", 1000).show();
				qiycom.setText("图片已上传,点击更换图片");
				//	idcard.setAlpha(0);
			}


		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
	
	
	
	// 把imag变成base64
//		public static String imgToBase64(Bitmap bitmap) {
//			
//			ByteArrayOutputStream out = null;
//			try {
//				out = new ByteArrayOutputStream();
//				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//				out.flush();
//				out.close();
//				byte[] imgBytes = out.toByteArray();
//				return Base64.encodeToString(imgBytes, android.util.Base64.NO_WRAP);
//			} catch (Exception e) {
//				return null;
//			} finally {
//				try {
//					out.flush();
//					out.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
	
	
	private void initData() {
		commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(checkPic()){
					initThread();
				}else{
					initThread();
				}
			}
		});
		re1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				intent1 = new Intent(ImageGridActivity.this,PersonInfoUpdateActivityPublish.class);
				intent1.putExtra("f", "1");
				intent1.putExtra("title", "公司名称");
				startActivityForResult(intent1, arr[0]);	

			}
		});
		re2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				intent2 = new Intent(ImageGridActivity.this,PersonInfoUpdateActivityPublish.class);
				intent2.putExtra("f", "2");
				intent2.putExtra("is_list", true);
				intent2.putExtra("title", "公司类别");
				startActivityForResult(intent2, arr[1]);

			}
		});
		re3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				intent3 = new Intent(ImageGridActivity.this,PersonInfoUpdateActivityPublish.class);
				intent3.putExtra("f", "3");
				intent3.putExtra("title", "公司全称");
				startActivityForResult(intent3, arr[2]);

			}
		});
		re4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				intent4 = new Intent(ImageGridActivity.this,PersonInfoUpdateActivityPublish.class);
				intent4.putExtra("title", "联系人姓名");
				intent4.putExtra("f", "4");
				startActivityForResult(intent4, arr[3]);

			}
		});
		re5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mCropParams = new CropParams(
						CacheUtils.getCacheDirectory(ImageGridActivity.this),
						"infos.jpg");
				relativeType = 0;
				startActivityForResult(
						CropHelper.buildCropFromGalleryIntent(mCropParams),
						CropHelper.REQUEST_CROP);	
			}
		});
		re6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				intent6 = new Intent(ImageGridActivity.this,PersonInfoUpdateActivityPublish.class);
				intent6.putExtra("f", "6");
				intent6.putExtra("title", "营业执照注册号");
				startActivityForResult(intent6, arr[5]);

			}
		});
		re7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mCropParams = new CropParams(
						CacheUtils.getCacheDirectory(ImageGridActivity.this),
						"ids.jpg");
				relativeType = 1;
				startActivityForResult(
						CropHelper.buildCropFromGalleryIntent(mCropParams),
						CropHelper.REQUEST_CROP);	
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			updateCompany(data,namecom);
			break;
		case 2:
			//公司名称
			updateCompany(data,typecom);
			break;
		case 3:
			//地址
			updateCompany(data,comcom);
			break;
		case 4:
			//联系方式
			updateCompany(data,concom);
			break;
		case 5:
			//联系方式
			updateCompany(data,phocom);

			break;
		case 6:
			//联系方式
			updateCompany(data,zhucom);
			break;
		case 7:
			//联系方式
			updateCompany(data,qiycom);
			break;
		default:
			this.onPhotoCropped(getCropParams().temUri);
			break;
		}
	}
	private void updateCompany(Intent data,TextView view) {
		if(data!=null){
			Bundle bundle = data.getExtras();
			if(bundle!=null){
				type = bundle.getString("result");				
				view.setText(type);
				view.setTextColor(Color.BLACK);
			}
		}
	}
	private void initView() {
		customer_id = getIntent().getStringExtra("customer_id");
		backima = (ImageView) findViewById(R.id.imageView1);
		backima.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ImageGridActivity.this.finish();
				
			}
		});
		commit = (TextView) findViewById(R.id.commit);
		namecom = (TextView) findViewById(R.id.namecom);
		typecom = (TextView) findViewById(R.id.typecom);
		comcom = (TextView) findViewById(R.id.comcom);
		concom = (TextView) findViewById(R.id.concom);
		phocom = (TextView) findViewById(R.id.phocom);
		zhucom = (TextView) findViewById(R.id.zhucom);
		qiycom = (TextView) findViewById(R.id.qiycom);
		re1 = (RelativeLayout)findViewById(R.id.re1);
		re2 = (RelativeLayout)findViewById(R.id.re2);
		re3 = (RelativeLayout)findViewById(R.id.re3);
		re4 = (RelativeLayout)findViewById(R.id.re4);
		re5 = (RelativeLayout)findViewById(R.id.re5);
		re6 = (RelativeLayout)findViewById(R.id.re6);
		re7 = (RelativeLayout)findViewById(R.id.re7);

	}

	@Override
	public void onCropCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCropFailed(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public CropParams getCropParams() {
		// TODO Auto-generated method stub
		return mCropParams;
	}

	@Override
	public Activity getContext() {
		// TODO Auto-generated method stub
		return null;
	}

}