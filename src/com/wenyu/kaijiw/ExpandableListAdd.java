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
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.Publishpublic;
import com.wenyu.Utils.ConstantClassField;
import com.wenyu.kjw.adapter.PublishpublicAddAdapter;

/**
 * Ìí¼ÓÆ÷²Ä 
 */
public class ExpandableListAdd extends Activity {
	private ListView listview;
	String url =ConstantClassField.SERVICE_URL +"service/searchEquipmentByKeyWord";
	private String[] images;
	private String customer_id,category,ss,equtype;
	private Map<String, String> paramsValue;
	private List<Publishpublic> pubs,pu;
	private Button button1;
	private PublishpublicAddAdapter psa;
	private TextView textView4,textView2;
	private EditText edits;
	private SwipeMenuListView mListView;
	private ImageView back;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				psa = new PublishpublicAddAdapter(ExpandableListAdd.this,pubs);
				mListView.setAdapter(psa);
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
	}

	private void initView() {
		mListView = (SwipeMenuListView) findViewById(R.id.listview);
		button1 =  (Button) findViewById(R.id.button1);
		textView4 =(TextView) findViewById(R.id.textView4);
		edits = (EditText) findViewById(R.id.edits); 
		textView2 =(TextView) findViewById(R.id.textView2);
		category = getIntent().getStringExtra("category");
		customer_id = getIntent().getStringExtra("customer_id");
		equtype = getIntent().getStringExtra("equ_type");
		back=(ImageView) findViewById(R.id.imageView1);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ExpandableListAdd.this.finish();
				
			}
		});
//		mListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				Intent  it = new Intent(ExpandableListAdd.this,ExpandableListAddDetail.class);
//				
//				startActivity(it);
//			}
//		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			Intent intent = new Intent(ExpandableListAdd.this,ExpandableListAddDetail.class);
			intent.putExtra("customer_id",customer_id);
			intent.putExtra("item_id",pubs.get(arg2).getIdqi());
			intent.putExtra("equ_type",equtype);
			intent.putExtra("item_count","0");
			intent.putExtra("item_price","0");
			startActivity(intent);
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
				String name = jo.optString("name");
				String coverimage = jo.optString("coverImage");
				images[i] = coverimage;
				Publishpublic psp = new Publishpublic(idQi,name,coverimage);
				pubs.add(psp);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return pubs;
	}
	private void initThread() {
		new Thread(new Runnable(){

			@Override
			public void run() {
				paramsValue=new HashMap<String, String>(); 
				paramsValue.put("equ_type",category);
				paramsValue.put("customer_id",customer_id);
				paramsValue.put("KeyWord",edits.getText().toString()+"");
				try {
					ss = new HttpP().sendPOSTRequestHttpClient(url.trim(),paramsValue);
//					System.err.println("_____________________________!111111111_______________"+url+paramsValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(("null").equals(ss)&&("").equals(ss)){
//					handler.sendEmptyMessage(8);
				}else {
					pubs = parseJson(ss);
					handler.sendEmptyMessage(1);
				}
			}
			
		}).start();
	}


}
