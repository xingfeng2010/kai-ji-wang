package com.wenyu.kaijiw;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.BaoyzApplication;
import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.Customer;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Urls;
import com.wenyu.db.DBManager;

public class LoginActivity extends Activity {
	public static LoginActivity sSingleton;
	private EditText phonenumber,password;
	private Button submit,login,forget;
	private String jsonData,imID,imPwd;
	private Map<String,String> paramsValue,loginResult;
	private DBManager mgr;
	private Customer customer;
	private int customer_id,flag;
	private ImageView quit;
	private String message,type,url = Urls.Url_Logins;
	
	private static LoginActivity mInstance = null;
	
	public static LoginActivity  getInstance(){
		
		return mInstance;
	}

	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				Toast.makeText(LoginActivity.this, "网络连接异常 ", 1000).show();
				break;
			case 1:
				BaoyzApplication.getInstance().isLogined = true;
				Intent it = new Intent(LoginActivity.this, MainActivity.class);
				it.putExtra("imID", imID);
				it.putExtra("imPwd",imPwd);
				startActivity(it);
				LoginActivity.this.finish();    
				Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(LoginActivity.this, "用户名不存在或密码不正确", Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(LoginActivity.this, "返回上级重试 ", 1000).show();
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.login);
		sSingleton=this;
		mInstance=this;
		initView();
	}

	private void initView() {
		phonenumber =	(EditText)findViewById(R.id.login_phonenumber);
		password =	(EditText) findViewById(R.id.login_password);
		submit = (Button) findViewById(R.id.login_submit);
		login =  (Button) findViewById(R.id.login_register);
		forget = (Button) findViewById(R.id.login_forget);
		quit = (ImageView)findViewById(R.id.filmonquit); 
		submit.setOnClickListener(ol);
		login.setOnClickListener(ol);
		forget.setOnClickListener(ol);
		quit.setOnClickListener(ol);
	}


	private boolean checkEdit(){
		if(phonenumber.getText().toString().trim().equals("")){
			Toast.makeText(LoginActivity.this, "用户电话不能为空", Toast.LENGTH_SHORT).show();
		}else if(password.getText().toString().trim().equals("")){
			Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
		}else{
			return true;
		}
		return false;
	}
	private void httpLogin() {
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					paramsValue=new HashMap<String, String>(); 
					paramsValue.put("phoneNumber",phonenumber.getText().toString());
					paramsValue.put("pwd",password.getText().toString());
					if(NetWorkUtil.isNetAvailable(LoginActivity.this)){
						jsonData = new HttpP().sendPOSTRequestHttpClient(url,paramsValue);
						if(("").equals(jsonData)){
							handler.sendEmptyMessage(0);
						}else {
							message = initying(jsonData);
							if("".equals(message)){
								handler.sendEmptyMessage(2);
							}else{
								
								handler.sendEmptyMessage(1);
							}
						}

					}else {
						handler.sendEmptyMessage(0);
					}

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}).start();
	}
	/**
	 * 
	 * @param json1
	 * @return
	 */
	private String initying(String json1) {

		try {
			JSONObject jo = new JSONObject(json1);
			String  message = jo.optString("message");
			type = jo.optString("authentication_type");
			customer_id = jo.optInt("customer_id");
			imID  = jo.optString("imsdk_customUserID");
			imPwd = jo.optString("imsdk_password");
			//判断个人用户或企业用户
			if("个人用户".equals(type)){
				flag=1;
			}else if("企业用户".equals(type)){
				flag=2;
			}else if("未认证".equals(type)){
				flag = 0;
			}else if("个人".equals(type)){
				flag = 3;
			}
			else if("企业".equals(type)){
				flag = 4;
			}
			//判断是否手机号不存在或密码为空
			if("".equals(type)){
				return "";
			}else{	
				Customer queryItem = mgr.queryItem(phonenumber.getText().toString());
//				if(queryItem!=null){
//					queryItem.setPassword(password.getText().toString());
//					queryItem.setCertify(flag);
//					queryItem.setActive(1);
//					mgr.updatePassword(queryItem);
//					mgr.updateCertify(queryItem);
//					mgr.updateActive(queryItem);
//				}else{
					customer = new Customer();
					customer.setId(customer_id);
					customer.setPhonenumber(phonenumber.getText().toString());
					customer.setPassword(password.getText().toString());
					customer.setCertify(flag);
					customer.setActive(1);
					mgr.add(customer);
				}
//			}
			if(flag==0){
				return message+",请尽快认证";
			}
			return message;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "解析失败";
	}	

	OnClickListener  ol = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.login_submit:
				if(checkEdit()){
					mgr = new DBManager(LoginActivity.this);
					httpLogin();
				}
				break;
			case R.id.login_register:
				Intent it = new Intent(LoginActivity.this,RegisterActivity.class);
				it.putExtra("title", "注册");
				it.putExtra("submit", "注册"); 
				startActivity(it);
//				LoginActivity.this.finish();  
				break;
			case R.id.login_forget:
				Intent it1 = new Intent(LoginActivity.this,ResetPwdActivity.class);
				startActivity(it1);
				LoginActivity.this.finish();  
				break;
			case R.id.filmonquit:
				LoginActivity.this.finish();  
				break;
			default:
				break;
			}

		}
	};
}
