package com.wenyu.kaijiw;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.Find_yingshi;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Pscd;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.choose.ExpandTabView;
import com.wenyu.choose.ViewLeft;
import com.wenyu.choose.ViewMiddle;
import com.wenyu.choose.ViewRight;
import com.wenyu.kjw.adapter.HomeYing;
/**
 *Ê×Ò³_¸¨ÖúÆ÷²Ä_µê¼Ò
 * @author Life.Shen
 *
 */

public class LightShopFragment extends Fragment {
	private String url = ConstantClassField.SERVICE_URL + "service/findequip";
	private String url1= ConstantClassField.SERVICE_URL + "service/stores";
	private  String[] area,typearr,orderarr,areaarr ;
	private String typetitle,areatitle,ordertitle;
	private ArrayList<String> area1;
	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewLeft viewLeft;
	private ViewMiddle viewMiddle;
	private ViewRight viewRight;
	private ListView listview;
	private List<Find_yingshi> fyslist;
	private HomeYing hla;
	private Map<String,String> paramsValue;
	private List<String>str,str2,str3;
	private ArrayList<String> str1;
	private  String ss,customer_id,category ;
	private Pscd pscd;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				Toast.makeText(getActivity(), "ÍøÂçÁ¬½ÓÊ§°Ü ", 1000).show();
				break;
			case 1:
				typearr=(String[])str.toArray(new String[str.size()]);
				orderarr=(String[])str2.toArray(new String[str2.size()]);
				area = (String[])area1.toArray(new String[area1.size()]);
				areaarr=(String[])str3.toArray(new String[str3.size()]);
				viewLeft = new ViewLeft(getActivity(),typearr);
				viewMiddle = new ViewMiddle(getActivity(),area,areaarr);
				viewRight = new ViewRight(getActivity(),orderarr);
				initVaule();
				initListener();
				break;
			case 3:
				hla =new HomeYing(fyslist, getActivity());
				listview.setAdapter(hla);
				break;
			
