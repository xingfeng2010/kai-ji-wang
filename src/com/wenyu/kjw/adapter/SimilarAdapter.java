package com.wenyu.kjw.adapter;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.mywork.util.AsyncImageLoader;
import com.example.mywork.util.AsyncImageLoader.ImageCallback;
import com.wenyu.Data.Similar;
import com.wenyu.kaijiw.R;

public class SimilarAdapter extends BaseAdapter {
	Context context;
	List<Similar> list;
	ViewHolder h;
	RequestQueue mQueue;
	private AsyncImageLoader mAsyncImageLoader;
	public SimilarAdapter(Context context, List<Similar> list) {
		super();
		this.context = context;
		this.list = list;
		mQueue = Volley.newRequestQueue(context);
	}

	@Override
	public int getCount() {
		if(list.size()==0){
			return 0 ;
		}
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		if(list.size()==0){
			return 0 ;
		}
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		h = new ViewHolder();	
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.similar, null);
			h.img = (ImageView) convertView.findViewById(R.id.ImageView01);
			h.name = (TextView) convertView.findViewById(R.id.textView2);
			h.regional = (TextView) convertView.findViewById(R.id.textView3);
			h.address = (TextView) convertView.findViewById(R.id.textView4);
			convertView.setTag(h);
		}else{
			h = (ViewHolder)convertView.getTag();
		}
		h.name.setText(list.get(position).getName());
		h.regional.setText(list.get(position).getRegional());
		h.address.setText(list.get(position).getAddress());
		final String imageUrl = list.get(position).getImage();
		//给ImageView设置标记（图片的url），以防止图片加载混乱
		h.img.setTag(imageUrl);
		mAsyncImageLoader=new AsyncImageLoader(context);
		Bitmap image = mAsyncImageLoader.loadImage(imageUrl, new ImageCallback() {
			//只有当图片通过网络加载得到时才有机会触发此方法
			@Override
			public void onImageLoaded(Bitmap image, String url) {
				//parent即convertView的父View也就是ListView，findViewWithTag（）根据标记重新找到对应的View
				ImageView picImage = (ImageView)parent.findViewWithTag(imageUrl);
				if(image!=null && picImage!=null){
					picImage.setImageBitmap(null);
					picImage.setImageBitmap(image);
				}else{
					Log.d("jjj---", "image: " + image + "     picImage:" + picImage);
				}
			}
		});
		if(image!=null){
			h.img.setImageBitmap(image);
		}else{
			h.img.setImageResource(R.drawable.ic_launcher);
		}
		return convertView;
	}
	class ViewHolder{
		ImageView img;
		TextView name,address,regional;
	}

}
