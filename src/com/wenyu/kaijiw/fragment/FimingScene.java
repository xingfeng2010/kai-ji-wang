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

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.baoyz.swipemenulistview.BaoyzApplication;
import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.CityEra;
import com.wenyu.Data.EraData;
import com.wenyu.Data.Find_yingshi;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Pscd;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.choose.ExpandTabView;
import com.wenyu.choose.ViewLeft;
import com.wenyu.choose.ViewMiddle;
import com.wenyu.choose.ViewRight;
import com.wenyu.kaijiw.Nearby;
import com.wenyu.kaijiw.R;
import com.wenyu.kjw.adapter.HomeYing;

/**
 * 拍摄场地——实景
 * @author Life.Shen
 *
 */
public class FimingScene extends Fragment implements  IXListViewListener {
	private  String[] area,typearr,orderarr,areaarr ;// 显示字段
	private String typetitle,areatitle,ordertitle,ss,category,customer_id;
	private ArrayList<String> area1;
	private String url = ConstantClassField.SERVICE_URL + "service/regional";
	private String url1= ConstantClassField.SERVICE_URL + "service/real";
	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();// view数组
	private ViewLeft viewLeft;
	private ViewMiddle viewMiddle;
	private ViewRight viewRight;
	private XListView listview;
	private List<Find_yingshi> fyslist;
	private HomeYing hla;
	private Map<String,String> paramsValue;
	private List<String>str,str2,str3,str9,str10;
	private List<EraData> strrea;
	List<CityEra> strrea1;
	private ArrayList<String> str1;
	private Pscd pscd;
	private Map<List<String>,ArrayList<String>> mas ;
	String [] area2 ={"北京市","上海市"};
	private int page=1;

