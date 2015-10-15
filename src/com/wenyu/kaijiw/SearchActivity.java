package com.wenyu.kaijiw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywork.util.NetWorkUtil;
import com.wenyu.Data.HttpP;
import com.wenyu.Data.SearchBean;
import com.wenyu.Data.Urls;
import com.wenyu.kjw.adapter.SearchAdapter;

public class SearchActivity extends Activity {
	private String url = Urls.Url_Search;	
	private AutoCompleteTextView searchText;
	private ImageView searchDel;	
	private TextView searchStart;
	private String ss,searchContent,customer_id;
//	private Textiew clearHistory;
	private Map<String,String> paramsValue;
	private ListView mListview;
	private List<SearchBean> fyslist;
//    private ArrayAdapter<String> adapter_history;
    private SearchAdapter hla;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				Toast.makeText(SearchActivity.this, "网络连接异常 ", 1000).show();
				break;
			case 1:
				break;
			case 3:
				hla =new SearchAdapter(fyslist, SearchActivity.this);
				mListview.setAdapter(hla);
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
        initView();
        mListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				switch(position){
				default:
					SearchBean fs = fyslist.get(position);
					Intent it1;
					if("器材".equals(fs.type)){
						it1= new Intent(SearchActivity.this,EquipsNearby.class);
						it1.putExtra("pic", fs.picture);
						it1.putExtra("id", fs.id);
						it1.putExtra("customer_id", customer_id);
						startActivity(it1);
						
					}
					break;

				}

			}
		});
	}

	
	List<searchBean> listData = new ArrayList<SearchActivity.searchBean>();
	
	
	private void initView() {
		searchText =  (AutoCompleteTextView) findViewById(R.id.searchTextView1);
		searchDel = (ImageView) findViewById(R.id.searchDel);
		searchStart = (TextView) findViewById(R.id.searchStart);
//		clearHistory = (TextView) findViewById(R.id.searchclean);
		customer_id = getIntent().getStringExtra("customer_id");
		mListview= (ListView) findViewById(R.id.searchlistview);
		// 获取搜索记录文件内容
		
//        adapter_history = new ArrayAdapter<String>(this, 
//        android.R.layout.simple_dropdown_item_1line, hisArrays); 
//        if (hisArrays.length > 50) { 
//            String[] newArrays = new String[50]; 
//            System.arraycopy(hisArrays, 0, newArrays, 0, 50); 
//            adapter_history = new ArrayAdapter<String>(this, 
//                    android.R.layout.simple_dropdown_item_1line, newArrays); 
//        } 
        
		listData = getData();
        final Madapter adapter = new Madapter(this, listData);
        
        searchText.setAdapter(adapter); 
        searchText.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				if(adapter.getItem(arg2).id == 0){
			        SharedPreferences sp =getSharedPreferences("history_strs",0);
			        SharedPreferences.Editor editor=sp.edit();
			        editor.clear();
			        editor.commit();
			        Toast.makeText(SearchActivity.this, "清除成功", Toast.LENGTH_SHORT).show();
					searchText.setText("");
				}else{
					searchText.setText(adapter.getItem(arg2).name);
				}
				
			}
		});
		searchDel.setOnClickListener(ol);
		searchStart.setOnClickListener(ol);
