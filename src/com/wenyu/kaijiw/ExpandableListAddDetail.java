package com.wenyu.kaijiw;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wenyu.Data.HttpP;
import com.wenyu.Utils.ConstantClassField;

/**
 * 添加器材（数量and 价格）
 */
public class ExpandableListAddDetail extends Activity {
	String url =ConstantClassField.SERVICE_URL +"service/addQCByCustomerIDAndEquID";
	private ImageView backimage;
	private TextView okbtn,textView5,textView6;
	private RelativeLayout count,price;
	private String type,equ_type;
	private String customer_id,ss,item_count,item_id,item_price;
	private Map<String, String> paramsValue;
	private ImageView back;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expandablelistadddetail);
		initView();

		okbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				initThread();
				Toast.makeText(ExpandableListAddDetail.this, ss, 1000).show();
				Intent it = new Intent(ExpandableListAddDetail.this,ExpandableList.class);
				it.putExtra("customer_id", customer_id);
				it.putExtra("equ_type", equ_type);
				startActivity(it);
			}
		});
	}

	private void initView() {
		equ_type = getIntent().getStringExtra("equ_type");
		item_id = getIntent().getStringExtra("item_id");
		item_count = getIntent().getStringExtra("item_count");
		item_price = getIntent().getStringExtra("item_price");
		customer_id = getIntent().getStringExtra("customer_id");
		backimage =  (ImageView) findViewById(R.id.imageView1);
		okbtn =(TextView) findViewById(R.id.textView2);
		count = (RelativeLayout) findViewById(R.id.relativeLayout3);
		price = (RelativeLayout) findViewById(R.id.relativeLayout4);
		textView5 = (TextView) findViewById(R.id.textView5);
		textView6 = (TextView) findViewById(R.id.textView6);
		textView5.setText(item_count);
		textView6.setText(item_price);
		count.setOnClickListener(ocl);
		price.setOnClickListener(ocl);

		backimage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ExpandableListAddDetail.this.finish();
				
			}
		});
	}
	private void initThread() {
		new Thread(new Runnable(){

			@Override
			public void run() {
				item_count =textView5.getText().toString();
				item_price =textView6.getText().toString();
				paramsValue=new HashMap<String, String>(); 
				paramsValue.put("customer_id",customer_id);
				paramsValue.put("item_id",item_id);
				paramsValue.put("item_count",item_count);
				paramsValue.put("item_price",item_price);
				try {
					ss = new HttpP().sendPOSTRequestHttpClient(url.trim(),paramsValue);
//					System.out.println("添加的路径"+url+paramsValue);
					String d = getParse(ss);
//					System.out.println("发布数据"+d);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(("null").equals(ss)&&("").equals(ss)){
					//					handler.sendEmptyMessage(8);
				}else {
					//					pubs = parseJson(ss);
					//					handler.sendEmptyMessage(1);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			//第一职业
			//			if(data!=null){
			//				Bundle bundle = data.getExtras();
			//				if(bundle!=null){
			//				 type = bundle.getString("category");	
			////				 category.setText(type);
			////				 category.setTextColor(Color.BLACK);
			//				}
			//			}

			break;
		case 2:
			//公司名称
			updateCompany(data,textView5);
			break;
		case 3:
			//地址
			updateCompany(data,textView6);
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
	OnClickListener ocl = 	new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch(arg0.getId()){
			case R.id.relativeLayout3:
				Intent it = new Intent(ExpandableListAddDetail.this,PersonInfoUpdateActivityPublish.class);
				it.putExtra("title", "数量");
				it.putExtra("content", textView5.getText().toString());
				it.putExtra("f", "1");
				startActivityForResult(it, 2);
				break;
			case R.id.relativeLayout4:
				Intent it1 = new Intent(ExpandableListAddDetail.this,PersonInfoUpdateActivityPublish.class);
				it1.putExtra("title", "价格");
				it1.putExtra("content", textView6.getText().toString());
				it1.putExtra("f", "1");
				startActivityForResult(it1, 3);
				break;
			}
		}
	};
}