			case 8:
				break;
			}
		};
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.expandlist, null);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		movesPeople();
		

	}
	private void initView() {
		category = getArguments().getString("s");
		customer_id = getArguments().getString("customer_id");
		expandTabView = (ExpandTabView) getView().findViewById(R.id.expandtab_view);
		listview =(ListView) getView().findViewById(R.id.listView1);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent it1= new Intent(getActivity(),LightNearby.class);
				it1.putExtra("picture",fyslist.get(arg2).getPicture());
				it1.putExtra("id", fyslist.get(arg2).getId());
				it1.putExtra("customer_id", customer_id);
				startActivity(it1);

			}

		});
	}

	private void initVaule() {
		mViewArray.add(viewMiddle);
		mViewArray.add(viewLeft);
		mViewArray.add(viewRight);
		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("0");
		mTextArray.add("1");
		mTextArray.add("2");
		expandTabView.setValue(mTextArray, mViewArray);
		// 
		expandTabView.setTitle(viewMiddle.getShowText(area, 0), 0);
		expandTabView.setTitle(viewLeft.getShowText(0,typearr), 1);
		expandTabView.setTitle(viewRight.getShowText(0,orderarr), 2);

	}

	private void movesPeople() {

		new Thread(new Runnable(){

			@Override
			public void run() {

				try {
					paramsValue=new HashMap<String, String>(); 
					paramsValue.put("city","");
					paramsValue.put("category","¸¨ÖúÆ÷²Ä");
					area1 = new ArrayList<String>();
					if(NetWorkUtil.isNetAvailable(getActivity())){
						ss = new HttpP().sendPOSTRequestHttpClient(url.trim(),paramsValue);
//						System.out.println("*((**(*((**(*(*(98"+url+paramsValue);
						if(("null").equals(ss)&&("").equals(ss)){
							handler.sendEmptyMessage(8);
						}else {
							pscd = fa(ss);
							handler.sendEmptyMessage(1);
						}
					}else {
						handler.sendEmptyMessage(0);
					}

				} catch (Exception e) {

					e.printStackTrace();
				}

			}
		}).start();

	}
	private List<Find_yingshi> initying(String jss) {
		List<Find_yingshi>	fyslist = new ArrayList<Find_yingshi>();
		try {
			JSONArray jaa = new JSONArray(jss);
			for (int i = 0; i < jaa.length(); i++) {
				JSONObject jo = jaa.optJSONObject(i);
				String id = jo.optString("id");
				String name = jo.optString("name");
				String pic = jo.optString("image");
				String countV = jo.optString("countV");
				Find_yingshi fys = new Find_yingshi(pic,name,id,countV);
				fyslist.add(fys);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return fyslist;

	}
	public  Pscd  fa(String jsonData){
		str =  new ArrayList<String>();
		str1 =  new ArrayList<String>();
		str2 = new ArrayList<String>();
		str3= new ArrayList<String>();
		pscd =null;
		JSONObject jo;
		try {
			jo = new JSONObject(jsonData);
			JSONArray types = jo.optJSONArray("type");
			for (int i = 0; i < types.length(); i++) {
				str.add(types.optString(i));
			}
			JSONArray regionals = jo.optJSONArray("regional");
			for (int i = 0; i < regionals.length(); i++) {
				JSONObject reobj = regionals.optJSONObject(i);
				String city = reobj.optString("city");
				area1.add(city);
				JSONArray  jsarr = reobj.optJSONArray("array");
				for (int j = 0; j < jsarr.length(); j++) {
					String era = jsarr.optString(j);
					str3.add(era);
				}
			}
			JSONArray orderby = jo.optJSONArray("orderby");
			for (int i = 0; i < orderby.length(); i++) {
				str2.add(orderby.optString(i));
			}
			pscd = new Pscd(str, str1, str2);


		} catch (JSONException e) {
			e.printStackTrace();
		}
		initView();
		return pscd ;

	}  
	private void initListener() {
		typetitle =typearr[0];
		areatitle = area[0];
//		area2title = areaarr[0];
		ordertitle =orderarr[0];
		viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				typetitle = showText;
				onRefresh(viewLeft, showText);
				
			}
		});

		viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {

			@Override
			public void getValue(String showText) {
				areatitle = showText;
				onRefresh(viewMiddle, showText);

			}

			@Override
			public void getValue(String distance, String showText) {
				// TODO Auto-generated method stub
				
			}
		});

		viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				ordertitle = showText;
				onRefresh(viewRight, showText);
			}
		});

	}

	/**
	 * 
	 * @param view
	 * @param showText
	 */
	private void onRefresh(View view, String showText) {

		expandTabView.onPressBack();
		int position = getPositon(view);

		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}
		new Thread(new Runnable(){
		@Override
		public void run() {

			try {
				paramsValue=new HashMap<String, String>();  
				if(("ÀàÐÍ").equals(typetitle)||("È«²¿").equals(typetitle)){
					typetitle= "";
				}
//				if(("").equals(areatitle)||("").equals(areatitle)){
//					areatitle= "";
//				} 
				if(("×ÛºÏÅÅÐò").equals(ordertitle)){
					ordertitle= "";
				}
				
				paramsValue.put("city","±±¾©ÊÐ");
				paramsValue.put("type",typetitle);
				paramsValue.put("regional",areatitle);
				paramsValue.put("orderby", ordertitle);
				paramsValue.put("category","¸¨ÖúÆ÷²Ä");	
				if(NetWorkUtil.isNetAvailable(getActivity())){
					ss = new HttpP().sendPOSTRequestHttpClient(url1.trim(),paramsValue);
					if(("").equals(ss)){
						handler.sendEmptyMessage(8);
					}else{
						fyslist = initying(ss);
						handler.sendEmptyMessage(3);
					}
				}
				else {
					handler.sendEmptyMessage(0);
				}
			} catch (Exception e) {

				e.printStackTrace();
			}

		}


	}).start();
	}

	/**
	 * 
	 * @param tView
	 * @return
	 */
	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {

			if (mViewArray.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}

}
