package com.wenyu.kaijiw;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.mywork.util.NetWorkUtil;
import com.renn.rennsdk.http.HttpRequest.Base64;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.IntroData;
import com.wenyu.Data.IntronameData;
import com.wenyu.Data.IntrovalueData;
import com.wenyu.Data.Urls;
import com.wenyu.Utils.CacheUtils;
import com.wenyu.Utils.CropHandler;
import com.wenyu.Utils.CropHelper;
import com.wenyu.Utils.CropParams;
import com.wenyu.Utils.UrlToImage;

public class CompanyInfoActivity extends Activity   implements CropHandler{


	private ImageView myphoto;
	private String url = Urls.Url_Details;
	private RequestQueue queue;
	private String image,customer_id,json1,type;
	private Map<String,String> paramsValue,detailResult;
	private TextView detailName,detailContect,detailAddress,category,detailIntroduction,detailParticipateFilm;
	private List<IntroData> li;
	private GridView detailIntro;
	private RelativeLayout  companyType,companyName,companyAddress,companyContect,companyIntroduction,companyFilm;
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				Toast.makeText(CompanyInfoActivity.this, "网络连接异常 ", 1000).show();
				break;
			case 1:

				break;
			case 3:
			   	initContent();
//				ShopIntroduceAdapter sa = new ShopIntroduceAdapter(li, CompanyInfoActivity.this);
//				detailIntro.setAdapter(sa);
				
