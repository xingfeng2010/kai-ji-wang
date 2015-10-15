package com.wenyu.kaijiw;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class PersonInfoUpdateActivityPublishisue extends Activity {
	private TextView titleName,save;
	private EditText titleContent;
	private View contentDel;
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
	}
	OnClickListener ol = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.myinfor3_save:
				Intent it = new Intent(); 
				it.putExtra("result", titleContent.getText().toString());
				PersonInfoUpdateActivityPublishisue.this.setResult(fl, it);
				PersonInfoUpdateActivityPublishisue.this.finish();
				break;
			case R.id.perinfo3ImageView01:
				titleContent.setText(null);
				break;
			default:
				break;
			}

		}
	};
}
