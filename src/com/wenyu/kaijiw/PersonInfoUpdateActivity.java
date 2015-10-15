package com.wenyu.kaijiw;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonInfoUpdateActivity extends Activity {
	private TextView titleName,save;
	private EditText titleContent;
	private ImageView contentDel,back;
	private String title,content;
	private int fl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_infor3);
		initView();

	}

	private void initView() {
		titleName =	(TextView) findViewById(R.id.myinfor3_name);
		back= (ImageView) findViewById(R.id.myinfor3_back);
		titleContent = (EditText) findViewById(R.id.perinfo3TextView01);
		contentDel = (ImageView) findViewById(R.id.perinfo3ImageView01);
		save = (TextView) findViewById(R.id.myinfor3_save);
		title =getIntent().getStringExtra("title");
		content = getIntent().getStringExtra("content");
		titleName.setText(title);
		titleContent.setText(content);
		save.setOnClickListener(ol);	
		contentDel.setOnClickListener(ol);	
		back.setOnClickListener(ol);
	}
	OnClickListener ol = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.myinfor3_save:
				Intent it = new Intent(); 
				it.putExtra("result", titleContent.getText().toString());
				PersonInfoUpdateActivity.this.setResult(2, it);
				PersonInfoUpdateActivity.this.finish();
				break;
			case R.id.perinfo3ImageView01:
				titleContent.setText(null);
				break;
			case R.id.myinfor3_back:
				PersonInfoUpdateActivity.this.finish();
				break;
			default:
				break;
			}

		}
	};
}
