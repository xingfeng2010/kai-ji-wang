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
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.BaoyzApplication;
import com.example.mywork.util.NetWorkUtil;
import com.renn.rennsdk.http.HttpRequest.Base64;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.IntroData;
import com.wenyu.Data.IntronameData;
import com.wenyu.Data.IntrovalueData;
import com.wenyu.Data.Urls;
import com.wenyu.Utils.UrlToImage;
import com.wenyu.kjw.adapter.ShopIntroduceAdapter;
/**
 * 我的_个人信息
 * @author shasha
 *
 */
public class PersonInfoActivity extends Activity {
	private ImageView myphoto,name,back;
	private String  detailUrl = Urls.Url_Details;
	private RequestQueue queue;
	private String image,detailJson;
	private TextView detailName,detailNameEn,
	detailGender,detailAge,detailPosition,detailAddress;
	private GridView detailIntro;
	private Map<String,String> paramsValue,detailResult;
	private List<IntroData> li;
	private int customer_id;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				Toast.makeText(PersonInfoActivity.this, "网络连接异常 ", 1000).show();
				break;
			case 1:

				break;
			case 3:
				PerImage() ;
				initContent();
				if(li!=null&&li.size()!=0){
				ShopIntroduceAdapter sa = new ShopIntroduceAdapter(li, PersonInfoActivity.this);
				detailIntro.setAdapter(sa);
				}
				
