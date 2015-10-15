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
import android.content.DialogInterface;
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
import com.wenyu.Data.Find_yingshi;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Pscd;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.choose.ExpandTabView;
import com.wenyu.choose.ViewLeft;
import com.wenyu.choose.ViewMiddle;
import com.wenyu.choose.ViewRight;
import com.wenyu.kaijiw.FindThree;
import com.wenyu.kaijiw.LoginActivity;
import com.wenyu.kaijiw.Nearby;
import com.wenyu.kaijiw.R;
import com.wenyu.kjw.adapter.HomeYing;

/**
 * 首页_拍摄场地_影棚
 * @author shasha
 *
 */
public class FimingStudio extends Fragment implements IXListViewListener {
	private String url = ConstantClassField.SERVICE_URL + "service/regional";
	private String url1= ConstantClassField.SERVICE_URL + "service/studio";
	private  String[] area,typearr,orderarr,areaarr ;
	private String typetitle,areatitle,ordertitle;
	private ArrayList<String> area1;
	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewLeft viewLeft;
	private ViewMiddle viewMiddle;
	private ViewRight viewRight;
	private XListView listview;
	private List<Find_yingshi> fyslist;
	private HomeYing hla;
	private Map<String,String> paramsValue;
	private List<String>str,str2,str3;
	private ArrayList<String> str1;
	private  String ss,customer_id,cate ;
	private Pscd pscd;
	private int page=1;
	private Map<List<String>,ArrayList<String>> mas ;
	String [] area2 ={"北京市","上海市"};
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				Toast.makeText(getActivity(), "网络连接失败 ", 1000).show();
				break;
			case 1:
				typearr=(String[])str.toArray(new String[str.size()]);
				orderarr=(String[])str2.toArray(new String[str2.size()]);
				area = (String[])area1.toArray(new String[area1.size()]);
				areaarr=(String[])str3.toArray(new String[str3.size()]);

