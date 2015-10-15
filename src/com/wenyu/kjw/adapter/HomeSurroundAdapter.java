package com.wenyu.kjw.adapter;

import java.util.List;

import com.wenyu.Data.HomeSurround;
import com.wenyu.kaijiw.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeSurroundAdapter  extends BaseAdapter{
	private  List<HomeSurround> strs;
	private Context context;
	private ViewHolder vh;
	private String image;
	
	public HomeSurroundAdapter(List<HomeSurround> strs, Context context,
			String image) {
		super();
		this.strs = strs;
		this.context = context;
		this.image = image;
	}

	public HomeSurroundAdapter(List<HomeSurround> strs, Context context) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.surrond
					, null);
			vh.name = (TextView) convertView.findViewById(R.id.textView1);
			vh.value = (TextView) convertView.findViewById(R.id.textView2);
			vh.image = (ImageView) convertView.findViewById(R.id.imageView1);
			convertView.setTag(vh);
		}else{
			vh= (ViewHolder) convertView.getTag();
		}
		vh.name.setText(strs.get(position).getName());
		vh.image.setImageResource(Integer.parseInt(image));
		vh.value.setText(strs.get(position).getValue());
		return convertView;
	}
	class ViewHolder{
		TextView name,value;
		ImageView image;
	}
}
