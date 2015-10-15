package com.wenyu.kjw.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class my_zu_fgAda  extends FragmentPagerAdapter{
	private List<Fragment> fileList;

	public my_zu_fgAda(FragmentManager fm, List<Fragment> frs) {
		super(fm);
		fileList = frs;
	}
	public my_zu_fgAda(FragmentManager fm) {  
        super(fm);  
    }  
	
	 @Override
     public int getCount() {
         return fileList == null ? 0 : fileList.size();
     }
    
     @Override
     public Fragment getItem(int position) {
    	 return (Fragment) (fileList == null ? 0 : fileList.get(position));
     }

 }