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
import com.wenyu.Data.Myhome;
import com.wenyu.kaijiw.R;

public class DetailsAdapter  extends BaseAdapter{
	private  List<Myhome> strs;
	private Context context;
	private AsyncImageLoader mAsyncImageLoader;
	RequestQueue mQueue;
	public DetailsAdapter(List<Myhome> strs, Context context) {
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
		// TODO Auto-generated method stub
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
			convertView = LayoutInflater.from(context).inflate(R.layout.detailsadpter
					, null);
			vh.ps = (TextView) convertView.findViewById(R.id.textView1);
			vh.text = (TextView) convertView.findViewById(R.id.textView2);
			vh.image = (ImageView) convertView.findViewById(R.id.imageView1);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		Myhome str =strs.get(position);
		vh.text.setText(str.getName());
		vh.ps.setText(str.getType());
		//		vh.image.setAlpha(0.5f);
		final String imageUrl = str.getImag();
		//给ImageView设置标记（图片的url），以防止图片加载混乱
		vh.image.setTag(imageUrl);
		mAsyncImageLoader=new AsyncImageLoader(context);
		Bitmap image = mAsyncImageLoader.loadImage(imageUrl, new ImageCallback() {
			//只有当图片通过网络加载得到时才有机会触发此方法
			@Override
			public void onImageLoaded(Bitmap image, String url) {
				//parent即convertView的父View也就是ListView，findViewWithTag（）根据标记重新找到对应的View
				ImageView picImage = (ImageView)parent.findViewWithTag(imageUrl);
				if(image!=null && picImage!=null){
					picImage.setImageBitmap(image);
				}else{
					Log.d("jjj---", "image: " + image + "     picImage:" + picImage);
				}
			}
		});
		if(image!=null){
			vh.image.setImageBitmap(null);
			vh.image.setImageBitmap(image);
		}else{
			vh.image.setImageResource(R.drawable.ic_launcher);
		}
		return convertView;
	}
	class ViewHolder{
		TextView ps,text;
		ImageView image;
	}
}
