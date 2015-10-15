package com.wenyu.kaijiw;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.cookie.Cookie;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.Customer;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Urls;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.db.DBManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @class   ע����
 * @author $shenshasha
 * @params  ע�ᵽIM �ͷ�����
 * @return  ״̬
 */
public class RegisterActivity extends Activity {
	private EditText phonenumber,password,checkcode;
	private TextView title,useagreement;
	private CheckBox agrement;
	private Button submit,getcheck;
	private String jsonData,imID,imPwd;
	private Map<String,String> paramsValue;
	private Customer customer;
	private DBManager mgr;
	private ImageView back;
	private RadioButton single;
	private RadioButton com;
	private int customer_id;
	private String[] GENRES = {"�����û�", "��ҵ�û�"};
	private String titleflag,message,url = Urls.Url_Register,check = Urls.Url_Checkcode,authType="";
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){

			case 0:
				Toast.makeText(RegisterActivity.this, "���������쳣 ", 1000).show();
				break;
			case 1:
				intent();
				Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(RegisterActivity.this, "�����ϼ����� ", 1000).show();
				break;
			case 8:
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.register);
		initView();
	}
	/**
	 * ע��ɹ���ת�� ��ҳ��  ����״̬���浽DBManager
	 */
	private void  intent(){
		Intent it = new Intent(RegisterActivity.this, MainActivity.class);
		it.putExtra("imID", imID);
		it.putExtra("imPwd",imPwd);
		startActivity(it);
		if(LoginActivity.getInstance() != null && !LoginActivity.getInstance().isFinishing()){
			LoginActivity.getInstance().finish();
		}
		RegisterActivity.this.finish();
		customer = new Customer();
		customer.setId(customer_id);
		customer.setPhonenumber(phonenumber.getText().toString());	
		customer.setPassword(password.getText().toString());
		customer.setActive(1);
		if("����".equals(authType)){
			customer.setCertify(3);
		}else if("��ҵ".equals(authType)){
			customer.setCertify(4);
		}else{
			customer.setCertify(0);
		}

		mgr = new DBManager(RegisterActivity.this);
//		Customer queryItem = mgr.queryItem(customer.getPhonenumber());
//		if(queryItem!=null){
//			return;
//		}
		mgr.add(customer);
	}
	private void initView() {
		title = (TextView) findViewById(R.id.title_register);
		back = (ImageView) findViewById(R.id.title_back);
		phonenumber =	(EditText) findViewById(R.id.registerPhonenumber);
		password =	(EditText) findViewById(R.id.registerPassword);
		checkcode = (EditText) findViewById(R.id.register_checkcode);
		single = (RadioButton) findViewById(R.id.typ_single);
		com = (RadioButton) findViewById(R.id.typ_com);
		//��ȡ��֤��
		getcheck = (Button) findViewById(R.id.getCheckcode);
		submit = (Button) findViewById(R.id.register_submit);
		agrement = (CheckBox) findViewById(R.id.kj_agreement);
		useagreement = (TextView)findViewById(R.id.kj_personagreement);
		titleflag = getIntent().getStringExtra("title");
		//���ò���������ɫ
		title.setText(titleflag);
		submit.setText(getIntent().getStringExtra("submit"));
		CheckBox agreement = (CheckBox)findViewById(R.id.kj_agreement);
		if("��������".equals(titleflag)){
			agreement.setChecked(true);
			agreement.setAlpha(0);
			useagreement.setText("");
		}
		useagreement.setOnClickListener(ol);
		back.setOnClickListener(ol);
		getcheck.setOnClickListener(ol);
		submit.setOnClickListener(ol);
	}
	/*
	 * ���ı������жϿ�
	 */
	private boolean checkEdit(){
		if(phonenumber.getText().toString().trim().equals("")){
			Toast.makeText(RegisterActivity.this, "�û�������Ϊ��", Toast.LENGTH_SHORT).show();
		}else if(password.getText().toString().trim().equals("")){
			Toast.makeText(RegisterActivity.this, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
		}else if(checkcode.getText().toString().trim().equals("")){
			Toast.makeText(RegisterActivity.this, "��֤�벻��Ϊ��", Toast.LENGTH_SHORT).show();
		}else if(ConstantClassField.CHECK_CODE.trim().equals("")||!ConstantClassField.CHECK_CODE.equals(checkcode.getText().toString())){
			Toast.makeText(RegisterActivity.this, "��������ȷ����֤��", Toast.LENGTH_SHORT).show();
		}else if(!agrement.isChecked()){
			Toast.makeText(RegisterActivity.this, "��ȷ�Ͽ������û�ʹ��Э��", Toast.LENGTH_SHORT).show();
		}else{
			return true;
		}
		return false;
	}
	/**
	 * �ύע����Ϣ�ӿ�
	 */
	private void httpRegister() {
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					paramsValue=new HashMap<String, String>(); 
					paramsValue.put("phoneNumber",phonenumber.getText().toString());
					paramsValue.put("pwd",password.getText().toString());
					paramsValue.put("imsdk_customUserID",System.currentTimeMillis()+"@oet_china");
					paramsValue.put("imsdk_password","123456");
					paramsValue.put("authentication_type",authType);
					if(NetWorkUtil.isNetAvailable(RegisterActivity.this)){
						jsonData = new HttpP().sendPOSTRequestHttpClient(url,paramsValue);
						if(("").equals(jsonData)){
							handler.sendEmptyMessage(0);
						}else {
							imID = System.currentTimeMillis()+"@oet_china";
							imPwd = "123456";
							message = initying(jsonData,1);
							if(customer_id==0){
								handler.sendEmptyMessage(3);
								return;
							}
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
	/**
	 * �ύ��֤������
	 */
	private void httpCheck() {
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					paramsValue=new HashMap<String, String>(); 
					paramsValue.put("phoneNumber",phonenumber.getText().toString());
					if(NetWorkUtil.isNetAvailable(RegisterActivity.this)){
						jsonData = new HttpP().sendPOSTRequestHttpClient(check,paramsValue);
						//						int start = jsonData.indexOf("{");
						//						jsonData = jsonData.substring(start);
						if(("").equals(jsonData)){
							handler.sendEmptyMessage(0);
						}else {
							message = initying(jsonData,1);
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
	// ��������
	private String initying(String json1,int flag) {
		String message = "";
		try {
			JSONObject jo = new JSONObject(json1);
			message = jo.optString("message"); 
			customer_id = jo.optInt("customer_id");
			ConstantClassField.CHECK_CODE = jo.optString("checkcode");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return message;
	}	
	/**
	 * �ж� authType ���� 
	 * @return 
	 */

	private boolean PickNum(){

		if(single.isChecked()){
			authType = "����";	
			return true;
		}else if(com.isChecked()){
			authType ="��ҵ";
			return true;
		}else{
			Toast.makeText(RegisterActivity.this, "��ѡ��һ�����", 0).show();	
		}
		return false;
	}    


	OnClickListener  ol = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.getCheckcode:
				//������֤��
				httpCheck();
				break;
			case R.id.title_back:
				RegisterActivity.this.finish();
				break;
			case R.id.register_submit:
				//�ύע��
				if(	PickNum()&&checkEdit()){
					httpRegister();
				}
				break;
			case R.id.kj_personagreement:
				Intent it1 = new Intent(RegisterActivity.this, AgreementActivity.class);
				it1.putExtra("type", "agreement");
				startActivity(it1);
				break;
			default:
				break;
			}


		}
	};

}
