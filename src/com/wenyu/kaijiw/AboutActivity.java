package com.wenyu.kaijiw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends Activity {  
	private ImageView feedback;
	private TextView personal,about,fankui;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_about);
		initView();
	}

	private void initView() {
		feedback = (ImageView) findViewById(R.id.aboutimageView1);
		fankui=(TextView) findViewById(R.id.aboutTextView02);
		personal = (TextView) findViewById(R.id.aboutTextView03);
		about= (TextView) findViewById(R.id.abouttextView1);
		about.setText("关于开机网");
		feedback.setOnClickListener(ol);
		personal.setOnClickListener(ol);
		fankui.setOnClickListener(ol);
	}
	OnClickListener ol = new OnClickListener(){
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.aboutTextView02:
					Intent intent1 = new Intent(AboutActivity.this,FeedbackActivity.class);
					startActivity(intent1);		
					break;
				case R.id.aboutTextView03:
					Intent intent2 = new Intent(AboutActivity.this,AgreementActivity.class);
					intent2.putExtra("type", "");
					startActivity(intent2);
					break;
				case R.id.aboutimageView1:
					AboutActivity.this.finish();
				}
			 }	
			};
}   
