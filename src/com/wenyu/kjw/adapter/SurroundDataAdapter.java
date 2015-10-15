package com.wenyu.kjw.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenyu.Data.SurroundData;
import com.wenyu.kaijiw.R;

public class SurroundDataAdapter  extends BaseAdapter{
	private  List<SurroundData> strs;
	private Context context;
	private ViewHolder vh;
	public SurroundDataAdapter(List<SurroundData> strs, Context context) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.surronddata
					, null);
			vh.image = (ImageView) convertView.findViewById(R.id.imageView);
			vh.text = (TextView) convertView.findViewById(R.id.textView1);
			
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		SurroundData str =strs.get(position);
		vh.image.setImageResource(str.getImage());
		vh.text.setText(str.getName());
		return convertView;
	}
	class ViewHolder{
		ImageView image;
		TextView text;
	}
}
