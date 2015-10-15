package com.wenyu.kaijiw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CompanyTypeActivity extends Activity {
	private ListView listView;
	private TextView save;
	private String[] GENRES = {"Ӱ�ӹ�˾", "Ӱ��/�������޹�˾", "ʵ������"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_two);
		initView();
		

	}
	
	private void initView() {
		listView = (ListView) findViewById(R.id.singlelistView1);
		listView.setAdapter(new ArrayAdapter<String>(this,
	              android.R.layout.simple_list_item_single_choice, GENRES));
		save = (TextView) findViewById(R.id.companybuttonView);
		save.setOnClickListener(ol);
		
	}
	OnClickListener ol = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.companybuttonView:
				PickNum();
				break;

			default:
				break;
			}
		}
	};

	private void PickNum(){
		int position = listView.getCheckedItemPosition();
		if(ListView.INVALID_POSITION != position){
			Intent it = new Intent();    	
			it.putExtra("category", GENRES[position]);
			CompanyTypeActivity.this.setResult(1, it);
			CompanyTypeActivity.this.finish();	
			return;
		}
		Toast.makeText(CompanyTypeActivity.this, "��ѡ��һ�����", 0).show();
	}
	
}
