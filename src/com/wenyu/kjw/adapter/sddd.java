package com.wenyu.kjw.adapter;
import java.util.List;

import cache.loader.ImageWorker;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.example.mywork.util.AsyncImageLoader;
import com.example.mywork.util.AsyncImageLoader.ImageCallback;
import com.wenyu.Data.Find_yingshi;
import com.wenyu.Utils.BitmapImage;
import com.wenyu.kaijiw.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class sddd extends BaseAdapter {
	Context context;
	List<Find_yingshi> list;
	private AsyncImageLoader mAsyncImageLoader;
	RequestQueue mQueue;
	public sddd(Context context, List<Find_yingshi> list) {
		super();
		this.context = context;
		this.list = list;
		
	}

	@Override
	public int getCount() {
		if(list==null){
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
		mQueue = Volley.newRequestQueue(context);
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
		final String imageUrl = list.get(position).getPicture();
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
		TextView name;
	}

}