				break;
			case 8:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_infor);
		initView();
		initThread();
		//PerImage() ;
	}

	private void initView() {
		myphoto =(ImageView) findViewById(R.id.perinfoimageView2);
		back=(ImageView) findViewById(R.id.perinfoimageView1);
		name = (ImageView) findViewById(R.id.perinfoImageView02);
		detailName = (TextView) findViewById(R.id.perinfotextView3);//名字
		detailNameEn = (TextView) findViewById(R.id.perinfotextView4);//英文名字
		detailAge = (TextView) findViewById(R.id.perinfotextView5);//年龄
		detailGender = (TextView) findViewById(R.id.perinfotextView6);//性别
		detailPosition =(TextView) findViewById(R.id.perinfotextView7);//职位
		detailAddress = (TextView) findViewById(R.id.perinfotextView8);//地区
		detailIntro =  (GridView)findViewById(R.id.perinfotextView9);
//		customer_id = getIntent().getStringExtra("customer_id");
		customer_id = BaoyzApplication.getInstance().customer_id;
		myphoto.setOnClickListener(ol);
		name.setOnClickListener(ol);
		back.setOnClickListener(ol);
	}
	private void initContent(){
		detailName.setText(detailResult.get("name"));
		detailNameEn.setText(detailResult.get("nameEn"));
		detailAge.setText(detailResult.get("age"));
		detailGender.setText(detailResult.get("gender"));
		detailPosition.setText(detailResult.get("position"));
		detailAddress.setText(detailResult.get("address"));
		
	}
	private void initThread() {
		new Thread(new Runnable(){

			@Override
			public void run() {

				try {
					paramsValue=new HashMap<String, String>();  
					paramsValue.put("id",customer_id+"");
					if(NetWorkUtil.isNetAvailable(PersonInfoActivity.this)){
							detailJson = new HttpP().sendPOSTRequestHttpClient(detailUrl,paramsValue);
//							System.out.println("我是答应的数据路径"+detailUrl+paramsValue);
							if(("").equals(detailJson)||("notfind").equals(detailJson)){
								handler.sendEmptyMessage(8);
							}else{
								detailResult = initying(detailJson);
//								System.out.println("解析后"+detailResult);
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
   private Map<String,String> initying(String detailJson){
	   detailResult =new HashMap<String, String>();  
	   try {
		JSONObject jo = new JSONObject(detailJson);
		image = jo.optString("image");
		String name = jo.optString("name");
		String nameEn =jo.optString("nameEn");
		 String age =jo.optString("age");
		 String gender =jo.optString("gender");
		 String position =jo.optString("position");
		 String address =jo.optString("address");
         detailResult.put("name", name);
         detailResult.put("nameEn", nameEn);
         detailResult.put("age", age);
         detailResult.put("gender", gender);
         detailResult.put("position", position);
         detailResult.put("address", address);
         //------------解析个人简介------------
         li = new ArrayList<IntroData>();
         List<IntronameData> inname = new ArrayList<IntronameData>();
 		List<IntrovalueData> invalue = new ArrayList<IntrovalueData>();
     	JSONArray   ja = jo.optJSONArray("fields");
		for (int j = 0; j < ja.length(); j++) {
			JSONObject  objj = ja.optJSONObject(j);
			JSONArray arr = objj.optJSONArray("array");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject ob = arr.optJSONObject(i);
				String infoname =ob.optString("name");
				String value = ob.optString("value");
				IntronameData intrname= new IntronameData(infoname);
				IntrovalueData intrvalue= new IntrovalueData(value);
				inname.add(intrname);
				invalue.add(intrvalue);
				IntroData id = 	new IntroData(inname, invalue, "", "","","");
				li.add(id);
			}
		}	
         
	} catch (JSONException e) {
		e.printStackTrace();
	}
   return detailResult;
   }
	
	
	
	
	private void PerImage() {
		try {
               queue =  Volley.newRequestQueue(PersonInfoActivity.this);
             //  image = getIntent().getStringExtra("image");       		   
				UrlToImage.getImage(image,queue,myphoto);	                      
				} catch (Exception e) {
					e.printStackTrace();
					 Toast.makeText(PersonInfoActivity.this, "无法加载图片",Toast.LENGTH_SHORT).show();  
				}
	}
	OnClickListener ol = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
		  switch (v.getId()) {
		case R.id.perinfoimageView2:
			Intent intent1 = new Intent(PersonInfoActivity.this,MypersonInfoActivity.class);
			 intent1.putExtra("image", image);
			 intent1.putExtra("name", detailResult.get("name"));
			 intent1.putExtra("gender", detailResult.get("gender"));
			 intent1.putExtra("address", detailResult.get("address"));
			 startActivityForResult(intent1, 1001);
//			startActivity(intent1);
			break;
//		case R.id.perinfoImageView02:
//			
//			break;
		case R.id.perinfoimageView1:
			PersonInfoActivity.this.finish();
			break;
		default:
			break;
		}
			
		}
	};
	
		String picUrl;
		String img64;
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1001://历史地址
			if(data!=null){
				picUrl = data.getStringExtra("persion_imag");
				if(!TextUtils.isEmpty(picUrl) ){
					try {
						Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(picUrl));
						ByteArrayOutputStream bao = new ByteArrayOutputStream();
						bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
						byte[] ba = bao.toByteArray();
						img64 = Base64.encodeBytes(ba);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
						
				}	
			}
			
			break;
		case 1:
			
			
			break;

		default:
			break;
		}
		
		
	};
	
	String jsonData;
	private void updataPersion() {


		new Thread(new Runnable(){

			@Override
			public void run() {
				try {

					paramsValue=new HashMap<String, String>(); 
					paramsValue.put("customer_id",customer_id+"");
//					System.out.println("个人信息"+customer_id);
					 //打开此处，把数据放进去
					paramsValue.put("name",detailName.getText().toString());//名字
					paramsValue.put("nameEn",detailNameEn.getText().toString());//英文名字
					paramsValue.put("position",detailPosition.getText().toString());//职位
					paramsValue.put("introduction","");//个人简介
					paramsValue.put("gender",detailGender.getText().toString());//性别
					paramsValue.put("age",detailAge.getText().toString());//年龄
					paramsValue.put("birthday",detailAge.getText().toString());//生日
					paramsValue.put("regional",detailAddress.getText().toString());//区域城市
					
					//一下是demo
//					paramsValue.put("name", "张三");//名字
//					paramsValue.put("nameEn","zhangsan");//英文名字
//					paramsValue.put("position","不告诉你");//职位
//					paramsValue.put("introduction","你猜");//个人简介
//					paramsValue.put("gender","1");//性别
//					paramsValue.put("birthday","2016-01-20");//生日
//					paramsValue.put("regional","北京市");//区域城市
					
					paramsValue.put("image", img64);
					if(NetWorkUtil.isNetAvailable(PersonInfoActivity.this)){
						jsonData = new HttpP().sendPOSTRequestHttpClient(Urls.Url_PersonInfoUpdate,paramsValue);
						String ss =getParse(jsonData);
//								System.out.println("我是认证数据元"+ss+url+paramsValue);
						if(TextUtils.equals(ss, "success")){
							handler.sendEmptyMessage(1);
							
						}else if(("").equals(jsonData)){
							Toast.makeText(PersonInfoActivity.this, "网络连接异常 ", 1000).show();
						}else if(("fail").equals(jsonData)){
							Toast.makeText(PersonInfoActivity.this, "验证信息失败，请检查 ", 1000).show();
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			updataPersion();
			
		}
		return super.onKeyDown(keyCode, event);
	}

}
