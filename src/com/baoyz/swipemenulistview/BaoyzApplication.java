package com.baoyz.swipemenulistview;


import android.app.Application;


public class BaoyzApplication extends Application {
	
    private static BaoyzApplication mInstance = null;
   
    
	public boolean isLogined = false;//是否登录


	public boolean isApprove = false;//是否认证
	
	public int customer_id = 0;//用户id
	
	

	@Override
    public void onCreate() {
		mInstance = this;
		super.onCreate();
	}
	

	public static BaoyzApplication getInstance() {
		return mInstance;
	}

}