package com.wenyu.kjw.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wenyu.Data.Find_filmcompanyItem;
import com.wenyu.kaijiw.R;

public class Find_FilmCompanyAdapter  extends BaseAdapter{
	private  List<Find_filmcompanyItem> strs;
	private Context context;
	private TextView text;
	private ViewHolder vh;

	public Find_FilmCompanyAdapter(List<Find_filmcompanyItem> strs, Context context) {
		super();
		this.strs = strs;
		this.context = context;

	}

	@Override
	public int getCount() {
		if(strs.size()==0){
			return 0;
		}
		return strs.size();
	}

	@Override
	public Object getItem(int arg0) {
		if(strs.size()==0){
			return 0;
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

			if(position%2==0){
				convertView = LayoutInflater.from(context).inflate(R.layout.filmcompayadpter
						, null);
			}else {
				convertView = LayoutInflater.from(context).inflate(R.layout.filmcompanyadapger_grary
						, null);
			}
			vh.text1 = (TextView) convertView.findViewById(R.id.textView1);
			vh.text2 = (TextView) convertView.findViewById(R.id.textView2);
			vh.text3 = (TextView) convertView.findViewById(R.id.textView3);
			convertView.setTag(vh);
		}else{
			vh= (ViewHolder) convertView.getTag();
		}
		Find_filmcompanyItem str =strs.get(position);
		vh.text1.setText(str.getProject());
		vh.text2.setText(str.getAddress());
		vh.text3.setText(str.getTime());
		return convertView;
	}
	class ViewHolder{
		TextView text1,text2,text3;
	}
}
