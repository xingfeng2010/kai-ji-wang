package com.wenyu.kaijiw.fragment;

import imsdk.data.mainphoto.IMSDKMainPhoto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.BaoyzApplication;
import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.Constants;
import com.wenyu.Data.Customer;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Urls;
import com.wenyu.Data.Constants.Extra;
import com.wenyu.Utils.CacheUtils;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.Utils.CropParams;
import com.wenyu.Utils.ImageViewService;
import com.wenyu.Utils.UrlToImage;
import com.wenyu.db.DBManager;
import com.wenyu.kaijiw.CompanyInfoActivity;
import com.wenyu.kaijiw.ImageGridActivity;
import com.wenyu.kaijiw.LoginActivity;
import com.wenyu.kaijiw.MainActivity;
import com.wenyu.kaijiw.R;
import com.wenyu.kaijiw.Release;
import com.wenyu.kaijiw.SettingActivity;


public class MineComFragment extends MyBaseFragment {
	private ImageView cominfo,release,setting,myphoto;
	private Button exit;
	private int customer_id,cflag;
	private String phone,password,json1,image,imID;
	private Map<String,String> paramsValue;
	private String url = Urls.Url_Logins;
	private RequestQueue queue;
	private Bitmap  bitmap,bitmap1;
	private CropParams  mCropParams;
	private RelativeLayout certifyRelative,cusService;
	private TextView mtextView2,mtextView3,mtextView4,mtextView5,mtextView6;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){

			case 0:
				Toast.makeText(getActivity(), "网络连接异常 ", 1000).show();
				break;
			case 1:

				break;
			case 3:
				try {
					queue =  Volley.newRequestQueue(getActivity());	
					UrlToImage.getImage(image,queue,myphoto);	   
					bitmap1 = IMSDKMainPhoto.get(imID);
					if(bitmap1==null){
						mCropParams = new CropParams(
								CacheUtils.getCacheDirectory(getActivity()),
								imID +".jpg");
						try{
							bitmap1 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mCropParams.temUri);
						}catch(IOException e){
						}
					}
					if(bitmap1!=null){
					}else{

						UrlToImage.uploadIM(bitmap,getActivity());
						String img_path = CropParams.getRealFilePath(getActivity(),mCropParams.temUri);
						OutputStream outStream = new FileOutputStream(img_path); 
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
						outStream.flush();  
						outStream.close(); 
					}

				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getActivity(), "无法加载图片",Toast.LENGTH_SHORT).show();  
				}
				break;
			case 5:
				Toast.makeText(getActivity(), "返回上级重试 ", 1000).show();
				break;
			case 8:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)  {
		if (getArguments() != null) {
			customer_id = getArguments().getInt("customer_id");
			phone = getArguments().getString("phone");
			password = getArguments().getString("password");
			imID  =  getArguments().getString("imID");
			cflag = getArguments().getInt("cflag");
		}
		super.onCreate(savedInstanceState);
		return inflater.inflate(R.layout.com_attestaionbefor, null);}
	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initThread();

	}

	private void initView() {
		mtextView2 =(TextView) getView().findViewById(R.id.mtextView2);
		mtextView3=(TextView) getView().findViewById(R.id.mtextView3);
		mtextView4=(TextView) getView().findViewById(R.id.mtextView4);
		mtextView5=(TextView) getView().findViewById(R.id.mtextView5);
		mtextView6=(TextView) getView().findViewById(R.id.mtextView6);
		myphoto = (ImageView) getView().findViewById(R.id.mimageView2);
		cominfo = (ImageView) getView().findViewById(R.id.mimageView3);
		certifyRelative =	(RelativeLayout) getView().findViewById(R.id.re);
		certifyRelative.setOnClickListener(ol);
		if(cflag==2){
			certifyRelative.setVisibility(View.GONE);
			certifyRelative.setLayoutParams(new RelativeLayout.LayoutParams(0,0));
		}else{
			certifyRelative.setVisibility(View.VISIBLE);

		}

		//		certifyRelative.setVisibility(View.VISIBLE);
		//		release = (ImageView) getView().findViewById(R.id.mimageView5);
		//		cusService = (RelativeLayout)getView().findViewById(R.id.comattestaionrelative02);
		//		setting = (ImageView) getView().findViewById(R.id.mimageView7);
		exit = (Button) getView().findViewById(R.id.mbutton1);
		getView().findViewById(R.id.mtextView2).setOnClickListener(ol);
		//		cominfo.setOnClickListener(ol);
		//		release.setOnClickListener(ol);
		//		certifyRelative.setOnClickListener(ol);
		//		cusService.setOnClickListener(ol);
		//		setting.setOnClickListener(ol);
		exit.setOnClickListener(ol);
		mtextView2.setOnClickListener(ol);
		mtextView3.setOnClickListener(ol);
		mtextView4.setOnClickListener(ol);
		mtextView5.setOnClickListener(ol);
		mtextView6.setOnClickListener(ol);

	}
	public static MineComFragment newInstance(int id,String phone,String password,String imID,int cflag) {
		MineComFragment fragment = new MineComFragment();
		Bundle args = new Bundle();
		args.putInt("customer_id", id);
		args.putString("phone", phone);
		args.putString("password", password);
		args.putString("imID", imID);
		args.putInt("cflag", cflag);
		fragment.setArguments(args);
		return fragment;
	}
	private void initThread() {
		new Thread(new Runnable(){

			@Override
			public void run() {

				try {
					paramsValue=new HashMap<String, String>();  
					paramsValue.put("phoneNumber",phone);
					paramsValue.put("pwd", password);

					if(NetWorkUtil.isNetAvailable(getActivity())){
						json1 = new HttpP().sendPOSTRequestHttpClient(url,paramsValue);
						if(("").equals(json1)){
							handler.sendEmptyMessage(8);
						}else{
							image = initying(json1);
							bitmap = ImageViewService.getImage(image);
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
	private String initying(String json1) {

		try {
			JSONObject jo = new JSONObject(json1);
			String  image = jo.optString("image");
			return image;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "解析失败";
	}	

	OnClickListener ol = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mtextView2:
				if(cflag==2){
					Intent intent1 = new Intent(getActivity(),CompanyInfoActivity.class);
					intent1.putExtra("image", image);
					intent1.putExtra("customer_id", customer_id+"");
					startActivity(intent1);}
				else{
					new AlertDialog.Builder(getActivity()).setMessage("请先认证").setPositiveButton("确定", new DialogInterface.OnClickListener() {		
						@Override
						public void onClick(DialogInterface dialog, int which) {
						} 
					}).show();
				}
				break;


			case R.id.re:

				if(cflag==4){
					Intent intent2 = new Intent(getActivity(), ImageGridActivity.class);
					intent2.putExtra(Extra.IMAGES, Constants.IMAGES);
					intent2.putExtra("customer_id", customer_id+"");
					startActivity(intent2);
				}
				else{
					new AlertDialog.Builder(getActivity()).setMessage("认证已完成，无需重新认证").setPositiveButton("确定", new DialogInterface.OnClickListener() {		
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).show();
				}
				break;
			case R.id.mtextView4:
				if(cflag==2){
					Intent intent3 = new Intent(getActivity(),Release.class);
					intent3.putExtra(Extra.IMAGES, Constants.IMAGES);
					intent3.putExtra("customer_id", customer_id+"");
					startActivity(intent3);
				}else{
					new AlertDialog.Builder(getActivity()).setMessage("请先认证").setPositiveButton("确定", new DialogInterface.OnClickListener() {		
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).show();
				}
				break;
			case R.id.mtextView5:
				if(cflag==2){
					new AlertDialog.Builder(getActivity())
					.setMessage("4001166396")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {		
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + "4001166396"));  
							startActivity(intent);
						}
					})
					.setNegativeButton("取消", null)
					.show();

				}else{
					new AlertDialog.Builder(getActivity()).setMessage("请先认证").setPositiveButton("确定", new DialogInterface.OnClickListener() {		
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).show();
				}

				break;
			case R.id.mtextView6:
				Intent intent4 = new Intent(getActivity(),SettingActivity.class);
				intent4.putExtra("phone", phone);
				startActivity(intent4);
				//				
				break;	
			case R.id.mbutton1:
				exitButtonClick();
				break;

			default:
				break;
			}
		}
	};


}



