package com.wenyu.kaijiw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Publishpublic;
import com.wenyu.Data.ScreenManager;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.kjw.adapter.PublishStudioNoImageAdapter;

public class PublistStudio extends Activity{
	private String url =ConstantClassField.SERVICE_URL +"service/getEqeListByCustomerID";
	private String urldeleat=ConstantClassField.SERVICE_URL +"service/delQCByCustomerIDAndEquID";
	private ImageView back;
	private TextView text,add;
	private ListView listview;
	private String customer_id,equtype,ss;
	private Map<String, String> paramsValue;
	private List<Publishpublic>  pubs;
	private SwipeMenuListView mListView;
	private PublishStudioNoImageAdapter pas ;
	private ScreenManager sm;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				pas = new  PublishStudioNoImageAdapter(pubs, PublistStudio.this);
				mListView.setAdapter(pas);
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publishstudio);
		initView();
		initValue();
		initThread();

	}
	private void initValue() {
		// 创建左移删除按钮
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem openItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				// set item width
				openItem.setWidth(dp2px(90));
				// set item title
				openItem.setTitle("取消");
				// set item title fontsize
				openItem.setTitleSize(18);
				// set item title font color
				openItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(openItem);

				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				// set item width
				deleteItem.setWidth(dp2px(90));
				// set a icon
				deleteItem.setIcon(R.drawable.ic_delete);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};

		mListView.setMenuCreator(creator);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(PublistStudio.this,PublishStudioAdd.class);
				intent.putExtra("customer_id",customer_id);
				intent.putExtra("item_id", pubs.get(arg2).getIdqi());
				intent.putExtra("item_name", pubs.get(arg2).getName());
				intent.putExtra("item_type",pubs.get(arg2).getType());
				intent.putExtra("item_contact", pubs.get(arg2).getContact());
				intent.putExtra("item_contact_phone",pubs.get(arg2).getTelephone());
				intent.putExtra("item_price",pubs.get(arg2).getPrice());
				startActivity(intent);
			}
		});
		
		// listview的Item删除 
		mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				//下标 index 或position
				final String idq = pubs.get(position).getIdqi();
				switch (index) {
				case 0:
					break;
				case 1:
					pubs.remove(position);
					new Thread(new Runnable(){
						@Override
						public void run() {
							Map<String, String>	paramsValue=new HashMap<String, String>(); 
							paramsValue.put("customer_id",customer_id);
							paramsValue.put("item_id",idq);
							try {
								String d = new HttpP().sendPOSTRequestHttpClient(urldeleat,paramsValue);
							} catch (Exception e) {
								e.printStackTrace();
							}

						}

					}).start();
					pas.notifyDataSetChanged();
					break;
				}
				return false;
			}
		});
		
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(PublistStudio.this,PublishStudioAdd.class);
				intent.putExtra("customer_id",customer_id);
				intent.putExtra("item_id", "");
				intent.putExtra("item_name", "");
				intent.putExtra("item_type","");
				intent.putExtra("item_contact", "");
				intent.putExtra("item_contact_phone","");
				intent.putExtra("item_price","");
				startActivity(intent);

			}
		});
		
	}
	//设置 宽高像素
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	//开启线程访问 影棚数据
	private void initThread() {
		//		 customer_id ：用户ID
		//		 equ_type ：发布的类别（拍摄器材、辅助器材、灯光器材）
		//		 equ_search：器材名称；检索
		new Thread(new Runnable(){

			@Override
			public void run() {

				try {
					paramsValue=new HashMap<String, String>(); 
					paramsValue.put("equ_type",equtype);
					paramsValue.put("customer_id",customer_id);
					//					paramsValue.put("type_str","演播棚");
					if(NetWorkUtil.isNetAvailable(PublistStudio.this)){
						ss = new HttpP().sendPOSTRequestHttpClient(url.trim(),paramsValue);
						if(("null").equals(ss)&&("").equals(ss)){
							handler.sendEmptyMessage(8);
						}else {
							pubs = parseJson(ss);
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
	//解析 影棚数据
	public List<Publishpublic>parseJson(String jsons){
		List<Publishpublic>  pubs = new ArrayList<Publishpublic>();
		//		images = new String[30];
		try {
			JSONArray jarr = new JSONArray(jsons);
			for (int i = 0; i < jarr.length(); i++) {
				JSONObject  jo = jarr.optJSONObject(i);
				String idQi = jo.optString("id");
				String idShop = jo.optString("theEquipId");
				String name = jo.optString("name");
				String coverimage = jo.optString("coverImage");
				//				images[i] = coverimage;
				String price = jo.optString("price");
				String number = jo.optString("number");
				String type = jo.optString("type");
				String category = jo.optString("category");
				String contact = jo.optString("contact");
				String telephone = jo.optString("telephone");
				Publishpublic psp = new Publishpublic(idQi,idShop,name,coverimage,price,number,type,category,contact,telephone);
				pubs.add(psp);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pubs;
	}
	
	// 初始化控件
	private void initView() {
		//		 customer_id ：用户ID
		//		  item_id ： 器材ID
		//		 item_count ：器材数量
		//		  item_price ：器材价格
		sm = ScreenManager.gerScreenManager();
		mListView = (SwipeMenuListView) findViewById(R.id.listview);
		customer_id = getIntent().getStringExtra("customer_id");
		equtype = getIntent().getStringExtra("equ_type");
		back = (ImageView) findViewById(R.id.imageView1);
		text = (TextView) findViewById(R.id.textView1);
		add = (TextView) findViewById(R.id.textView2);
		back.setOnClickListener(ocl);
		add.setOnClickListener(ocl);
	}
	OnClickListener ocl =new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch(arg0.getId()){
			case R.id.textView2:
				Intent publishstudioadd =  new Intent(PublistStudio.this,PublishStudioAdd.class);
				publishstudioadd.putExtra("equ_type", equtype);
				publishstudioadd.putExtra("customer_id", customer_id);
				startActivity(publishstudioadd);
				PublistStudio.this.finish();
				sm.popAllActivityExceptOne(PublistStudio.class);
				break;
			case R.id.imageView1:
				PublistStudio.this.finish();
				break;
			}
		}
	};

}
