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
import android.view.ViewGroup;
import android.widget.ListView;
import com.baoyz.swipemenulistview.BaoyzApplication;
import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.ReFindVistData;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.kaijiw.R;
import com.wenyu.kjw.adapter.ReFindVistAdpater;

public class FindVistFragment extends Fragment {
	private String intentid,ss;
	private String url = ConstantClassField.SERVICE_URL+"service/talent/detail"; 
	private Map<String,String> paramsValue;
	private List<String> namelist,yearlist,positionlist,filmtype;
	private List<ReFindVistData> rfxlist;
	private ListView listview;
	private  int customer_id;
	Handler  handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				try {
					ReFindVistAdpater rfa = new ReFindVistAdpater(getActivity(),rfxlist);
					listview.setAdapter(rfa);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		};
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.findfour_visit, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		movesPeople();
	}
	private void initView() {
		customer_id= BaoyzApplication.getInstance().customer_id;
		intentid = getArguments().getString("id");
		listview = (ListView) getActivity().findViewById(R.id.listView);
		listview.setFocusable(false);
	}

	private void movesPeople() {
		new Thread(new Runnable(){

			@Override
			public void run() {

				try {
					paramsValue=new HashMap<String, String>();  
					paramsValue.put("id", intentid);
					paramsValue.put("userid", customer_id+"");
					paramsValue.put("category", "影视人才");
					if(NetWorkUtil.isNetAvailable(getActivity())){
						ss = new HttpP().sendPOSTRequestHttpClient(url,paramsValue);
						rfxlist = getList(ss);
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
	public List<ReFindVistData>  getList(String  str){

		List<ReFindVistData> rfxlist = new ArrayList<ReFindVistData>();
		yearlist = new ArrayList<String>();
		namelist = new ArrayList<String>();
		positionlist = new ArrayList<String>();
		filmtype = new ArrayList<String>();
		try {
			JSONObject  jobj = new JSONObject(str);
			JSONArray arrfields = jobj.optJSONArray("film");
			for (int i = 0; i < arrfields.length(); i++) {
				JSONObject  objfields = arrfields.optJSONObject(i);
					String year = objfields.optString("year");
					String name = objfields.optString("name");
					String filmType = objfields.optString("filmType");
					String position = objfields.optString("position");
					yearlist.add(year);
					namelist.add(name);
					positionlist.add(position);
					filmtype.add(filmType);
					ReFindVistData rfid = new ReFindVistData(yearlist, namelist,positionlist,filmtype);
					rfxlist.add(rfid);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return rfxlist;
	}
	
	
}
