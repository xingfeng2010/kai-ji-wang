package com.wenyu.kjw.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MySadapter  extends FragmentPagerAdapter{
	List<Fragment> frags ;
	public MySadapter(FragmentManager fm,List<Fragment> frags ) {
		super(fm);
		this.frags = frags ;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		return	(Fragment) ((frags==null)? 0:frags.get(arg0)); 
	}

	@Override
	public int getCount() {
		return	 ((frags==null)? 0:frags.size());
	}

}
