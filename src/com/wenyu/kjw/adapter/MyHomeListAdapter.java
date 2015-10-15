package com.wenyu.kjw.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.wenyu.Data.Myone;
import com.wenyu.kaijiw.R;

public class MyHomeListAdapter  extends BaseAdapter{
	private  List<Myone> strs;
	private Context context;
	private ViewHolder vh;
	public MyHomeListAdapter(List<Myone> strs, Context context) {
		super();
		this.strs = strs;
		this.context = context;
		
	}

	@Override
	public int getCount() {
		if(strs.size()==0){
			return 0 ;
		}
		return strs.size();
	}

	@Override
	public Object getItem(int arg0) {
		if(strs.size()==0){
			return 0 ;
		}
		return strs.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	/*
	 * (non-Javadoc)
	 *getView方法的数据需要更改。
	 * */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		vh = new ViewHolder();
		if(convertView ==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.myhome_list
					, null);
			vh.image = (ImageView) convertView.findViewById(R.id.imageView);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		Myone str =strs.get(position);
		vh.image.setImageResource(str.getImage());
		return convertView;
	}
	
	/**
	* 动态设置ListView的高度
	* @param listView
	*/
	public static void setListViewHeightBasedOnChildren(ListView listView) {
	    if(listView == null) return;
	    MyHomeListAdapter  listAdapter = (MyHomeListAdapter) listView.getAdapter();
	    if (listAdapter == null) {
	        // pre-condition
	        return;
	    }
	    int totalHeight = 0;
	    for (int i = 0; i < listAdapter.getCount(); i++) {
	        View listItem = listAdapter.getView(i, null, listView);
	        listItem.measure(0, 0);
	        totalHeight += listItem.getMeasuredHeight();
	    }
	    ViewGroup.LayoutParams params = listView.getLayoutParams();
	    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	    listView.setLayoutParams(params);
	}

	
	class ViewHolder{
		ImageView image;
	}
}
