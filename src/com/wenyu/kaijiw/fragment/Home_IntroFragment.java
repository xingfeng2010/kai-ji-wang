package com.wenyu.kaijiw.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.BaoyzApplication;
import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.IntroData;
import com.wenyu.Data.IntronameData;
import com.wenyu.Data.IntrovalueData;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.kaijiw.R;
import com.wenyu.kjw.adapter.NearbyAdapter;
import com.wenyu.kjw.adapter.ShopIntroduceAdapter;


public class Home_IntroFragment extends Fragment{
	private IntroData id;
	private String url = ConstantClassField.SERVICE_URL + "service/place";
	private Map<String,String>params;
	List<IntroData> li;
	private  String ss,intentid;
	private ListView listview;
	View view;
	private int customer_id;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				Toast.makeText(getActivity(), "«Î¡¨Ω”Õ¯¬Á", 1000).show();
				break;
			case 1:
				NearbyAdapter sa = new NearbyAdapter(li, getActivity());
				listview.setAdapter(sa);
				break;
			}
		}
	};
	public View onCreateView(LayoutInflater inflater, android.view.ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.shopintro_list, null);
		return view;

	};



	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		initView();
		initThread();

	}
	private void initView() {
		intentid = getArguments().getString("id");
		li = new ArrayList<IntroData>();
		listview = (ListView) view.findViewById(R.id.listView1);
		listview.setFocusable(false);
		customer_id=BaoyzApplication.getInstance().customer_id;
	}



	private void initThread() {
		params = new HashMap<String,String>();
		params.put("id", intentid);
		params.put("userid", customer_id+"2");
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					if(NetWorkUtil.isNetAvailable(getActivity())){
						ss = new HttpP().sendPOSTRequestHttpClient(url, params);
						li =getJSon(ss);
						handler.sendEmptyMessage(1);
					}else {
						handler.sendEmptyMessage(0);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}).start();

	}

	public List<IntroData> getJSon(String str){
		li = new ArrayList<IntroData>();
		List<IntronameData> inname = new ArrayList<IntronameData>();
		List<IntrovalueData> invalue = new ArrayList<IntrovalueData>();
		id = null;
		JSONObject jo;
		try {
			jo = new JSONObject(str);

			JSONArray   ja = jo.optJSONArray("fields");
			JSONObject  obj= ja.optJSONObject(0);
			JSONArray jarr = obj.optJSONArray("array");
			for (int j = 0; j < jarr.length(); j++) {
				JSONObject  objj = jarr.optJSONObject(j);
				String name =objj.optString("name");
				String value = objj.optString("value");
				IntronameData intrname= new IntronameData(name);
				IntrovalueData intrvalue= new IntrovalueData(value);
				inname.add(intrname);
				invalue.add(intrvalue);

				id= new IntroData(inname, invalue);
				li.add(id);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return li;

	}
}
