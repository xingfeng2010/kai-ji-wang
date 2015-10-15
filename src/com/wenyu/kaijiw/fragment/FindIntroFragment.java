package com.wenyu.kaijiw.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
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
import com.wenyu.Data.ReFindIntroData;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.kaijiw.R;
import com.wenyu.kjw.adapter.ReFindIntroAdpater;

@SuppressLint("HandlerLeak")
public class FindIntroFragment extends Fragment {
	private String intentid,ss;
	private String url = ConstantClassField.SERVICE_URL+"service/talent/detail"; 
	private Map<String,String> paramsValue;
	private List<String> namelist,valuelist;
	private List<ReFindIntroData> rfxlists;
	private ListView listview;
	private  int customer_id;
	Handler  handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				try {
					ReFindIntroAdpater rfa = new ReFindIntroAdpater(getActivity(),rfxlists);
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
		return inflater.inflate(R.layout.findfour_info, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		movesPeople();
	}
	private void initView() {
		
		intentid = getArguments().getString("id");
		listview = (ListView) getActivity().findViewById(R.id.listView);
		listview.setFocusable(false);
		customer_id=BaoyzApplication.getInstance().customer_id;
		
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
						
						rfxlists = getLists(ss);
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
	public List<ReFindIntroData>  getLists(String  str){

		List<ReFindIntroData> rfxlist = new ArrayList<ReFindIntroData>();
		namelist = new ArrayList<String>();
		valuelist = new ArrayList<String>();
		try {
			JSONObject  jobj = new JSONObject(str);
			JSONArray arrfields = jobj.optJSONArray("fields");
			for (int i = 0; i < arrfields.length(); i++) {
				JSONObject  objfields = arrfields.optJSONObject(i);
				JSONArray introarr = objfields.optJSONArray("array");
				for (int j = 0; j < introarr.length(); j++) {
					JSONObject  job = introarr.optJSONObject(j);
					String name = job.optString("name");
					String value = job.optString("value");
					namelist.add(name);
					valuelist.add(value);
					ReFindIntroData rfid = new ReFindIntroData(namelist, valuelist);
					rfxlist.add(rfid);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return rfxlist;
	}
	
	
}
