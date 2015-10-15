package com.wenyu.kaijiw.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.HomeSurround;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.SurroundData;
import com.wenyu.kaijiw.Home_SurroundActivity;
import com.wenyu.kaijiw.MyMapActivity;
import com.wenyu.kaijiw.R;
import com.wenyu.kjw.adapter.HomeSurroundAdapter;
import com.wenyu.kjw.adapter.SurroundDataAdapter;


public class Home_SurroundFragment extends Fragment{
	private String TAG = Home_IntroFragment.class.getSimpleName();
	private String url = "http://api.map.baidu.com/place/v2/search";
	private Map<String,String>params;
	private List<HomeSurround> hss ;
	private  String ss,x,y,fname;
	private ListView listview;
	private String[]arr = {"餐厅","超市","加油站","旅店","药店","医院","银行"};
	private int[] images = {R.drawable.rest,R.drawable.surmaket,R.drawable.jiayou,R.drawable.hotel,R.drawable.yaodian,R.drawable.yiyuan,R.drawable.yinhang};
	View view;
	public View onCreateView(LayoutInflater inflater, android.view.ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.shopintro_list, null);
		return view;

	};



	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();

	}
	private void initView() {
		fname = getArguments().getString("fname");
		x = getArguments().getString("x");
		y = getArguments().getString("y");
		listview = (ListView) view.findViewById(R.id.listView1);
		listview.setFocusable(false);
		List<SurroundData> sds = new ArrayList<SurroundData>(); 
		
		for (int i = 0; i < images.length; i++) {
			SurroundData sd = new SurroundData(images[i],arr[i]);
			sds.add(sd);
		}
		SurroundDataAdapter sda = new SurroundDataAdapter(sds, getActivity());
		listview.setAdapter(sda);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent  myma = new Intent(getActivity(),Home_SurroundActivity.class);
				myma.putExtra("fname", fname);
				myma.putExtra("x", x);
				myma.putExtra("y", y);
				myma.putExtra("name", arr[arg2]);
				myma.putExtra("pic", arg2+"");
				startActivity(myma);
			}
		});
	}

}
