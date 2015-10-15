package com.wenyu.kaijiw.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.Customer;
import com.wenyu.Data.HttpP;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.db.DBManager;
import com.wenyu.kaijiw.CompanyCustomerActivity;
import com.wenyu.kaijiw.MainActivity;
import com.wenyu.kaijiw.PersonCustomerActivity;
import com.wenyu.kaijiw.R;
//import com.wenyu.kaijiw.SettingActivity;
import com.wenyu.kaijiw.SettingActivity;

/**
 * 我的
 * @author shasha
 *
 */
public class MyFragment extends MyBaseFragment {
	private ImageView personinfor ,Certifi,Release,back;
	TextView settings;
	private Button exit;
	private String phone,password,json1;
	private int customer_id;
	private Map<String,String> paramsValue;
	private String type,url = ConstantClassField.SERVICE_URL + "service/logins";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		   if (getArguments() != null) {
			   customer_id = getArguments().getInt("customer_id");
			    phone = getArguments().getString("phone");
			    password = getArguments().getString("password");
			    //Toast.makeText(getActivity(), customer_id+":"+phone+":"+password, Toast.LENGTH_SHORT).show();
	        }
		return inflater.inflate(R.layout.com_attestaionbefor, null);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initThread();
		
	}
	private void initView() {
		Certifi = (ImageView) getView().findViewById(R.id.mimageView3);
		settings = (TextView) getView().findViewById(R.id.mtextView6);
		back = (ImageView) getView().findViewById(R.id.mimageView1);
	    exit = 	(Button) getView().findViewById(R.id.mbutton1);
	    Certifi.setOnClickListener(ol);
	    back.setOnClickListener(ol);
	    exit.setOnClickListener(ol);

	}
	  public static MyFragment newInstance(int id,String phone,String password) {
	        MyFragment fragment = new MyFragment();
	        Bundle args = new Bundle();
	        args.putInt("customer_id", id);
	        args.putString("phone", phone);
	        args.putString("password", password);
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
//								System.out.println("我是答应的数据"+json1);
								if(("").equals(json1)){
									Toast.makeText(getActivity(), "请求失败 ", 1000).show();
								}else{
									type = initying(json1);
//									System.out.println("解析后"+type);
								}
							}
						else {
							Toast.makeText(getActivity(), "网络连接异常 ", 1000).show();
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
					String  type = jo.optString("type");
					return type;
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return "解析失败";
		}	
	  
	OnClickListener ol = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
           switch (v.getId()) {
       	case R.id.mimageView3:
       		Intent intent1;
       		if("企业".equals(type)){
       		 intent1 = new Intent(getActivity(),CompanyCustomerActivity.class);
       		}else{
       		 intent1 = new Intent(getActivity(),PersonCustomerActivity.class);
       		}
       		intent1.putExtra("phone", phone);
       		intent1.putExtra("customer_id", customer_id);
			startActivity(intent1);			
			break;   
	
		case R.id.mtextView6:
			Intent intent4 = new Intent(getActivity(),SettingActivity.class);
			startActivity(intent4);
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
	

	









