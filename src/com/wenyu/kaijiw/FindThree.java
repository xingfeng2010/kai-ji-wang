package com.wenyu.kaijiw;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.BaoyzApplication;
import com.example.mywork.util.NetWorkUtil;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.wenyu.Data.Find_yingshi;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Myhome;
import com.wenyu.Data.Pscd;
import com.wenyu.Data.ScreenManager;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.choose.ExpandTabView;
import com.wenyu.choose.ViewLeft;
import com.wenyu.choose.ViewMiddle;
import com.wenyu.choose.ViewRight;
import com.wenyu.kjw.adapter.MyhomeAdapter;
/**
 * 影视人才_分组
 * @author huhaoran
 *
 */
public class FindThree extends Activity{
	private String url =ConstantClassField.SERVICE_URL +"service/talenttop";
	private String urlcontent =ConstantClassField.SERVICE_URL + "service/talents";
	List<Find_yingshi> list;
	private String ss;
	private Spinner sp1,sp2,sex ;
	private TextView text;
	private GridView gridview;
	private ArrayAdapter<String> aa,bb,cc ;
	private ImageView back;
	private ScreenManager sm;
	private Map<String,String> paramsValue;
	private String keys,s,s1,s2;
	private List<String>str,str1,str2;
	private List<Myhome>  myhomes ;
	private PullToRefreshGridView mPullRefreshListView;
	private int page=1;
	private MyhomeAdapter d;
	private ExpandTabView expandTabView;
	private ViewLeft viewLeft;
	private ViewMiddle viewMiddle;
	private ViewRight viewRight;
	private  String[] area,typearr,orderarr,areaarr ;// 显示字段
	private ArrayList<View> mViewArray = new ArrayList<View>();// view数组
	private String typetitle,areatitle,ordertitle;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				mPullRefreshListView.onRefreshComplete();
				Toast.makeText(FindThree.this, "网络连接异常", 1000).show();
				break;
			case 1:
				typearr=(String[])str.toArray(new String[str.size()]);
				orderarr=(String[])str1.toArray(new String[str1.size()]);
				areaarr=(String[])str2.toArray(new String[str2.size()]);
				viewMiddle = new ViewMiddle(FindThree.this,
						typearr);
				viewLeft = new ViewLeft(FindThree.this,orderarr);
				
				viewRight = new ViewRight(FindThree.this,areaarr);
				initVaule();
				initListener();
				onRefresh(viewMiddle, "");

				break;
			case 5:
				if(page>1){
					d.setData(myhomes);
					d.notifyDataSetChanged();
					mPullRefreshListView.onRefreshComplete();
				}else{
					mPullRefreshListView.onRefreshComplete();
					d =new MyhomeAdapter(FindThree.this,myhomes);
					mPullRefreshListView.setAdapter(d);
				}
				break;
			case 6:
				Toast.makeText(FindThree.this, "没有更多数据", 1000).show();
				break;

			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_three);
		initView();
		movesPeople();
		//		expandTabView.onPressBack();
		mPullRefreshListView
		.setOnRefreshListener(new OnRefreshListener2<GridView>()	{

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<GridView> refreshView)
			{
				Log.e("TAG", "onPullDownToRefresh"); // Do work to
				String label = DateUtils.formatDateTime(
						getApplicationContext(),
						System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy()
				.setLastUpdatedLabel(label);

				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						page=1;
						initThread();
					}
				}, 100);
				
				
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<GridView> refreshView)
			{
				Log.e("TAG", "onPullUpToRefresh"); 
				page++;
				initThread();
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
		// 设置顶部默认显示的条目,从Itent获取显示的位置

		expandTabView.setTitle(viewMiddle.getShowText(typearr,0), 0);
		expandTabView.setTitle(viewLeft.getShowText(0,orderarr), 1);
		expandTabView.setTitle(viewRight.getShowText(0,areaarr), 2);

	}


