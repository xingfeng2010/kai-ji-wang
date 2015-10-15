package com.wenyu.kjw.adapter;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenyu.Data.ReFindXiang;
import com.wenyu.kaijiw.R;

public class ReFindXiangAdapter extends BaseAdapter {
	Context context;
	List<ReFindXiang> list;
	public ReFindXiangAdapter(Context context, List<ReFindXiang> list) {
		super();
		this.context = context;
		this.list = list;
		
	}

	@Override
	public int getCount() {
		if(list.size()==0){
			return 0;
		}
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		if(list.size()==0){
			return null;
		}
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		ViewHolder h;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.griditem, null);
			h = new ViewHolder();
			h.img = (ImageView) convertView.findViewById(R.id.imageView);
			h.name = (TextView) convertView.findViewById(R.id.textView);
			convertView.setTag(h);
		}else{
			h = (ViewHolder)convertView.getTag();
		}
		h.name.setText(list.get(position).getName());
		
		
		return convertView;
	}
	class ViewHolder{
		ImageView img;
		TextView name;
	}

}
