package com.wenyu.kaijiw;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wenyu.Utils.DataCleanManager;

public class SettingActivity extends Activity {
 private RelativeLayout notify,accountSafe,privacy;
private TextView cleanCache;
private TextView about;
private ImageView back;
 private String phone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_setting);
		initView();
	}
	private void initView() {
        about = (TextView) findViewById(R.id.textView2);
        cleanCache = (TextView) findViewById(R.id.textView3);
        back =(ImageView) findViewById(R.id.settingimageView1);
        phone=getIntent().getStringExtra("phone");
        
//        notify.setOnClickListener(ol);
        back.setOnClickListener(ol);
		about.setOnClickListener(ol);
		cleanCache.setOnClickListener(ol);
		
	}
	OnClickListener ol = new OnClickListener() {		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.textView2:	
				Intent it2 = new Intent(SettingActivity.this,AboutActivity.class);
				startActivity(it2);
				break;
			case R.id.textView3:
				new AlertDialog.Builder(SettingActivity.this).setMessage("清理缓存后，会删除已加载的图片和信息").setPositiveButton("确定", new DialogInterface.OnClickListener() {		
					@Override
					public void onClick(DialogInterface dialog, int which) {
						DataCleanManager.cleanInternalCache(getApplicationContext());
					}
				}).show();
				break;
			case R.id.settingimageView1:
				SettingActivity.this.finish();
				break;
//			case R.id.settingrelative6:
//				Intent it3 = new Intent(SettingActivity.this,AccountActivity.class);
//				it3.putExtra("phone",phone);
//				startActivity(it3);
//				break;
//			case R.id.settingrelative7:
//				Intent it4 = new Intent(SettingActivity.this,PrivacyActivity.class);
//				startActivity(it4);
//				break;
			default:
				break;
			}
		}
	};

}
