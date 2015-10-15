package com.wenyu.kaijiw.fragment;

import java.util.ArrayList;
import java.util.List;

import com.wenyu.Data.ScreenManager;
import com.wenyu.kaijiw.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;

/**
 * ◊„º£
 * @author shasha
 *
 */
public class FootFragment extends Fragment {
	private List<Fragment> li;
	/**
	 * ‰Ø¿¿
	 */
	private FootScan fs;
	/**
	 *  ’≤ÿ
	 */
	private FootConnect fsc;
	private RadioButton  scan,collect;
	private  ImageView image;
	private String customer_id;
	ScreenManager sm;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.footl_list_title1, null);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}
	
	public  FootFragment newInstance(int id,String phone,String password,int cflag) {
		//		customer_id = id;
		FootFragment fragment = new FootFragment();
		Bundle args = new Bundle();
		args.putInt("customer_id", id);
		args.putString("phone", phone);
		args.putString("password", password);
		args.putInt("cflag", cflag);
		fragment.setArguments(args);
		return fragment;
	}
	private void initView() {
		sm =  ScreenManager.gerScreenManager();
		sm.pushActivity(getActivity());
		image = (ImageView) getView().findViewById(R.id.image);
		scan = (RadioButton)getView().findViewById(R.id.liu);
		scan.setText("‰Ø¿¿");
		collect = (RadioButton)getView().findViewById(R.id.shoucang);
		collect.setText(" ’≤ÿ");
		scan.setOnClickListener(ol);
		collect.setOnClickListener(ol);
		li = new ArrayList<Fragment>();
		fs = new FootScan();
		fsc =  new FootConnect();
		li.add(fs);
		li.add(fsc);
		getChildFragmentManager().beginTransaction().add(R.id.frame, fs).show(fs).add(R.id.frame, fsc).hide(fsc).commit();
		image.setOnClickListener(ol);
	}


	OnClickListener ol =new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			switch(arg0.getId()){
			case R.id.liu:
				scan.setTextColor(Color.WHITE);
				collect.setTextColor(Color.RED);
				scan.setBackgroundResource(R.drawable.redleft);
				collect.setBackgroundResource(R.drawable.writeright);
				replace(0);
				break;
			case R.id.shoucang:
				collect.setTextColor(Color.WHITE);
				scan.setTextColor(Color.RED);
				collect.setBackgroundResource(R.drawable.right);
				scan.setBackgroundResource(R.drawable.left);
				replace(1);
				break;
			case R.id.image:
				image.setVisibility(View.INVISIBLE);
				break;
			}
		}
	};
	private void replace(int i) {
		for (int j = 0; j < li.size(); j++) {
			if(j==i){
				getChildFragmentManager().beginTransaction().show(li.get(i)).commit();
			}else{
				getChildFragmentManager().beginTransaction().hide(li.get(j)).commit();
			}
		}

	}
	
}


