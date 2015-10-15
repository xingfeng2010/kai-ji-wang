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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Publishpublic;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.kjw.adapter.PublishpublicAdapter;

/**
 * 发布器材
 */
public class ExpandableList extends Activity {
	private ListView listview;
	private String urldeleat=ConstantClassField.SERVICE_URL +"service/delQCByCustomerIDAndEquID";
	private String url =ConstantClassField.SERVICE_URL +"service/getEqeListByCustomerID";
	private String[] images;
	private String customer_id,equtype,ss,item_id;
	private Map<String, String> paramsValue;
	private List<Publishpublic> pubs;
	private Button button1;
	private PublishpublicAdapter psa;
	private TextView textView4,textView2;
	private EditText edits;
	private SwipeMenuListView mListView;
	private ImageView back;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				psa = new PublishpublicAdapter(ExpandableList.this,pubs);
				mListView.setAdapter(psa);
				psa.notifyDataSetChanged();
				button1.setOnClickListener(new OnClickListener() {
					int a =0;
					@Override
					public void onClick(View arg0) {
						if(a%2==0){
							mListView.setVisibility(View.VISIBLE);
							button1.setBackgroundResource(R.drawable.group_down);
						}else{
							button1.setBackgroundResource(R.drawable.group_up);
							mListView.setVisibility(View.GONE);
						}
						a++;
					}
				});
			}
		};
	};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publishequipment);
		initView();
		initThread();
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
		Intent intent = new Intent(ExpandableList.this,ExpandableListAddDetail.class);
		intent.putExtra("customer_id",customer_id);
		intent.putExtra("equ_type",equtype);
		intent.putExtra("item_id", pubs.get(arg2).getIdqi());
		intent.putExtra("item_count", pubs.get(arg2).getNumber());
		intent.putExtra("item_price",pubs.get(arg2).getPrice());
		startActivity(intent);
		ExpandableList.this.finish();
		}
	});
	// step 2. listener item click event
	mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
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
				psa.notifyDataSetChanged();
				break;
			}
			return false;
		}
	});
	
	}
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
	private void initView() {
		item_id = getIntent().getStringExtra("item_id");
		mListView = (SwipeMenuListView) findViewById(R.id.listview);
		button1 =  (Button) findViewById(R.id.button1);
		textView4 =(TextView) findViewById(R.id.textView4);
		textView2 =(TextView) findViewById(R.id.textView2);
		customer_id = getIntent().getStringExtra("customer_id");
		equtype = getIntent().getStringExtra("equ_type");
		edits = (EditText) findViewById(R.id.edits);
		textView4.setText("添加");
		back=(ImageView) findViewById(R.id.imageView1);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ExpandableList.this.finish();
				
			}
		});
		textView4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent  it = new Intent(ExpandableList.this,ExpandableListAdd.class);
				it.putExtra("customer_id", customer_id);
				it.putExtra("equ_type",equtype);
				if(pubs.size()>0){
					for (int i = 0; i < pubs.size(); i++) {
						it.putExtra("category",pubs.get(i).getCategory());
					}
					startActivity(it);
					ExpandableList.this.finish();
				}
			}
		});
		textView2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				initThread();
				
			}
		});
		
	}
	public List<Publishpublic>parseJson(String jsons){
		List<Publishpublic>  pubs = new ArrayList<Publishpublic>();
		images = new String[30];
		try {
			JSONArray jarr = new JSONArray(jsons);
			for (int i = 0; i < jarr.length(); i++) {
				JSONObject  jo = jarr.optJSONObject(i);
				String idQi = jo.optString("id");
				String idShop = jo.optString("theEquipId");
				String name = jo.optString("name");
				String coverimage = jo.optString("coverImage");
				images[i] = coverimage;
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
	
	
	private void initThread() {
		new Thread(new Runnable(){

			@Override
			public void run() {
				paramsValue=new HashMap<String, String>(); 
				paramsValue.put("equ_type",equtype);
				paramsValue.put("customer_id",customer_id);
				paramsValue.put("equ_search",edits.getText().toString());
				try {
					ss = new HttpP().sendPOSTRequestHttpClient(url.trim(),paramsValue);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(("null").equals(ss)&&("").equals(ss)){
					//handler.sendEmptyMessage(8);
				}else {
					pubs = parseJson(ss);
					handler.sendEmptyMessage(1);
				}
			}

		}).start();
	}


}
