package com.wenyu.kaijiw;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends Activity {
	private TextView send;
	private EditText feedbackmessage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_feedback);
		initView();
	}

	private void initView() {
		send = (TextView) findViewById(R.id.feedbacktextView2);
		feedbackmessage = (EditText) findViewById(R.id.feedbackTextArea1);
		send.setOnClickListener(ol);
		feedbackmessage.setOnClickListener(ol);
	}
	OnClickListener ol = new OnClickListener(){
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.feedbacktextView2:
					if("".equals(feedbackmessage.getText().toString().trim())){
						Toast.makeText(FeedbackActivity.this, "文本框不能为空",Toast.LENGTH_SHORT).show();	
					}else{
						new AlertDialog.Builder(FeedbackActivity.this).setMessage("感谢您提出的宝贵建议").setPositiveButton("确定", new DialogInterface.OnClickListener() {		
							@Override
							public void onClick(DialogInterface dialog, int which) {
								FeedbackActivity.this.finish();
							}
						}).show();
					}

					break;
				}
			 }	
			};
}
