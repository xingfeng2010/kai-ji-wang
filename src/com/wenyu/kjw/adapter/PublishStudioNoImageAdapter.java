package com.wenyu.kjw.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.wenyu.Data.Publishpublic;
import com.wenyu.kaijiw.R;

public class PublishStudioNoImageAdapter  extends BaseAdapter{
	private  List<Publishpublic> strs;
	private Context context;
	RequestQueue mQueue;
	public PublishStudioNoImageAdapter(List<Publishpublic> strs, Context context) {
		super();
		this.strs = strs;
		this.context = context;

	}

	@Override
	public int getCount() {
		if(strs.size()<=0){
			return 0;
		}
		return strs.size(); 
	}

	@Override
	public Object getItem(int arg0) {
		if(strs.size()<=0){
			return null;
		}
		return strs.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	/*
	 * (non-Javadoc)
	 *getView方法的数据需要更改。
	 * */
	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		mQueue = Volley.newRequestQueue(context);
		ViewHolder vh;
		if(convertView ==null){
			vh = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.publishstudio_noimageitem
					, null);
			vh.text = (TextView) convertView.findViewById(R.id.textView1);
			vh.image = (ImageView) convertView.findViewById(R.id.imageView1);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		Publishpublic str =strs.get(position);
	    vh.text.setText(str.getName());
	    vh.image.setImageResource(R.drawable.publish);
		return convertView;
	}
	class ViewHolder{
		TextView text;
		ImageView image;
	}
}