				viewLeft = new ViewLeft(getActivity(),typearr);
				viewMiddle = new ViewMiddle(getActivity(),
						mas);
				viewRight = new ViewRight(getActivity(),orderarr);
				initVaule();
				initListener();
				onRefresh(viewRight, "");
				break;
			case 3:
				if(page > 1){
					hla.setData(fyslist);
					hla.notifyDataSetChanged();
				}else{
					hla =new HomeYing(fyslist, getActivity());
					listview.setAdapter(hla);
				}
				break;
			case 8:
				Toast.makeText(getActivity(), "没有更多数据", 1000).show();
				break;
			}
		};
	};

	public void onHiddenChanged(boolean hidden) {
		if(expandTabView != null){
			expandTabView.onPressBack();
		}
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
	/**
	 * 初始化控件并设置监听
	 */
	private void initView() {
		cate = getArguments().getString("s");
		customer_id = getArguments().getString("customer_id");
		expandTabView = (ExpandTabView) getView().findViewById(R.id.expandtab_view);
		listview = (XListView)getView().findViewById(R.id.xListView);
		listview.setPullLoadEnable(true);
		listview.setXListViewListener(FimingStudio.this);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				//				判断是否登陆

				if(BaoyzApplication.getInstance().isLogined){
					if(arg2<=fyslist.size()){
						Intent it1= new Intent(getActivity(),Nearby.class);
						it1.putExtra("picture",fyslist.get(arg2-1).getPicture());
						it1.putExtra("id", fyslist.get(arg2-1).getId());
						it1.putExtra("customer_id", customer_id);
						startActivity(it1);
					}
				}else{
					new AlertDialog.Builder(getActivity())
					.setMessage("您还没有登录，登录后可以查看资料")
					.setNegativeButton("去登录",new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							Intent intent = new Intent(getActivity(),LoginActivity.class);
							startActivity(intent);
						}
					})
					.setPositiveButton("再看看", null)
					.show().setCanceledOnTouchOutside(false);
				}


			}

		});
	}
	/**
	 * 筛选前view 的初始化
	 */
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
		expandTabView.setTitle(viewMiddle.getShowText(area2, 0), 0);
		expandTabView.setTitle(viewLeft.getShowText(0,typearr), 1);
		expandTabView.setTitle(viewRight.getShowText(0,orderarr), 2);

	}
	/**
	 * 筛选前接口调用
	 */
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
						if(("null").equals(ss)||("").equals(ss)){
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
	
	/**
	 * 筛选时 view 的监听
	 */
	private void initListener() {
		typetitle ="";
		areatitle = "";
		ordertitle ="";
		//		中间view的监听
		viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				typetitle = showText;
				onRefresh(viewLeft, showText);

			}
		});
		//左侧view 的监听
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
		//右侧view的监听
		viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {
			/**
			 * 获取被选中的值，请求数据
			 */

			@Override
			public void getValue(String distance, String showText) {
				ordertitle = showText;
				onRefresh(viewRight, showText);
			}
		});

	}

	/**
	 * 
	 * @param view 筛选时 当前view的数据请求
	 * @param showText
	 */
	private void onRefresh(View view, String showText) {

		expandTabView.onPressBack();
		int position = getPositon(view);// 当前第几个列表

		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {


			if(TextUtils.isEmpty(showText)){
				expandTabView.setTitle("综合排序", position);
			}else{
				expandTabView.setTitle(showText, position);
			}

		}
		initThreadSecond();
	}
	/**
	 * 筛选后数据接口调用
	 */
	public void initThreadSecond(){
		new Thread(new Runnable(){
			@Override
			public void run() {

				try {
					paramsValue=new HashMap<String, String>();  
					if(("类型").equals(typetitle)||("全部").equals(typetitle)){
						typetitle= "";
					}
					if(("综合排序").equals(ordertitle)){
						ordertitle= "";
					}

					paramsValue.put("city","");
					paramsValue.put("type",typetitle);
					paramsValue.put("regional",areatitle);
					paramsValue.put("orderby", ordertitle);
					paramsValue.put("page",page+"");
					if(NetWorkUtil.isNetAvailable(getActivity())){
						String 	ss = new HttpP().sendPOSTRequestHttpClient(url1.trim(),paramsValue);
						if(("").equals(ss)||("notfind").equals(ss)){
							handler.sendEmptyMessage(8);
						}else{
							List<Find_yingshi> newFyslist = initying(ss);
							if(page == 1){//下拉刷新
								fyslist = newFyslist;
								//								if(fyslist != null){
								//									fyslist.addAll(0, newFyslist);
								//								}else{
								//									fyslist = newFyslist;
								//								}
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

	/**
	 * 
	 * @param 当前view获取下标
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

	private void geneItems() {
		page++;
		initThreadSecond();

	}
	/**
	 * 上拉刷新回调
	 */
	@Override
	public void onRefresh() {
		page=1;
		initThreadSecond();
		onLoad();
	}
	/**
	 * 加载更多回调
	 */
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
	/**
	 * 筛选后接口的解析
	 * @param jss
	 * @return
	 */
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
	/**
	 * 
	 * @param jsonData 筛选前接口的解析
	 * @return Pscd 对象
	 */
	public  Pscd  fa(String jsonData){
		str =  new ArrayList<String>();
		str1 =  new ArrayList<String>();
		str2 = new ArrayList<String>();
		str3= new ArrayList<String>();
		pscd =null;
		mas = new HashMap<List<String>, ArrayList<String>>();
		JSONObject jo;
		try {
			jo = new JSONObject(jsonData);
			JSONArray types = jo.optJSONArray("type1");
			for (int i = 0; i < types.length(); i++) {
				str.add(types.optString(i));
			}
			JSONArray regionals = jo.optJSONArray("regional1");
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
			JSONArray orderby = jo.optJSONArray("orderby1");
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