	Handler handler = new Handler(){
		public  void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				Toast.makeText(getActivity(), "网络连接异常 ", 1000).show();
				break;
			case 1://请求头部数据
				typearr=(String[])str.toArray(new String[str.size()]);
				orderarr=(String[])str2.toArray(new String[str2.size()]);
				viewLeft = new ViewLeft(getActivity(),typearr);
				area = area1.toArray(new String[area1.size()]);
				viewMiddle = new ViewMiddle(getActivity(),
						mas);
				viewRight = new ViewRight(getActivity(),orderarr);
				initVaule();
				initListener();
				onRefresh(viewMiddle, "");
				break;
			case 3://筛选后列表
				if(page > 1){
					hla.setData(fyslist);
					hla.notifyDataSetChanged();
				}else{
					hla =new HomeYing(fyslist, getActivity());
					listview.setAdapter(hla);
				}
				break;
			case 5:
				Toast.makeText(getActivity(), "返回上级重试 ", 1000).show();
				break;
			case 8:
				Toast.makeText(getActivity(), "没有更多数据", 1000).show();
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
		expandTabView.onPressBack();

	}
	/**
	 * 初始化控件并设置监听
	 */
	private void initView() {
		category = getArguments().getString("s");
		customer_id = getArguments().getString("customer_id");
		expandTabView = (ExpandTabView) getView().findViewById(R.id.expandtab_view);
		listview = (XListView)getView().findViewById(R.id.xListView);
		listview.setPullLoadEnable(true);
		listview.setXListViewListener(FimingScene.this);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				//				判读是否登陆
				if(BaoyzApplication.getInstance().isLogined){	
					if(arg2<=fyslist.size()){
						Intent it1= new Intent(getActivity(),Nearby.class);
						it1.putExtra("picture",fyslist.get(arg2-1).getPicture());
						it1.putExtra("id", fyslist.get(arg2-1).getId());
						it1.putExtra("category", category);
						it1.putExtra("customer_id", customer_id);
						startActivity(it1);
					}
				}else{
					new AlertDialog.Builder(getActivity())
					.setMessage("请登录")
					.setNegativeButton("确定",null).show().setCanceledOnTouchOutside(false);
				}
			}
		});
	}
	// 初始化三个视图添加到expandTabView
	private void initVaule() {
		mViewArray.add(viewMiddle);
		mViewArray.add(viewLeft);
		mViewArray.add(viewRight);
		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("0");
		mTextArray.add("1");
		mTextArray.add("2");
		expandTabView.setValue(mTextArray, mViewArray);
		// 设置顶部默认显示的条目,从Itent获取显示的位置

		expandTabView.setTitle(viewMiddle.getShowText(area2, 0), 0);
		expandTabView.setTitle(viewLeft.getShowText(0,typearr), 1);
		expandTabView.setTitle(viewRight.getShowText(0,orderarr), 2);

	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if(expandTabView != null){
			expandTabView.onPressBack();
		}
	};
	/**
	 * 筛选后接口调用
	 */
	public void initThreadSecond(){
		new Thread(new Runnable(){
			@Override
			public void run() {

				try {
					paramsValue=new HashMap<String, String>();  
					if(("全部").equals(typetitle)||("类型").equals(typetitle)){
						typetitle= "";
					} if(("区域").equals(areatitle)||("全部").equals(areatitle)){
						areatitle= "";
					} if(("综合排序").equals(ordertitle)){
						ordertitle= "";
					}

					paramsValue.put("city","");
					paramsValue.put("type",typetitle);
					paramsValue.put("regional",areatitle);
					paramsValue.put("orderby", ordertitle);
					paramsValue.put("page", page+"");	
					if(NetWorkUtil.isNetAvailable(getActivity())){
						String ss = new HttpP().sendPOSTRequestHttpClient(url1.trim(),paramsValue);
						if(("").equals(ss)|| "null".equals(ss)||"notfind".equals(ss)){
							handler.sendEmptyMessage(8);
						}else{
							List<Find_yingshi> newFyslist = initying(ss);
							if(page == 1){//下拉刷新
								fyslist = newFyslist;
							}else{//加载更多
								if(fyslist != null){
									fyslist.addAll(fyslist.size(), newFyslist);
								}else{
									fyslist = newFyslist;
								}
							}
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
	//筛选前数据
	private void movesPeople() {

		new Thread(new Runnable(){

			@Override
			public void run() {

				try {
					paramsValue=new HashMap<String, String>(); 
					paramsValue.put("city","");
					area1 = new ArrayList<String>();
					if(NetWorkUtil.isNetAvailable(getActivity())){
						ss = new HttpP().sendPOSTRequestHttpClient(url.trim(),paramsValue);
						if(("null").equals(ss)&&("notfind").equals(ss)){
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

	//筛选时view的监听 
	private void initListener() {
		typetitle ="";
		areatitle ="";
		ordertitle="";
		/**
		 * 中间view的监听
		 */
		viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				typetitle = showText;
				onRefresh(viewLeft, showText);

			}
		});
		/**
		 * 左侧view的监听
		 */
		viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {

			@Override
			public void getValue(String showText) {
				if(TextUtils.equals(showText, "北京市")){
					showText = "";
				}
				areatitle = showText;
				onRefresh(viewMiddle, showText);

			}

			@Override
			public void getValue(String distance, String showText) {
				// TODO Auto-generated method stub

			}
		});
		/**
		 * 右侧view的监听
		 */
		viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				ordertitle = showText;
				onRefresh(viewRight, showText);
			}
		});

	}

	/**
	 * 更新条目
	 * 
	 * @param 获取当前view的  showText值进行数据的请求
	 * @param showText
	 */
	private void onRefresh(View view, String showText) {

		expandTabView.onPressBack();
		int position = getPositon(view);// 当前第几个列表

		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			if(TextUtils.isEmpty(showText)){
				expandTabView.setTitle("区域", position);
			}else{
				expandTabView.setTitle(showText, position);
			}

		}
		initThreadSecond();
	}

	/**
	 * 所属列表
	 * 
	 * @param 获取当前view 的下标
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

	@Override
	public void onDetach() {
		super.onDetach();
	}
	private void geneItems() {
		page++;
		initThreadSecond();

	}
	// 上拉刷新回调
	@Override
	public void onRefresh() {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				page=1;
				initThreadSecond();
				onLoad();
			}
		}, 100);

	}
	//	 下拉加载回调
	@Override
	public void onLoadMore() {

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				hla.notifyDataSetChanged();
				onLoad();
			}
		}, 100);
	}
	/**
	 * 停止刷新、加载
	 */
	private void onLoad() {
		listview.stopRefresh();
		listview.stopLoadMore();
		listview.setRefreshTime("刚刚");
	}
	//解析 筛选后 数据
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
	//解析筛选前数据
	public  Pscd  fa(String jsonData){
		str =  new ArrayList<String>();
		str1 =  new ArrayList<String>();
		str2 = new ArrayList<String>();
		mas = new HashMap<List<String>, ArrayList<String>>();
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
				List<String> area1 = new ArrayList<String>();
				ArrayList<String> str3= new ArrayList<String>();
				JSONObject reobj = regionals.optJSONObject(i);
				String city = reobj.optString("city");
				JSONArray  jsarr = reobj.optJSONArray("array");
				for (int j = 0; j < jsarr.length(); j++) {
					String era = jsarr.optString(j);
					str3.add(era);
				}
				area1.add(city);
				mas.put(area1, str3);
			}
			JSONArray orderby = jo.optJSONArray("orderby");
			for (int i = 0; i < orderby.length(); i++) {
				str2.add(orderby.optString(i));
			}
			pscd = new Pscd(str, str1, str2);


		} catch (JSONException e) {
			e.printStackTrace();
		}

		return pscd ;

	}  
}
