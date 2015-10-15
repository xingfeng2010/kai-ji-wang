package com.wenyu.kaijiw;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wenyu.Data.HttpP;
import com.wenyu.Utils.ConstantClassField;

public class PublishlistissueAdd extends Activity{
	private String url =ConstantClassField.SERVICE_URL +"service/addCDByCustomerIDAndEquParam";
	private ImageView back;
	private TextView text,add,studiotypeedit,studionameedit,studiocontactedit,studiophoneedit,studiopriceedit;
	private ListView listview;
	private RelativeLayout studionamesecond,studiotypefirst,studiocontactthird,studiocontactphonefourth,studiopricefifth;
	private String ss,type,customer_id,temp_name,item_name,item_id,item_contact,item_contact_phone,item_price,equtype;
	private Map<String, String> paramsValue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publishstudio_detail);
		initView();
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				initThread();
				Intent publishstudioadd =  new Intent(PublishlistissueAdd.this,PublistStudio.class);
				publishstudioadd.putExtra("equ_type", equtype);
				publishstudioadd.putExtra("customer_id", customer_id);
				startActivity(publishstudioadd);
			}
		});
	}

	private void initThread() {
		new Thread(new Runnable(){

			@Override
			public void run() {
				temp_name=studiotypeedit.getText().toString();
				item_name = studionameedit.getText().toString();
				item_contact=studiocontactedit.getText().toString();
				item_contact_phone = studiophoneedit.getText().toString();
				item_price = studiopriceedit.getText().toString();
				paramsValue=new HashMap<String, String>(); 
				paramsValue.put("customer_id",customer_id);
				paramsValue.put("temp_name",temp_name);
				paramsValue.put("item_name",item_name);
				paramsValue.put("item_id",item_id);
				paramsValue.put("item_contact",item_contact);
				paramsValue.put("item_contact_phone",item_contact_phone);
				paramsValue.put("item_price",item_price);
				try {
					ss = new HttpP().sendPOSTRequestHttpClient(url.trim(),paramsValue);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(("null").equals(ss)&&("").equals(ss)){
					//handler.sendEmptyMessage(8);
				}else {
				}
			}

		}).start();

	}

	private void initView() {
		
		customer_id =getIntent().getStringExtra("customer_id");
		equtype =getIntent().getStringExtra("equ_type");
		temp_name=getIntent().getStringExtra("temp_name");
		item_name=getIntent().getStringExtra("item_name");
		item_id=getIntent().getStringExtra("item_id");
		item_contact=getIntent().getStringExtra("item_contact");
		item_contact_phone=getIntent().getStringExtra("item_contact_phone");
		item_price=getIntent().getStringExtra("item_price");
		back = (ImageView) findViewById(R.id.imageView1);
		text = (TextView) findViewById(R.id.textView1);
		add = (TextView) findViewById(R.id.textView2);
		add.setText("确定");
		studiotypeedit=(TextView) findViewById(R.id.studiotypeedit);
		studionameedit=(TextView) findViewById(R.id.studionameedit); 
		studiocontactedit=(TextView) findViewById(R.id.studiocontactedit);
		studiophoneedit=(TextView) findViewById(R.id.studiophoneedit);
		studiopriceedit=(TextView) findViewById(R.id.studiopriceedit);
		studiotypeedit.setText(temp_name);
		studionameedit.setText(item_name);
		studiocontactedit.setText(item_contact);
		studiophoneedit.setText(item_contact_phone);
		studiopriceedit.setText(item_price);
		studiotypefirst = (RelativeLayout) findViewById(R.id.studiotypefirst);
		studionamesecond = (RelativeLayout) findViewById(R.id.studionamesecond );
		studiocontactthird = (RelativeLayout) findViewById(R.id.studiocontactthird);
		studiocontactphonefourth = (RelativeLayout) findViewById(R.id.studiocontactphonefourth);
		studiopricefifth = (RelativeLayout) findViewById(R.id.studiopricefifth);
		studiotypefirst.setOnClickListener(ocl);
		studionamesecond.setOnClickListener(ocl);
		studiocontactthird.setOnClickListener(ocl);
		studiocontactphonefourth.setOnClickListener(ocl);
		studiopricefifth.setOnClickListener(ocl);
		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 0:
			//第一职业
			if(data!=null){
				Bundle bundle = data.getExtras();
				if(bundle!=null){
					type = bundle.getString("result");	
					//				 studiotypeedit.setText(type);
					//				 category.setTextColor(Color.BLACK);
				}
			}
			break;
		case 2:
			//公司名称
			updateCompany(data,studiotypeedit);
			break;
		case 3:
			//地址
			updateCompany(data,studionameedit);
			break;
		case 4:
			//联系方式
			updateCompany(data,studiocontactedit);
			break;
		case 5:
			//联系方式
			updateCompany(data,studiophoneedit);

			break;
		case 6:
			//联系方式
			updateCompany(data,studiopriceedit);
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
	OnClickListener ocl =new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch(arg0.getId()){
			case R.id.textView2:
				//				Intent publishstudioadd =  new Intent(PublishStudioAdd.this,PublistStudio.class);
				//				startActivity(publishstudioadd);
				break;
			case R.id.imageView1:
				PublishlistissueAdd.this.finish();
				break;
			case R.id.studiotypefirst:
				Intent it = new Intent(PublishlistissueAdd.this,PersonInfoUpdateActivityPublishisue.class);
				it.putExtra("title", "影视类别");
				it.putExtra("content", studiotypeedit.getText().toString());
				it.putExtra("f", "2");
				startActivityForResult(it, 2);
				break;
			case R.id.studionamesecond:
				Intent it1 = new Intent(PublishlistissueAdd.this,PersonInfoUpdateActivityPublishisue.class);
				it1.putExtra("title", "实景名称");
				it1.putExtra("content", studionameedit.getText().toString());
				it1.putExtra("f", "3");
				startActivityForResult(it1, 3);
				break;
			case R.id.studiocontactthird:
				Intent it2 = new Intent(PublishlistissueAdd.this,PersonInfoUpdateActivityPublishisue.class);
				it2.putExtra("title", "联系人");
				it2.putExtra("content", studiocontactedit.getText().toString());
				it2.putExtra("f", "4");
				startActivityForResult(it2, 4);
				break;

			case R.id.studiocontactphonefourth:
				Intent it4 = new Intent(PublishlistissueAdd.this,PersonInfoUpdateActivityPublishisue.class);
				it4.putExtra("title","联系电话" );
				it4.putExtra("content",studiophoneedit.getText().toString());
				it4.putExtra("f", "5");
				startActivityForResult(it4,5);
				break;
			case R.id.studiopricefifth:
				Intent it3 = new Intent(PublishlistissueAdd.this,PersonInfoUpdateActivityPublishisue.class);
				it3.putExtra("title","价格" );
				it3.putExtra("content",studiopriceedit.getText().toString());
				it3.putExtra("f", "6");
				startActivityForResult(it3,6);
				break;
			}
		}
	};
}
