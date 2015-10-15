package com.wenyu.kjw.adapter;
import java.util.List;

import com.wenyu.Data.ReFindVistData;
import com.wenyu.kaijiw.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ReFindVistAdpater extends BaseAdapter {
	Context context;
	List<ReFindVistData> list;
	public ReFindVistAdpater(Context context, List<ReFindVistData> list) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.refindvist, null);
			h = new ViewHolder();
			h.type = (TextView) convertView.findViewById(R.id.textView1);
			h.position = (TextView) convertView.findViewById(R.id.textView2);
			h.name= (TextView) convertView.findViewById(R.id.textView4);
			h.year= (TextView) convertView.findViewById(R.id.textView5);
			convertView.setTag(h);
		}else{
			h = (ViewHolder)convertView.getTag();
		}
		ReFindVistData  rfi = list.get(position);
		h.name.setText(rfi.getNames().get(position));
			h.position.setText(rfi.getPositions().get(position));
		h.type.setText(rfi.getFilmtype().get(position));
		h.year.setText(rfi.getYears().get(position));
		return convertView;
	}
	class ViewHolder{
		TextView name,value,position,type,year;
	}

}
