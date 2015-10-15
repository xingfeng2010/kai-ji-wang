package com.wenyu.kaijiw;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Urls;

public class EnsurePwdActivity extends Activity {
	private Map<String,String>paramsValue;
	private String phone,message,jsonData,reset = Urls.Url_Resetpwd;
	private EditText password,check;
	private Button submit;
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				Toast.makeText(EnsurePwdActivity.this, "���������쳣 ", 1000).show();
				break;
			case 1:
			
						new AlertDialog.Builder(EnsurePwdActivity.this).setMessage(message).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {		
							@Override
							public void onClick(DialogInterface dialog, int which) {
								  Intent it = new Intent(EnsurePwdActivity.this, MainActivity.class);
							      startActivity(it);
							      EnsurePwdActivity.this.finish();    
							}
						}).show();
				break;

			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.ensurepwd);

		initView();
	}
	private void initView() {
		phone = getIntent().getStringExtra("phone");
		password = (EditText) findViewById(R.id.ensurepwdPhonenumber);
		check = (EditText) findViewById(R.id.ensurepwd_check);
		submit = (Button) findViewById(R.id.ensurepwd_submit);
		submit.setOnClickListener(ol);
	
	}
    private void httpForget() {
		new Thread(new Runnable(){

			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				try {
					paramsValue=new HashMap<String, String>(); 
					paramsValue.put("phoneNumber",phone);
					paramsValue.put("pwd",password.getText().toString());
					if(NetWorkUtil.isNetAvailable(EnsurePwdActivity.this)){
						jsonData = new HttpP().sendPOSTRequestHttpClient(reset,paramsValue);
						System.out.println("��Ӧ�������"+jsonData);
						if(("").equals(jsonData)){
							handler.sendEmptyMessage(0);
						}else {
							 message = initying(jsonData);
							System.out.println("������"+message);
							handler.sendEmptyMessage(1);
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
    private boolean checkEdit(){
       if(password.getText().toString().trim().equals("")){
            Toast.makeText(EnsurePwdActivity.this, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
        }else if(!password.getText().toString().equals(check.getText().toString())){
            Toast.makeText(EnsurePwdActivity.this, "�������벻һ��", Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }
        return false;
   }
    private String initying(String json1) {
		
		try {
			JSONObject jo = new JSONObject(json1);
			  String message = "";

				   message = jo.optString("message"); 

				return message;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "����ʧ��";
	}	
OnClickListener  ol = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ensurepwd_submit:
				 if(checkEdit()){
					 httpForget();
				 }
				break;

			default:
				break;
			}

	
		}
	};
}
