package com.wenyu.kaijiw;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.wenyu.Data.Myhome;
import com.wenyu.kjw.adapter.Home_List_Adapter;

public class ShootEquipsActivity extends Activity {
	private RadioButton scan,collect;
	private Spinner sp1,sp2,sp3;
	private ArrayAdapter<String> adapter,a;
	private ListView listview;
	private String[] arr ={"s","s","s"};
	private List<Myhome> lists;
	private Home_List_Adapter hla;
	private int[] images ={R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.foot_first);
		initView();
		iffun();
		sp1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
			hla =new Home_List_Adapter(lists, ShootEquipsActivity.this);
			listview.setAdapter(hla);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent it1= new Intent(ShootEquipsActivity.this,Nearby.class);
				startActivity(it1);
			}
		});
	};

	private void initView() {
		sp1 = (Spinner) findViewById(R.id.city);
		sp2 = (Spinner)findViewById(R.id.ci);
		sp3 = (Spinner) findViewById(R.id.ty);
		scan = (RadioButton)findViewById(R.id.liu);
		collect = (RadioButton)findViewById(R.id.shoucang);
		listview = (ListView) findViewById(R.id.listview);
		lists = new ArrayList<Myhome>();
		for (int i = 0; i < arr.length; i++) {
//			Myhome my =	new Myhome(arr[i], images[i]);
//			lists.add(my);
		}
		scan.setOnClickListener(ol);
		collect.setOnClickListener(ol);
	}

	private void iffun() {
		
		String[] att =getIntent().getStringArrayExtra("s");
		String int1=getIntent().getStringExtra("1");
		if(int1.equals("2")){
			scan.setText("店家");
			collect.setText("器材");
			a = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arr );
			a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sp1.setAdapter(a); 
			sp1.setOnItemSelectedListener(ols);
			a = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arr );
			sp2.setAdapter(a);
			sp2.setOnItemSelectedListener(ols);
			a = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.spinner1) );
			sp3.setAdapter(a);
			sp3.setOnItemSelectedListener(ols);
		}
		
		
	}

	
	
	 OnItemSelectedListener  ols  = new   OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				switch(arg2){
				case 0:
				
					break;
				case 1:
					
				Toast.makeText(ShootEquipsActivity.this, "第二个", 1000).show();
				break;
				
				case 2:
					Toast.makeText(ShootEquipsActivity.this, "第二个", 1000).show();
					break;
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		};
	
	
	OnClickListener ol =new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			switch(arg0.getId()){
			case R.id.liu:
				
				scan.setTextColor(Color.WHITE);
				collect.setTextColor(Color.RED);
				scan.setBackgroundResource(R.drawable.redleft);
				collect.setBackgroundResource(R.drawable.writeright);
				break;

			case R.id.shoucang:
				collect.setTextColor(Color.WHITE);
				scan.setTextColor(Color.RED);
				collect.setBackgroundResource(R.drawable.right);
				scan.setBackgroundResource(R.drawable.left);
				break;
			}
		}

	};
}