//	private void initIndicator()
//	{
//		ILoadingLayout startLabels = mPullRefreshListView
//				.getLoadingLayoutProxy(true, false);
//		startLabels.setPullLabel("刷新...");// 刚下拉时，显示的提示
//		startLabels.setRefreshingLabel("正在刷新...");// 刷新时
//		startLabels.setReleaseLabel("刷新...");// 下来达到一定距离时，显示的提示
//	}
	private void initThread() {
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
				}
				if("职位".equals(s)||("全部").equals(s)){
					s="";
				}
				paramsValue.put("category", keys);
				paramsValue.put("position",areatitle);
				paramsValue.put("orderby",ordertitle);
				paramsValue.put("gender",typetitle);
				paramsValue.put("page", page+"");
				if(NetWorkUtil.isNetAvailable(FindThree.this)){
					try {
						String content = new HttpP().sendPOSTRequestHttpClient(urlcontent,paramsValue);
						if(content!=null ){
							List<Myhome> newmyhomes= getJson(content);
							if(page==1){
								myhomes =newmyhomes;
							}else{
								if(newmyhomes!=null){
									myhomes.addAll(newmyhomes);
								}else{
									myhomes =newmyhomes;
								}
							}
							handler.sendEmptyMessage(5);
						}else {
							handler.sendEmptyMessage(6);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {
					handler.sendEmptyMessage(0);
				}

			}

		}).start();
	}
	private void movesPeople() {
		new Thread(new Runnable(){

			@Override
			public void run() {

				try {
					paramsValue=new HashMap<String, String>();  
					paramsValue.put("category", keys);
					if(NetWorkUtil.isNetAvailable(FindThree.this)){
						ss = new HttpP().sendPOSTRequestHttpClient(url,paramsValue);
						Pscd pscd = null;
						pscd = fa(ss);
						if(pscd!=null){
							handler.sendEmptyMessage(1);
						}else {
							System.out.println(ss);
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
	public List<Myhome> getJson(String str1){
		List<Myhome>  myhomes = new ArrayList<Myhome>();
		try {
			JSONArray  ja = new JSONArray(str1);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject  obj = ja.optJSONObject(i);
				String id = obj.optString("id");
				String  name = obj.optString("nameCn");
				String image = obj.optString("image");
				Myhome myhome = new Myhome(name, image,id);
				myhomes.add(myhome);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return myhomes;

	}


	public  Pscd  fa(String jsonData){
		str = new ArrayList<String>();
		str1 = new ArrayList<String>();
		str2 = new ArrayList<String>();
		Pscd	pscd =null;
		JSONObject jo;
		try {
			jo = new JSONObject(jsonData);
			JSONArray types = jo.optJSONArray("position");
			for (int i = 0; i < types.length(); i++) {
				str.add(types.optString(i));
			}
			JSONArray  genders = jo.optJSONArray("gender");
			for (int i = 0; i < genders.length(); i++) {
				str1.add(genders.optString(i));
			}
			JSONArray orderby = jo.optJSONArray("orderby");
			for (int i = 0; i < orderby.length(); i++) {
				str2.add(orderby.optString(i));
			}
			pscd = new Pscd(str, str1,str2);


		} catch (JSONException e) {
			e.printStackTrace();
		}
		return pscd ;

	}  
	private void initView() {
		sm =  ScreenManager.gerScreenManager();
		sm.pushActivity(FindThree.this);
		str= new ArrayList<String>();
		str1= new ArrayList<String>();
		back= (ImageView) findViewById(R.id.imageView1);
		expandTabView = (ExpandTabView)findViewById(R.id.expandtab_view);
		mPullRefreshListView = (PullToRefreshGridView) findViewById(R.id.pull_refresh_grid);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sm.popActivity(sm.currentActivity());
			}
		});
		text = (TextView) findViewById(R.id.textView1);
		keys =getIntent().getStringExtra("key");
		text.setText(keys);
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//				Toast.makeText(FindThree.this, arg2+myhomes.size(), 1000).show();
				if(BaoyzApplication.getInstance().isLogined){
					Intent it = new Intent(FindThree.this,FindTwoFouractivity.class);
					if(arg2<myhomes.size()){
						it.putExtra("id", myhomes.get(arg2).getId());
						it.putExtra("image", myhomes.get(arg2).getImag());
						startActivity(it);
					}else{

					}
				}else{
					new AlertDialog.Builder(FindThree.this)
					.setMessage("您还没有登录，登录后可以查看资料")
					.setNegativeButton("去登录",new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							Intent intent = new Intent(FindThree.this,LoginActivity.class);
							startActivity(intent);
						}
					})
					.setPositiveButton("再看看", null)
					.show().setCanceledOnTouchOutside(false);
				}

			}
		});

	}
	private void initListener() {
		typetitle ="";
		areatitle ="";
		ordertitle="";
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

			}

			@Override
			public void getValue(String distance, String showText) {
				// TODO Auto-generated method stub
				areatitle = showText;
				onRefresh(viewMiddle, showText);
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
				expandTabView.setTitle("职位", position);
			}else{
				expandTabView.setTitle(showText, position);
			}

		}
		initThread();
	}
	/**
	 * 所属列表
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
