package com.wenyu.kjw.adapter;
import java.util.List;

import cache.loader.ImageWorker;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.wenyu.Data.Find_yingshi;
import com.wenyu.Data.MyMessage;
import com.wenyu.Utils.BitmapImage;
import com.wenyu.kaijiw.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class myMessageadapter extends BaseAdapter {
	Context context;
	List<MyMessage> list;
	ViewHolder h;
	public myMessageadapter(Context context, List<MyMessage> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		if(list.size()==0){
			return  0;
		}
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		if(list.size()==0){
			return 0;
		}
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.griditem, null);
			h = new ViewHolder();
			h.name = (TextView) convertView.findViewById(R.id.textView);
			convertView.setTag(h);
		}else{
			h = (ViewHolder)convertView.getTag();
		}
		return convertView;
	}
	class ViewHolder{
		TextView img;
		TextView name;
	}

}
