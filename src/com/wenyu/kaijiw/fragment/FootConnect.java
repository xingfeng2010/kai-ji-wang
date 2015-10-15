package com.wenyu.kaijiw.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.baoyz.swipemenulistview.BaoyzApplication;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.Find_yingshi;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Myhome;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.choose.ExpandTabView;
import com.wenyu.choose.ViewLeft;
import com.wenyu.choose.ViewRight;
import com.wenyu.kaijiw.EquipsNearby;
import com.wenyu.kaijiw.LightNearby;
import com.wenyu.kaijiw.Nearby;
import com.wenyu.kaijiw.R;
import com.wenyu.kjw.adapter.Home_List_Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
@SuppressLint("ShowToast")
public class FootConnect extends Fragment implements  IXListViewListener {
	private RadioButton scan,collect;
	private Spinner sp1,sp2;
	private ArrayAdapter<String> adapter;
	private XListView listview;
	private Find_yingshi fys;
	private  ArrayAdapter<String> a1,a2;
	private ArrayAdapter<String> a,b;
	private int cflag,customer_id;
	private Map<String,String> paramsValue,cancelValue;
	private String footJson,test;
	private String foottitle = ConstantClassField.SERVICE_URL+"/service/footprint";
	private String connecturl =ConstantClassField.SERVICE_URL+"service/myEnshrine";
	private String footscanCancel=ConstantClassField.SERVICE_URL+"service/removeFootmark";
	private List<Myhome> lists1=new ArrayList<Myhome>();
	private Home_List_Adapter hla;
	List<String> types,orders;
	private String phone,password,titlescn;
	private int page =1;
	private ExpandTabView expandTabView;
	private String[] typearr;
	private String[] orderarr;
	private ViewLeft viewLeft;
	private ViewRight viewRight;
	private String typetitle,areatitle,ordertitle;
	private ArrayList<View> mViewArray = new ArrayList<View>();// view数组
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				Toast.makeText(getActivity(), "网络连接异常 ", 1000).show();
				break;
			case 1:
				Toast.makeText(getActivity(), "删除成功" , 1000).show();  
				break;
			case 3:
				if(page>1){
					hla.setData(lists1);
					hla.notifyDataSetChanged();
				}else{
					hla =new Home_List_Adapter(lists1, getActivity());
					listview.setAdapter(hla);
				}
				break;
			case 4:
				typearr=(String[])types.toArray(new String[types.size()]);
				orderarr=(String[])orders.toArray(new String[orders.size()]);
				viewLeft = new ViewLeft(getActivity(),typearr);
				viewRight = new ViewRight(getActivity(),orderarr);
				initVaule();
				initListener();
				onRefresh(viewLeft, "");
				break;
			case 5:
				Toast.makeText(getActivity(), "没有更多的数据", 1000).show();
				break;
			case 6:
				if(hla != null){
					hla.notifyDataSetChanged();
				}
				Toast.makeText(getActivity(), "没有数据", 1000).show();
				break;
			
			}
		};
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.foot_title, null);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initTitleThread();

		listview.setOnItemClickListener(itemlistener);
		
	}
	private void initTitleThread() {
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					paramsValue=new HashMap<String, String>(); 
					paramsValue.put("customer_id",customer_id+"");
					paramsValue.put("page","1");

					if(NetWorkUtil.isNetAvailable(getActivity())){				
						titlescn = new HttpP().sendPOSTRequestHttpClient
								(foottitle,paramsValue);
						getTitleJson(titlescn);
						handler.sendEmptyMessage(4);		
					}else {
						handler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	private void  getTitleJson(String str){
		types = new ArrayList<String>();
		orders = new ArrayList<String>();
		try {
			JSONObject jo = new JSONObject(str);
			JSONArray type =jo.optJSONArray("type");
			JSONArray orderby = jo.optJSONArray("orderby");
			for (int i = 0; i < type.length(); i++) {
				String typeentity = type.optString(i);
				types.add(typeentity);
			}
			for (int i = 0; i < orderby.length(); i++) {
				String orderbyentity = orderby.optString(i);
				orders.add(orderbyentity);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void initView() {
		customer_id =  BaoyzApplication.getInstance().customer_id;
		expandTabView = (ExpandTabView) getView().findViewById(R.id.expandtab_view);
		listview = (XListView)getView().findViewById(R.id.xListView);
		listview.setPullLoadEnable(true);
		listview.setXListViewListener(FootConnect.this);
	}
	
	private void initVaule() {
		mViewArray.add(viewLeft);
		mViewArray.add(viewRight);
		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("0");
		mTextArray.add("1");
		expandTabView.setValue(mTextArray, mViewArray);
		// 设置顶部默认显示的条目,从Itent获取显示的位置

		expandTabView.setTitle(viewLeft.getShowText(0,typearr), 0);
		expandTabView.setTitle(viewRight.getShowText(0,orderarr), 1);

	}
	private void initListener() {
		typetitle ="";
		ordertitle="";
		viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				typetitle = showText;
				onRefresh(viewLeft, showText);

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
	private void onRefresh(View view, String showText) {

		expandTabView.onPressBack();
		int position = getPositon(view);// 当前第几个列表

		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			if(TextUtils.isEmpty(showText)){
				expandTabView.setTitle("全部 ", position);
			}else{
				expandTabView.setTitle(showText, position);
			}

		}
		if(lists1 != null){
			lists1.clear();	
		}
		Connect();
	}
	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {

			if (mViewArray.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}
	

	private void Connect() {
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					paramsValue=new HashMap<String, String>(); 
					if(BaoyzApplication.getInstance().isLogined){
						customer_id=BaoyzApplication.getInstance().customer_id;
					}else{
						customer_id=-1;
					}
					paramsValue.put("customer_id",customer_id+"");
					paramsValue.put("page",page+"");
					paramsValue.put("category",typetitle);
					paramsValue.put("browseDate",ordertitle);
					if(NetWorkUtil.isNetAvailable(getActivity())){				
						footJson = new HttpP().sendPOSTRequestHttpClient(connecturl,paramsValue);	
						if(("notfind").equals(footJson)||("[]").equals(footJson)){
							handler.sendEmptyMessage(6);	
						}else{
							List<Myhome> newlists1 =parse(footJson);
							if(page==1){
								lists1 =newlists1;
							}else{
								if(newlists1!=null){
									lists1.addAll(newlists1);
								}else{
									lists1 =newlists1;		
								}
							}
							handler.sendEmptyMessage(3);	
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
	private List<Myhome> parse(String jss) {
		List<Myhome> lists1 = new ArrayList<Myhome>();
		try {
			JSONArray jaa = new JSONArray(jss);
			for (int i = 0; i < jaa.length(); i++) {
				JSONObject jo = jaa.optJSONObject(i);
				String name = jo.optString("name");
				String pic = jo.optString("imagePatch");
				String introduction = jo.optString("introduction");
				String id = jo.optString("id");
				String type = jo.optString("type");
				String record = jo.optString("record_id");
				Myhome homes = 	new Myhome(name,pic,type,id,introduction,record);
				lists1.add(homes);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return lists1;

	}


	OnItemClickListener itemlistener = new OnItemClickListener() {

		@Override  
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)  
		{  

//			if(position<lists1.size()){
//				Myhome mh = lists1.get(position);
//				Intent it1 = targetActivity(mh);
//				startActivity(it1);
//			}


		};
//		private Intent targetActivity(Myhome mh) {
//			Intent it1;
//			if("店家".equals(mh.getType())){
//				it1 = new Intent(getActivity(),LightNearby.class); 
//				it1.putExtra("pic", mh.getImag());
//				it1.putExtra("id", mh.getId());//id有bug,
//				System.out.println(mh.getId());
//				it1.putExtra("name", mh.getIntroduction().split(",")[0]);
//				it1.putExtra("category",  "拍摄器材");
//				return it1;
//			}else if("场地".equals(mh.getType())){
//				it1= new Intent(getActivity(),Nearby.class);
//				it1.putExtra("picture",mh.getImag());
//				it1.putExtra("id", mh.getId());
//				return it1;
//			}else if("器材".equals(mh.getType())){
//				it1= new Intent(getActivity(),EquipsNearby.class);
//				it1.putExtra("pic",mh.getImag());
//				it1.putExtra("id", mh.getId());
//				it1.putExtra("name", mh.getIntroduction().split(",")[0]);
//				it1.putExtra("category",  "灯光器材");
//				it1.putExtra("customer_id",customer_id+"");
//				return it1;
//
//			}
//			return null;
//		}  

	};
	private void geneItems() {
		page++;
		Connect();
	}
	@Override
	public void onRefresh() {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				page=1;
				Connect();
			}
		}, 500);
	
	}
	@Override
	public void onLoadMore() {

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				hla.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}
	private void onLoad() {
		listview.stopRefresh();
		listview.stopLoadMore();
		listview.setRefreshTime("刚刚");
	}
}
