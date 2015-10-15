package com.wenyu.kjw.adapter;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wenyu.Data.ReFindIntroData;
import com.wenyu.kaijiw.R;

public class ReFindIntroAdpater extends BaseAdapter {
	Context context;
	List<ReFindIntroData> list;
	public ReFindIntroAdpater(Context context, List<ReFindIntroData> list) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.refindintro, null);
			h = new ViewHolder();
			h.name = (TextView) convertView.findViewById(R.id.textView1);
			h.value = (TextView) convertView.findViewById(R.id.textView2);
			h.textView3= (TextView) convertView.findViewById(R.id.textView3);
			convertView.setTag(h);
		}else{
			h = (ViewHolder)convertView.getTag();
		}
		ReFindIntroData  rfi = list.get(position);
		h.name.setText(rfi.getName().get(position));
		if(rfi.getValue().get(position).length()>8){
			h.value.setText("");
			h.value.setVisibility(View.GONE);
			h.textView3.setVisibility(View.VISIBLE);
			h.textView3.setText(rfi.getValue().get(position));
		}else{
			h.textView3.setText("");
			h.textView3.setVisibility(View.GONE);
			h.value.setVisibility(View.VISIBLE);
			h.value.setText(rfi.getValue().get(position));
		}
//		System.out.println(rfi.getName().get(position)+"&()"+rfi.getValue().get(position));
		return convertView;
	}
	class ViewHolder{
		TextView name,value,textView3;
	}

}
