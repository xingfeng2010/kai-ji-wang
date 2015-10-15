package com.wenyu.kaijiw;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AccountActivity extends Activity {
	private String phone;
	private TextView phoneNumber;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_accountsafe);
		initView();
	}

	private void initView() {
	 phone = getIntent().getStringExtra("phone");
	 phoneNumber = (TextView) findViewById(R.id.accountImageView02);
	 phoneNumber.setText(phone);
	}
}
