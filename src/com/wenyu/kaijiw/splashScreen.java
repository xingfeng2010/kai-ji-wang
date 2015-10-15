package com.wenyu.kaijiw;

import com.baoyz.swipemenulistview.BaoyzApplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class splashScreen extends Activity {
	 @Override
	    public void onCreate(Bundle icicle) {
	        super.onCreate(icicle);
	        getWindow().setFormat(PixelFormat.RGBA_8888);
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);

	        setContentView(R.layout.splashscreen);

//	        //Display the current version number
//	        PackageManager pm = getPackageManager();
//	        try {
//	            PackageInfo pi = pm.getPackageInfo("org.wordpress.android", 0);
//	           // TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
//	           // versionNumber.setText("Version " + pi.versionName);
//	        } catch (NameNotFoundException e) {
//	            e.printStackTrace();
//	        }

	        new Handler().postDelayed(new Runnable() {
	            public void run() {
	                /* Create an Intent that will start the Main WordPress Activity. */
	            	Intent mainIntent = new Intent();
	            	if (BaoyzApplication.getInstance().isLogined) {
	            		mainIntent.setClass(splashScreen.this, MainActivity.class);
	            	} else {
	            		mainIntent.setClass(splashScreen.this, LoginActivity.class);
	            	}
	                
	                splashScreen.this.startActivity(mainIntent);
	                splashScreen.this.finish();
	            }
	        }, 1500); //1500 for release

	    }
}
