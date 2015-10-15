package com.wenyu.kaijiw.fragment;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.wenyu.Data.Myone;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.kaijiw.AuxiliaryActivity;
import com.wenyu.kaijiw.Fiming;
import com.wenyu.kaijiw.LightActivity;
import com.wenyu.kaijiw.R;
import com.wenyu.kaijiw.SearchActivity;
import com.wenyu.kaijiw.ShootActivity;
import com.wenyu.kjw.adapter.MyHomeListAdapter;
import com.wenyu.kjw.adapter.Myoneadpater;
/**
 * 首页（开机网）包含 拍摄场地、拍摄器材、灯管器材、辅助器材
 * @author shasha
 *
 */
public class HomeFragment extends Fragment {
	private String url = ConstantClassField.SERVICE_URL + "service/citylist";
	private ListView listview;
	private ImageButton imagebtn1, btn1,btn2,btn3,btn4;
	private List<String> strs;
	private String sr,phone,password;
	private int customer_id,cflag;
	ArrayAdapter<String> adapter ;
	String [] ss={"拍摄场地","拍摄器材","灯光器材","辅助器材"};
	String [] y ={"全部","类型","综合排序"};
	private List<Myone>arrlist;
	private MyHomeListAdapter hla;
	int[] image={R.drawable.e1,R.drawable.e4,R.drawable.e3,R.drawable.e2};
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,strs);
				listview.setAdapter(adapter);
				break;
			case 2:
				Toast.makeText(getActivity(), "网络连接异常", 1000).show();
				break;
			}
		};
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (getArguments() != null) {
			customer_id = getArguments().getInt("customer_id");
			phone = getArguments().getString("phone");
			password = getArguments().getString("password");
			cflag = getArguments().getInt("cflag");
		}
		return inflater.inflate(R.layout.home_list1, null);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initValue();
	}
	//初始化控件
	private void initView() {
		listview = (ListView)getView(). findViewById(R.id.listView1);
		imagebtn1 = (ImageButton) getView().findViewById(R.id.searchbtn);
	}
	
	// 对控件的赋值/监听设置
	@SuppressWarnings("static-access")
	private void initValue(){
		arrlist = new ArrayList<Myone>();
		for (int i = 0; i < image.length; i++) {
			Myone mh =	new Myone( image[i]);
			arrlist.add(mh);
		}
		hla =new MyHomeListAdapter(arrlist, getActivity());
		hla.setListViewHeightBasedOnChildren(listview);
		listview.setAdapter(hla);
		listview.setDivider(null);
		listview.setOnItemClickListener(ocl);
		imagebtn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(getActivity(),SearchActivity.class);
				it.putExtra("customer_id",customer_id+"");
				startActivity(it);
			}
		});
	}
	public static HomeFragment newInstance(int id,String phone,String password,int cflag) {
		HomeFragment fragment = new HomeFragment();
		Bundle args = new Bundle();
		args.putInt("customer_id", id);
		args.putString("phone", phone);
		args.putString("password", password);
		args.putInt("cflag", cflag);
		fragment.setArguments(args);
		return fragment;
	}
	OnItemClickListener  ocl = new OnItemClickListener(){


		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			switch(arg2){
			case 0:
				Intent  it = new Intent(getActivity(),Fiming.class);
				it.putExtra("s", "拍摄场地");
				it.putExtra("customer_id",customer_id+"");
				startActivity(it);
				break;
			case 1:
				Intent it1 = new Intent(getActivity(),ShootActivity.class);
				it1.putExtra("s", "拍摄器材");
				it1.putExtra("customer_id",customer_id+"");
				startActivity(it1);
				break;
			case 2:
				Intent it2 = new Intent(getActivity(),LightActivity.class);
				it2.putExtra("s", "灯光器材");
				it2.putExtra("customer_id",customer_id+"");
				startActivity(it2);
				break;
			case 3:
				Intent it3 = new Intent(getActivity(),AuxiliaryActivity.class);
				it3.putExtra("s", "辅助器材");
				it3.putExtra("customer_id",customer_id+"");
				startActivity(it3);
				break;
			}

		}

	};

}