				break;
			case 8:
				Toast.makeText(CompanyInfoActivity.this, "请求失败", 1000).show();
				break;
			}
		};
	};
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch (requestCode) {
		
		case CropHelper.REQUEST_CAMERA :
		case CropHelper.REQUEST_CROP:
			CropHelper.handleResult(this, requestCode, resultCode, data);
			break;
		case 1:
			//第一职业
			if(data!=null){
				Bundle bundle = data.getExtras();
				if(bundle!=null){
				 type = bundle.getString("category");	
				 category.setText(type);
				 category.setTextColor(Color.BLACK);
				}
			}
			break;
		case 2:
			//公司名称
			updateCompany(data,detailName);
			break;
		case 3:
			//地址
			updateCompany(data,detailAddress);
			break;
		case 4:
			//联系方式
			updateCompany(data,detailContect);
			break;
		case 5:
			//公司简介
			updateCompany(data, detailIntroduction);
			break;
		case 6:
			//参与影片
			updateCompany(data, detailParticipateFilm);
			break;
		case 1001://历史地址
			picUrl = data.getStringExtra("persion_imag");
			if(!TextUtils.isEmpty(picUrl)){
				try {
					Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(picUrl));
					ByteArrayOutputStream bao = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
					byte[] ba = bao.toByteArray();
					img64 = Base64.encodeBytes(ba);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_one);
		initView();
	    ComImage();
	    initThread(); 
	}
	private void initContent(){
		detailName.setText(detailResult.get("name"));
		detailAddress.setText(detailResult.get("address"));
		
	}
	private void initThread() {
		new Thread(new Runnable(){	
	    	@Override
	     	public void run() {
			try {
				paramsValue=new HashMap<String, String>();  
				paramsValue.put("id",customer_id);

				if(NetWorkUtil.isNetAvailable(CompanyInfoActivity.this)){
					json1 = new HttpP().sendPOSTRequestHttpClient(url,paramsValue);
					if(("").equals(json1)){
						handler.sendEmptyMessage(8);
					}else{
						detailResult = initying(json1);
						handler.sendEmptyMessage(3);
					}
				}
				else {
					handler.sendEmptyMessage(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}).start();
	}

	private void ComImage() {
		try {
               queue =  Volley.newRequestQueue(CompanyInfoActivity.this);
               image = getIntent().getStringExtra("image");       		   
				UrlToImage.getImage(image,queue,myphoto);	                      
				} catch (Exception e) {
					e.printStackTrace();
					 Toast.makeText(CompanyInfoActivity.this, "无法加载图片",Toast.LENGTH_SHORT).show();  
				}
	}

	private void initView() {
		mCropParams = new CropParams(
				CacheUtils.getCacheDirectory(CompanyInfoActivity.this),
				"Photo.jpg");
		detailIntro = (GridView) findViewById(R.id.cmessagetextView9);
		customer_id = getIntent().getStringExtra("customer_id");    
		myphoto  = 	(ImageView) findViewById(R.id.cmessageimageView2);
		detailName = (TextView) findViewById(R.id.cmessagetextView3);
		detailAddress = (TextView) findViewById(R.id.cmessagetextView5);
		detailContect = (TextView) findViewById(R.id.cmessagetextView6);
		detailIntroduction = (TextView) findViewById(R.id.cmessagetextView7);
		detailParticipateFilm = (TextView) findViewById(R.id.cmessagetextView8);
		category = (TextView) findViewById(R.id.cmessagetextView4);
		companyType = (RelativeLayout) findViewById(R.id.cmessagerelative02);
		companyName = (RelativeLayout) findViewById(R.id.cmessagerelative01);
		companyAddress = (RelativeLayout) findViewById(R.id.cmessagerelative03);
		companyContect = (RelativeLayout) findViewById(R.id.cmessagerelative04);
		companyIntroduction = (RelativeLayout) findViewById(R.id.cmessagerelative05);
		companyFilm = (RelativeLayout) findViewById(R.id.cmessagerelative06);
		companyName.setOnClickListener(ol);
		companyType.setOnClickListener(ol);
		companyAddress.setOnClickListener(ol);
		companyContect.setOnClickListener(ol);
		companyIntroduction.setOnClickListener(ol);
		companyFilm.setOnClickListener(ol);
		myphoto.setOnClickListener(ol);
	}
	  private Map<String,String> initying(String detailJson){
		   detailResult =new HashMap<String, String>();  
		   try {
			JSONObject jo = new JSONObject(detailJson);
			image = jo.optString("image");
			String name = jo.optString("name");
			String nameEn =jo.optString("nameEn");

			 String position =jo.optString("introduce");
			 String address =jo.optString("address");
	         detailResult.put("name", name);
	         detailResult.put("nameEn", nameEn);

	         detailResult.put("position", position);
	         detailResult.put("address", address);
	         //------------解析个人简介------------
	         li = new ArrayList<IntroData>();
	         List<IntronameData> inname = new ArrayList<IntronameData>();
	 		List<IntrovalueData> invalue = new ArrayList<IntrovalueData>();

				JSONArray arr = jo.optJSONArray("films");
				for (int i = 0; i < arr.length(); i++) {
					JSONObject ob = arr.optJSONObject(i);
					String infoname =ob.optString("name");
					String value = ob.optString("filmType");
					IntronameData intrname= new IntronameData(infoname);
					IntrovalueData intrvalue= new IntrovalueData(value);
					inname.add(intrname);
					invalue.add(intrvalue);
					IntroData id = 	new IntroData(inname, invalue, "", "","","");
					li.add(id);
				}
				
	         
		} catch (JSONException e) {
			e.printStackTrace();
		}
	   return detailResult;
	   }
	OnClickListener ol = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.cmessagerelative02:
				Intent it = new Intent(CompanyInfoActivity.this,CompanyTypeActivity.class);
				startActivityForResult(it, 1);
				break;
			case R.id.cmessagerelative01:
				Intent it1 = new Intent(CompanyInfoActivity.this,PersonInfoUpdateActivity.class);
				it1.putExtra("title", "公司名字");
				it1.putExtra("content", detailName.getText().toString());
				startActivityForResult(it1, 2);
				break;
			case R.id.cmessagerelative03:
				Intent it2 = new Intent(CompanyInfoActivity.this,PersonInfoUpdateActivity.class);
				it2.putExtra("title", "地址");
				it2.putExtra("content", detailAddress.getText().toString());
				startActivityForResult(it2, 3);
				break;
			case R.id.cmessagerelative04:
				Intent it3 = new Intent(CompanyInfoActivity.this,PersonInfoUpdateActivity.class);
				it3.putExtra("title", "联系方式");
				it3.putExtra("content", detailContect.getText().toString());
				startActivityForResult(it3, 4);
				break;
			case R.id.cmessagerelative05:
				Intent it4 = new Intent(CompanyInfoActivity.this,PersonInfoUpdateActivity.class);
				it4.putExtra("title", "公司简介");
				it4.putExtra("content", detailIntroduction.getText().toString());
				startActivityForResult(it4, 5);
				break;
			case R.id.cmessagerelative06:
				Intent it5 = new Intent(CompanyInfoActivity.this,PersonInfoUpdateActivity.class);
				it5.putExtra("title", "参与影片");
				it5.putExtra("content", detailParticipateFilm.getText().toString());
				startActivityForResult(it5, 6);
				break;
			case R.id.cmessageimageView2:
				initDialog();
			
			break;
			default:
				break;
			}
			
		}
	};
	
	
	String jsonData;
	private void updataPersion() {


		new Thread(new Runnable(){

			@Override
			public void run() {
				try {

					paramsValue=new HashMap<String, String>(); 
					paramsValue.put("customer_id",customer_id);
					paramsValue.put("name", detailName.getText().toString());//名字
					paramsValue.put("category",category.getText().toString());//企业类别
					paramsValue.put("address",detailAddress.getText().toString());//企业地址
//					paramsValue.put("introduce","你猜");//企业简介
					paramsValue.put("contact",detailContect.getText().toString());//联系人
					
//					 ["image" : 图片 ,      ||base64数据
//					   "name" : 用户名称,     ||
//					   "category" : 企业类别, ||
//					   "address" : 企业地址,  ||
//					   "introduce" : 企业简介, ||
//					   "contact" : 联系人]
					
					
					paramsValue.put("image", img64);
					if(NetWorkUtil.isNetAvailable(CompanyInfoActivity.this)){
						jsonData = new HttpP().sendPOSTRequestHttpClient(Urls.Url_PersonInfoUpdate,paramsValue);
						String ss =getParse(jsonData);
//								System.out.println("我是认证数据元"+ss+url+paramsValue);
						if(TextUtils.equals(ss, "success")){
							handler.sendEmptyMessage(1);
						}else if(("").equals(jsonData)){
							Toast.makeText(CompanyInfoActivity.this, "网络连接异常 ", 1000).show();
						}else if(("fail").equals(jsonData)){
							Toast.makeText(CompanyInfoActivity.this, "验证信息失败，请检查 ", 1000).show();
							return;
						}
					}

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}).start();

	}
	
	String picUrl;
	String img64;
	private TextView dialogPhoto;
	private TextView dialogSelect;
	private TextView dialogCancel;
	private CropParams mCropParams; // 截图的参数
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			updataPersion();
			
		}
		return super.onKeyDown(keyCode, event);
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
	
	
	private void initDialog() {
		//第一步获得视图对象：
		View myview=LayoutInflater.from(getApplicationContext()).inflate(R.layout.myperinfo2_dialog, null);
		//第二步实例化dialog类：
		final Dialog tc= new Dialog(CompanyInfoActivity.this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);//第二个参数可以使用系统弹窗风格，也可以使用自定义风格
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
	@Override
	public void onPhotoCropped(Uri uri) {
		Bitmap bitmap = null;

		try {
			bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
			  ByteArrayOutputStream bao = new ByteArrayOutputStream();
	          bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
	          byte[] ba = bao.toByteArray();
	          img64 = Base64.encodeBytes(ba);
	          System.out.println(img64);
			
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		
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
		return CompanyInfoActivity.this;
	}
	
	
}
