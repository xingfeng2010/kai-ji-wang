package com.wenyu.kaijiw.fragment;

import java.util.ArrayList;
import java.util.List;
import com.wenyu.Data.Myone;
import com.wenyu.kaijiw.ArtSchool;
import com.wenyu.kaijiw.FindTwoAlliance;
import com.wenyu.kaijiw.Findfilmcompany;
import com.wenyu.kaijiw.Findfilmpeople;
import com.wenyu.kaijiw.Findfilmproject;
import com.wenyu.kaijiw.Findissuepublicity;
import com.wenyu.kaijiw.Findproduction;
import com.wenyu.kaijiw.R;
import com.wenyu.kjw.adapter.Myoneadpater;
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


public class FindFragment extends Fragment {
	ImageView viewpager;
	private ListView listview;
	private List<Myone> arrlist;
	Myoneadpater hla;
	private  String [] str={"影视公司","影视项目","影视人才","后期制作","宣传发行","艺术院校","演绎联盟"};
	private int [] images={R.drawable.movescompany,R.drawable.movesproject,R.drawable.movespepple,R.drawable.lattermake,R.drawable.xuanchuan,R.drawable.artsc,R.drawable.biaoyan};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.find, null);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}
	private void initView() {

		listview = (ListView)getView().findViewById(R.id.listview);
		viewpager = (ImageView)getView().findViewById(R.id.viewpager);
		listview =	(ListView) getView().findViewById(R.id.listView);
		arrlist = new ArrayList<Myone>();
		for (int i = 0; i < str.length; i++) {
			Myone mh =	new Myone( images[i]);
			arrlist.add(mh);
		}
		viewpager.setImageResource(R.drawable.faxian_top);
		hla =new Myoneadpater(arrlist, getActivity());
		listview.setAdapter(hla);
		listview.setDivider(null);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch(arg2){
				case 0:
					Intent it1 = new Intent(getActivity(),Findfilmcompany.class);
					it1.putExtra("it", "影视公司");
					startActivity(it1);
					break;

				case 1:
					Intent it2 = new Intent(getActivity(),Findfilmproject.class);
					it2.putExtra("it", "影视项目");
					startActivity(it2);
					break;
				case 2:
					Intent it3 = new Intent(getActivity(),Findfilmpeople.class);
					it3.putExtra("it", "影视人才");
					startActivity(it3);
					break;

				case 3:
					Intent it4 = new Intent(getActivity(),Findproduction.class);
					it4.putExtra("it", "后期制作");
					startActivity(it4);
					break;
				case 4:
					Intent it5 = new Intent(getActivity(),Findissuepublicity.class);
					it5.putExtra("it", "宣传发行");
					startActivity(it5);
					break;
				case 5:
					Intent it6 = new Intent(getActivity(),ArtSchool.class);
					it6.putExtra("it", "艺术院校");
					startActivity(it6);
					break;

				case 6:
					Intent it7 = new Intent(getActivity(),FindTwoAlliance.class);
					it7.putExtra("it", "演绎联盟");
					startActivity(it7);
					break;
				}

			}
		});
	}
}
