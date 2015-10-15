package com.wenyu.kjw.adapter;

import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.mywork.util.AsyncImageLoader;
import com.example.mywork.util.AsyncImageLoader.ImageCallback;
import com.wenyu.Data.Myhome;
import com.wenyu.Data.Similar;
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

public class CompanyDetailAdapter  extends BaseAdapter{
	private  List<Similar> strs;
	private Context context;
	private AsyncImageLoader mAsyncImageLoader;
	RequestQueue mQueue;
	public CompanyDetailAdapter(Context context, List<Similar> strs) {
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
			if(position%2==0){
				convertView = LayoutInflater.from(context).inflate(R.layout.detailcompanysadpter
						, null);
			}else{
				convertView = LayoutInflater.from(context).inflate(R.layout.detailcompany_grary
						, null);
			}
			
			vh.ps = (TextView) convertView.findViewById(R.id.textView1);
			vh.text = (TextView) convertView.findViewById(R.id.textView2);
			vh.text3 = (TextView) convertView.findViewById(R.id.textView3);
			vh.image = (ImageView) convertView.findViewById(R.id.imageView1);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		Similar str =strs.get(position);
		vh.ps.setText(str.getName());

		vh.text.setText(str.getRegional());
		vh.text3.setText(str.getAddress());
		
		final String imageUrl = str.getImage();
		//给ImageView设置标记（图片的url），以防止图片加载混乱
		if(!imageUrl.trim().equals("")){
		vh.image.setTag(imageUrl);
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
			vh.image.setImageBitmap(null);
			vh.image.setImageBitmap(image);
		}else{
			vh.image.setImageResource(R.drawable.ic_launcher);
		 }
		}
		return convertView;
	}
	class ViewHolder{
		TextView ps,text,text3;
		ImageView image;
	}
}
