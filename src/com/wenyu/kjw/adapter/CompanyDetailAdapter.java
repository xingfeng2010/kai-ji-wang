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
	 *getView������������Ҫ���ġ�
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
		//��ImageView���ñ�ǣ�ͼƬ��url�����Է�ֹͼƬ���ػ���
		if(!imageUrl.trim().equals("")){
		vh.image.setTag(imageUrl);
		mAsyncImageLoader=new AsyncImageLoader(context);
		Bitmap image = mAsyncImageLoader.loadImage(imageUrl, new ImageCallback() {
			//ֻ�е�ͼƬͨ��������صõ�ʱ���л��ᴥ���˷���
			@Override
			public void onImageLoaded(Bitmap image, String url) {
				//parent��convertView�ĸ�ViewҲ����ListView��findViewWithTag�������ݱ�������ҵ���Ӧ��View
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
