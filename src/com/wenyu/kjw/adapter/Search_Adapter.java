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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.wenyu.Data.SearchData;
import com.wenyu.Utils.BitmapImage;
import com.wenyu.kaijiw.R;

public class Search_Adapter  extends BaseAdapter{
	private  List<SearchData> strs;
	private Context context;
	private ViewHolder vh;
	RequestQueue mQueue;
	public Search_Adapter(List<SearchData> strs, Context context) {
		super();
		this.strs = strs;
		this.context = context;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return strs.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
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
		mQueue = Volley.newRequestQueue(context);
		vh = new ViewHolder();
		if(convertView ==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.drop_dowm_box
					, null);
			vh.text = 	(TextView) convertView.findViewById(R.id.ps);
			vh.image = (ImageView) convertView.findViewById(R.id.imageView);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		SearchData str =strs.get(position);
	//	vh.text.setText(str.getHistory()());
		ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapImage());
		ImageListener imageListener = ImageLoader.getImageListener(vh.image, R.drawable.ic_launcher, R.drawable.right);
	//	imageLoader.get(str.getImage(), imageListener);
		return convertView;
	}
	class ViewHolder{
		TextView text;
		ImageView image;
	}
}
