package com.wenyu.kjw.adapter;

import java.util.List;

import com.wenyu.kaijiw.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SliAdapter  extends BaseAdapter{
	private  List<String> strs;
	private Context context;
	private ViewHolder vh;

	public SliAdapter(List<String> strs, Context context) {
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView ==null){
			vh = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.sliding_listitem
					, null);
			vh.text = (TextView) convertView.findViewById(R.id.sltext);
			convertView.setTag(vh);
		}else{
			vh= (ViewHolder) convertView.getTag();
		}
		String str =strs.get(position);
		vh.text.setText(str);
		return convertView;
	}
	class ViewHolder{
		TextView text;
	}
}
