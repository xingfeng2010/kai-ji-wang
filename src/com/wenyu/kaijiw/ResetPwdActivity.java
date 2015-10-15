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

import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Urls;
import com.wenyu.Utils.ConstantClassField;

public class ResetPwdActivity extends Activity {
	private Button submit,getCheckCode;
	private ImageView filmonresetback;
	private Map<String,String>paramsValue;
	private EditText checkCode,phoneNumber;
	private String jsonData,message,check = Urls.Url_Checkcode;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				Toast.makeText(ResetPwdActivity.this, "网络连接异常 ", 1000).show();
				break;
			case 1:
				  Intent it = new Intent(ResetPwdActivity.this, MainActivity.class);
				      startActivity(it);
				      ResetPwdActivity.this.finish();    
				  Toast.makeText(ResetPwdActivity.this, "", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				  Toast.makeText(ResetPwdActivity.this, "用户名不存在或密码不正确", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				 Toast.makeText(ResetPwdActivity.this, message, Toast.LENGTH_SHORT).show();
				 break;	
			case 5:
				Toast.makeText(ResetPwdActivity.this, "返回上级重试 ", 1000).show();
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.resetpwd);

		initView();
	}
	private void initView() {
		    phoneNumber = (EditText) findViewById(R.id.resetpwdPhonenumber);
		    checkCode = (EditText) findViewById(R.id.resetpwd_checkcode);
		    getCheckCode = (Button) findViewById(R.id.resetpwdCheckcode);
     		submit = (Button) findViewById(R.id.resetpwd_submit);
     		filmonresetback = (ImageView) findViewById(R.id.filmonresetback);
     		filmonresetback.setOnClickListener(ol);
     		getCheckCode.setOnClickListener(ol);
	        submit.setOnClickListener(ol);
	        
	}
	 private void httpCheck() {
			new Thread(new Runnable(){
				/* (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				@Override
				public void run() {
					try {
						paramsValue=new HashMap<String, String>(); 
						paramsValue.put("phoneNumber",phoneNumber.getText().toString());
						if(NetWorkUtil.isNetAvailable(ResetPwdActivity.this)){
							jsonData = new HttpP().sendPOSTRequestHttpClient(check,paramsValue);
							int start = jsonData.indexOf("{");
							jsonData = jsonData.substring(start);
							System.out.println("截取后的数据"+jsonData);
							if(("").equals(jsonData)){
								handler.sendEmptyMessage(0);
							}else {
								 message = initying(jsonData);
								System.out.println("解析后"+message);
								handler.sendEmptyMessage(3);
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
	   private String initying(String json1){
			
			try {
				JSONObject jo = new JSONObject(json1);
				  String message = "";
					    message = jo.optString("message"); 
					    ConstantClassField.CHECK_CODE = jo.optString("checkcode");
				 
					return message;
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return "解析失败";
		}	 
	   private boolean checkEdit(){
	        if(phoneNumber.getText().toString().trim().equals("")){
	            Toast.makeText(ResetPwdActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
	        }else if(checkCode.getText().toString().trim().equals("")){
	            Toast.makeText(ResetPwdActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
	        }else if(ConstantClassField.CHECK_CODE.trim().equals("")||!ConstantClassField.CHECK_CODE.equals(checkCode.getText().toString())){
	            Toast.makeText(ResetPwdActivity.this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
	        }else{
	            return true;
	        }
	        return false;
	   }
OnClickListener  ol = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.resetpwd_submit:
				if(checkEdit()){
				Intent it1 = new Intent(ResetPwdActivity.this,EnsurePwdActivity.class);
			    it1.putExtra("phone", phoneNumber.getText().toString());
				startActivity(it1);
				ResetPwdActivity.this.finish();
				}
				break;
			case R.id.resetpwdCheckcode:
				 httpCheck();
				break;
			case R.id.filmonresetback:
				ResetPwdActivity.this.finish();
				break;
			default:
				break;
			}

	
		}
	};

}
