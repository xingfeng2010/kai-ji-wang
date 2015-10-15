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
import com.wenyu.Data.Customer;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Urls;
import com.wenyu.Utils.CacheUtils;
import com.wenyu.Utils.CropParams;
import com.wenyu.Utils.ImageViewService;
import com.wenyu.Utils.UrlToImage;
import com.wenyu.db.DBManager;
import com.wenyu.kaijiw.MainActivity;
import com.wenyu.kaijiw.PersonCustomerActivity;
import com.wenyu.kaijiw.PersonInfoActivity;
import com.wenyu.kaijiw.R;
import com.wenyu.kaijiw.SettingActivity;


public class MineFragment extends MyBaseFragment {
	private ImageView personinfo,cusService,myphoto;
	RelativeLayout setting;
	private Button exit;
	private String phone,password,json1,imID;
	private int customer_id,cflag;
	private Map<String,String> paramsValue;
	private String image, url =Urls.Url_Logins;
	private RequestQueue queue;
	private RelativeLayout quit,myattestation,renzheng,kefu;
	private Bitmap bitmap,bitmap1;
	private CropParams mCropParams;
	private TextView mycounttextView5,mycounttextView4,mycounttextView3,mycounttextView2;
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
							//							System.out.println("读取缓存失败，当前没有缓存图片");
						}
						//						System.out.println(bitmap);
					}
					if(bitmap1!=null){
						System.out.println("图片已上传");
					}else{
						String img_path = CropParams.getRealFilePath(getActivity(),mCropParams.temUri);
						OutputStream outStream = new FileOutputStream(img_path); 
						System.out.println(img_path);
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
						outStream.flush();  
						outStream.close(); 
						UrlToImage.uploadIM(bitmap,getActivity());

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
		customer_id = BaoyzApplication.getInstance().customer_id;
		if (getArguments() != null) {
			//			customer_id = getArguments().getInt("customer_id");
			phone = getArguments().getString("phone");
			password = getArguments().getString("password");
			imID = getArguments().getString("imID");
			cflag = getArguments().getInt("cflag");
		}
		super.onCreate(savedInstanceState);
		return inflater.inflate(R.layout.my_attestaionbefor, null);}
	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initThread();

	}
	@Override
	public void onStop() {
		// TODO Auto-generat
		if(cflag==1){
			renzheng.setVisibility(View.GONE);
			renzheng.setLayoutParams(new RelativeLayout.LayoutParams(0,0));
		}else{
			renzheng.setVisibility(View.VISIBLE); 
		}
		super.onStop();
	}
	private void initView() {
		myphoto = (ImageView) getView().findViewById(R.id.mycountimageView2);
		myattestation =(RelativeLayout) getView().findViewById(R.id.myattestation);
		renzheng =(RelativeLayout) getView().findViewById(R.id.renzheng);
		kefu =(RelativeLayout) getView().findViewById(R.id.kefu);
		setting =(RelativeLayout) getView().findViewById(R.id.setting);
		exit = (Button) getView().findViewById(R.id.mycountbutton1);
		exit.setOnClickListener(ol);
		myattestation.setOnClickListener(ol);
		renzheng.setOnClickListener(ol);
		kefu.setOnClickListener(ol);
		setting.setOnClickListener(ol);
		if(cflag==1){
			renzheng.setVisibility(View.GONE);
			renzheng.setLayoutParams(new RelativeLayout.LayoutParams(0,0));
		}else{
			renzheng.setVisibility(View.VISIBLE); 
		}
		super.onPause();

	}
	public static MineFragment newInstance(int id,String phone,String password,String imID,int cflag) {
		MineFragment fragment = new MineFragment();
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
			case R.id.myattestation:
				if(cflag==1){
					Intent intent1 = new Intent(getActivity(),PersonInfoActivity.class);
					intent1.putExtra("image", image);
					intent1.putExtra("customer_id",customer_id+"");
					startActivity(intent1);
				}else{
					new AlertDialog.Builder(getActivity()).setMessage("请先认证").setPositiveButton("确定", new DialogInterface.OnClickListener() {		
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).show();
				}
				break;
			case R.id.renzheng:
				if(cflag==3){
					Intent intent2 = new Intent(getActivity(), PersonCustomerActivity.class);
					intent2.putExtra("phone", phone);
					intent2.putExtra("customer_id", customer_id+"");
					startActivity(intent2);
				}else{
					new AlertDialog.Builder(getActivity()).setMessage("认证已完成，无需重新认证").setPositiveButton("确定", new DialogInterface.OnClickListener() {		
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).show();
				}
				break;
				
			case R.id.setting:
				Intent intent4 = new Intent(getActivity(),SettingActivity.class);
				intent4.putExtra("phone", phone);
				startActivity(intent4);
				break;
			case R.id.mycountbutton1:
				exitButtonClick();
				break;
			case R.id.kefu:
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

				break;

			default:
				break;
			}
		}
	};


}