//		clearHistory.setOnClickListener(ol);
	    	
	}
	
	private List<searchBean> getData(){
		List<searchBean> mBeans = new ArrayList<SearchActivity.searchBean>();
		SharedPreferences sp = getSharedPreferences("history_strs", 0); 
        String save_history = sp.getString("history", "暂时没有搜索记录"); 
        // 用逗号分割内容返回数组
        String[] hisArrays = save_history.split(","); 
        for(String name : hisArrays){
        	searchBean bean = new searchBean();
        	bean.name = name;
        	bean.id = 1;
        	mBeans.add(bean);
        }
        
        if(mBeans.size() > 0){
        	 searchBean bean = new searchBean();
     		bean.id = 0;
     		bean.name = "清除历史记录";
     		mBeans.add(bean);
        }
       
		return mBeans;
	}
	
	class searchBean{
		String name;
		int id;
	};
	
	public final class Madapter extends BaseAdapter implements Filterable{
		List<searchBean> mBeans = new ArrayList<SearchActivity.searchBean>();
		
		private LayoutInflater mInflater;
		private ArrayFilter mFilter;
		
		public Madapter(Context context,List<searchBean> beans) {
			
			mBeans = beans;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mBeans.size();
		}

		@Override
		public searchBean getItem(int arg0) {
			// TODO Auto-generated method stub
			return mBeans.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(final int arg0, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub\
			Messages info = null;
			if(convertView == null){
				info = new Messages();
				convertView = mInflater.inflate(R.layout.com_item, null);
				info.name = (TextView)convertView.findViewById(R.id.textView1);
				convertView.setTag(info);
			}else{
				info = (Messages) convertView.getTag();
			}
			
			if(mBeans.get(arg0).id == 0){
				info.name.setTextColor(Color.RED);
			}else{
				info.name.setTextColor(Color.BLACK);	
			}
			info.name.setText(mBeans.get(arg0).name);
			
			return convertView;
		}

		@Override
		public Filter getFilter() {
			if (mFilter == null) {  
	            mFilter = new ArrayFilter();  
	        }  
	        return mFilter; 
		}
		
		
		 private class ArrayFilter extends Filter {  
			  
		        @Override  
		        protected FilterResults performFiltering(CharSequence prefix) {  
		            FilterResults results = new FilterResults();  
		  
//		            if (mBeans == null) {  
//		            	mBeans = new ArrayList<searchBean>(mBeans);  
//		            }  
		  
		            if (prefix == null || prefix.length() == 0) {  
		                List<searchBean> list = getData();  
		                results.values = list;  
		                results.count = list.size();  
		            } else {  
		                String prefixString = prefix.toString().toLowerCase();  
		  
		                List<searchBean> unfilteredValues = getData();  
		                int count = unfilteredValues.size();  
		  
		                ArrayList<searchBean> newValues = new ArrayList<searchBean>(count);  
		  
		                for (int i = 0; i < count; i++) {  
		                	searchBean pc = unfilteredValues.get(i);  
		                    if (pc != null) {  
		                          
		                        if(pc.name!=null && pc.name.startsWith(prefixString)){  
		                              
		                            newValues.add(pc);  
		                        }else if(pc.name!=null && pc.name.startsWith("清除历史记录")){
		                        	newValues.add(pc);  
		                        }
		                    }  
		                }  
		  
		                results.values = newValues;  
		                results.count = newValues.size();  
		            }  
		  
		            return results;  
		        }  
		  
		        @Override  
		        protected void publishResults(CharSequence constraint,  
		                FilterResults results) {  
		             //noinspection unchecked  
		        	mBeans = (List<searchBean>) results.values;  
		            if (results.count > 0) {  
		                notifyDataSetChanged();  
		            } else {  
		                notifyDataSetInvalidated();  
		            }  
		        }  
		          
		    }
		
	};
	
	
	
	public final class Messages {
		public TextView name;
	}
	
	OnClickListener ol = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
          switch (arg0.getId()) {
          case R.id.search_text:
        	searchContent =searchText.getText().toString();
    		
        	  break;
		case R.id.searchDel:
			searchText.setText(null);
			break;
		case R.id.searchStart:	
			searchContent =searchText.getText().toString();
			Save(searchContent);
			if(searchContent.trim().equals("")){
				 Toast.makeText(SearchActivity.this, "搜索为空", Toast.LENGTH_SHORT).show();
                 return;
			}
			 movesPeople();
			break;
//		case R.id.searchclean:
//	        SharedPreferences sp =getSharedPreferences("history_strs",0);
//	        SharedPreferences.Editor editor=sp.edit();
//	        editor.clear();
//	        editor.commit();
//	        Toast.makeText(SearchActivity.this, "清除成功", Toast.LENGTH_SHORT).show();
//
//			break;
		default:
			break;
		}			
	  }
	};
	private void Save(String content) {
		SharedPreferences sp = getSharedPreferences("history_strs", 0); 
        String save_Str = sp.getString("history", "暂时没有搜索记录"); 
        String[] hisArrays = save_Str.split(","); 
        for(int i=0;i<hisArrays.length;i++) 
        { 
            if(hisArrays[i].equals(content)) 
            { 
                return; 
            } 
        }
        StringBuilder sb = new StringBuilder(save_Str); 
        sb.append(content + ","); 
        sp.edit().putString("history", sb.toString()).commit(); 
        Toast.makeText(SearchActivity.this, sb.toString(), Toast.LENGTH_LONG).show(); 
	}

	private void movesPeople() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					paramsValue=new HashMap<String, String>();  
					paramsValue.put("keyword",searchText.getText().toString());
					if(NetWorkUtil.isNetAvailable(SearchActivity.this)){
						ss = new HttpP().sendPOSTRequestHttpClient(url,paramsValue);
						initying(ss);
						handler.sendEmptyMessage(3);
					}else {
						handler.sendEmptyMessage(0);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

				
		}).start();
		
	}
	private List<SearchBean> initying(String jsonData) {
		fyslist = new ArrayList<SearchBean>();
		try {
			JSONArray jsonArray = 	new JSONArray(jsonData);
			SearchBean searchBean = null;
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = jsonArray.optJSONObject(i);
				
//				
				searchBean = new SearchBean();
				searchBean.name = jo.optString("name");
				searchBean.picture = jo.optString("image");
				searchBean.id = jo.optString("objId");
				searchBean.countV = jo.optString("introduce");
				searchBean.type = jo.optString("type");
				
				fyslist.add(searchBean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return fyslist;				
	}			

}
