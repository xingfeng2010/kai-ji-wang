package com.wenyu.kjw.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public	class MyViewPageAdapter extends PagerAdapter  {  
	  
    private List<View> mViewList;


    public MyViewPageAdapter(List<View> views) {  
        mViewList = views;  
    }  

    @Override  
    public int getCount() {  
        return mViewList.size();  
    }  

    @Override  
    public boolean isViewFromObject(View arg0, Object arg1) {  

        return arg0 == arg1;  
    }  

    @Override  
    public Object instantiateItem(ViewGroup container, int position) {  
        container.addView(mViewList.get(position), 0);  
        return mViewList.get(position%mViewList.size());  
    }  
    @Override  
    public void destroyItem(ViewGroup container, int position, Object object) {  
        container.removeView(mViewList.get(position));  
    }  
    
    
    
    }
