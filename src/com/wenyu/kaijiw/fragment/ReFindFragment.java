package com.wenyu.kaijiw.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wenyu.Data.Myone;
import com.wenyu.Data.ScreenManager;
import com.wenyu.kaijiw.FindThree;
import com.wenyu.kaijiw.R;
import com.wenyu.kjw.adapter.Myoneadpater;

/**
 * 影视人才
 * @author shasha
 */
public class ReFindFragment extends Fragment {
	private ListView listview;
	private Myoneadpater hla;
	private ImageView back;
	ScreenManager sm;
	private List<Myone> arrlist;
	Map<String, String> paramsValue;
	private TextView text;
	private  String [] str={"编剧组","制片组","导演组","美术组","摄影组","灯光组","录音组","服装组","化妆组","道具组","艺术院校"};
	private int [] images={R.drawable.bianju,R.drawable.production,R.drawable.director,R.drawable.writer,R.drawable.photography,R.drawable.light,R.drawable.recordsound,R.drawable.clothing,R.drawable.makeup,R.drawable.prop};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.find_people, null);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}
	private void initView() {
		sm =  ScreenManager.gerScreenManager();
		sm.pushActivity(getActivity());
		back= (ImageView)getView(). findViewById(R.id.imageView1);
		text = (TextView) getView().findViewById(R.id.textView1);
		listview = (ListView)getView(). findViewById(R.id.listView1);
		arrlist = new ArrayList<Myone>();
		for (int i = 0; i < images.length; i++) {
			Myone mh =	new Myone( images[i]);
			arrlist.add(mh);

		}
		
		hla =new Myoneadpater(arrlist, getActivity());
		listview.setAdapter(hla);
		hla.setListViewHeightBasedOnChildren(listview);
		listview.setDivider(null);
		listview.setOnItemClickListener(ocl);
		back.setVisibility(View.INVISIBLE);
	}

	OnItemClickListener ocl =new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent it0= new Intent(getActivity(),FindThree.class);
			it0.putExtra("key", str[arg2]);
			startActivity(it0);
		}

	};
}
