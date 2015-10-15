package com.wenyu.kaijiw;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class PersonInfoUpdateActivityPublish extends Activity {
	private TextView titleName,save;
	private EditText titleContent;
	private View contentDel;
	private String title,content;
	private int fl;
	private ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_infor3);
		initView();
	}
	private void initView() {
		back = (ImageView) findViewById(R.id.myinfor3_back);
		titleName =	(TextView) findViewById(R.id.myinfor3_name);
		titleContent = (EditText) findViewById(R.id.perinfo3TextView01);
		contentDel = (View) findViewById(R.id.perinfo3ImageView01);
		save = (TextView) findViewById(R.id.myinfor3_save);
		title =getIntent().getStringExtra("title");
		content = getIntent().getStringExtra("content");
		fl =Integer.parseInt(getIntent().getStringExtra("f")) ;
		titleName.setText(title);
		titleContent.setText(content);
		save.setOnClickListener(ol);	
		contentDel.setOnClickListener(ol);
		back.setOnClickListener(ol);
		RelativeLayout layout = (RelativeLayout)findViewById(R.id.myinfor3relative2);
		ListView mycomlist = (ListView)findViewById(R.id.mycomlist);
		if(getIntent().getBooleanExtra("is_list", false)){
			save.setVisibility(View.GONE);
			layout.setVisibility(View.GONE);
			mycomlist.setVisibility(View.VISIBLE);
		}else{
			save.setVisibility(View.VISIBLE);
			layout.setVisibility(View.VISIBLE);
			mycomlist.setVisibility(View.GONE);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.com_item,
                new String[]{"title"},
                new int[]{R.id.textView1});
		mycomlist.setAdapter(adapter);
//		mycomlist.setAdapter(new ArrayAdapter<String>(this,getData(), R.layout.com_item,new String[]{"title"},
//                new int[]{R.id.textView1}));
		
		mycomlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				Intent it = new Intent(); 
				it.putExtra("result", getData().get(arg2).get("title"));
				PersonInfoUpdateActivityPublish.this.setResult(fl, it);
				PersonInfoUpdateActivityPublish.this.finish();
				
			}
		});
	}
	
	 private List<Map<String, String>> getData() {
	        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	 
	        Map<String, String> map = new HashMap<String, String>();
	        map.put("title", "影视棚");
	        list.add(map);
	 
	        map = new HashMap<String, String>();
	        map.put("title", "图片棚");
	        list.add(map);
	 
	        map = new HashMap<String, String>();
	        map.put("title", "演播棚");
	        list.add(map);
	         
	        return list;
	    }
//	 private List<String> getData(){
//         
//	        List<String> data = new ArrayList<String>();
//	        data.add("影视公司");
//	        data.add("后期制作");
//	        data.add("宣传发行");
//	        return data;
//	    }
	
	OnClickListener ol = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.myinfor3_save:
				Intent it = new Intent(); 
				it.putExtra("result", titleContent.getText().toString());
				PersonInfoUpdateActivityPublish.this.setResult(fl, it);
				PersonInfoUpdateActivityPublish.this.finish();
				break;
			case R.id.perinfo3ImageView01:
				titleContent.setText(null);
				break;
			case R.id.myinfor3_back:
				PersonInfoUpdateActivityPublish.this.finish();
			default:
				break;
			}

		}
	};
}
