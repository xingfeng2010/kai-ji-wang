package com.wenyu.kaijiw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class AgreementActivity extends Activity {  
 private ImageView back;
 private TextView title,content;
 private String type ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_agreement);
		initView();
	}

	private void initView() {
       back = (ImageView) findViewById(R.id.agreementimageView1);
       title = (TextView) findViewById(R.id.agreementtextView1);
       content = (TextView) findViewById(R.id.agreementTextView01);
       type =  getIntent().getStringExtra("type");
       if(!TextUtils.isEmpty(type)){
          title.setText("《用户使用协议》");  	   
    	  content.setText(R.string.useagreement); 
       }
	   back.setOnClickListener(ol);
	}
	OnClickListener ol = new OnClickListener(){
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.agreementimageView1:
				AgreementActivity.this.finish();
				break;

			}
		 }	
		};
}   
