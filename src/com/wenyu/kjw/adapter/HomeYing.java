package com.wenyu.kjw.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mywork.util.AsyncImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.wenyu.Data.Find_yingshi;
import com.wenyu.kaijiw.R;

public class HomeYing  extends BaseAdapter{
	private  List<Find_yingshi> strs;
	private Context context;
	private AsyncImageLoader mAsyncImageLoader;
	public HomeYing(List<Find_yingshi> strs, Context context) {
		super();
		this.strs = strs;
		this.context = context;

	}
	
	public void setData(List<Find_yingshi> strs){
		this.strs = strs;
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
	 *getView������������Ҫ���ġ�
	 * */
	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		ViewHolder vh;
		if(convertView ==null){
			vh = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.drop_dowm_box
					, null);
			vh.ps = (TextView) convertView.findViewById(R.id.ps);
			vh.text = (TextView) convertView.findViewById(R.id.textView1);
			vh.image = (ImageView) convertView.findViewById(R.id.imageView);
			vh.image.setColorFilter(Color.GRAY,PorterDuff.Mode.MULTIPLY);
			
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		Find_yingshi str =strs.get(position);

	    vh.text.setText(str.getName());
	    vh.text.setTextSize(20);
		vh.ps.setText(" "+str.getCountV()+"�����"+" ");
		final String imageUrl = str.getPicture();
		//��ImageView���ñ�ǣ�ͼƬ��url�����Է�ֹͼƬ���ػ���
		vh.image.setTag(imageUrl);
		ImageLoader imageloader = ImageLoader.getInstance();
		imageloader.init(ImageLoaderConfiguration.createDefault(context));
		
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		//���ؿ�ʼĬ�ϵ�ͼƬ
		.showStubImage(R.drawable.z1)
		//urlΪ��ʱ��ʾ��ͼƬ
		.showImageForEmptyUri(R.drawable.z2)
		.cacheInMemory(true)
		.cacheOnDisc(true)
//		.cacheOnDisc(false)
		//����ͼƬ����������ʾͼƬ
		.showImageOnFail(R.drawable.z3)
		.build();
		
		imageloader.displayImage(imageUrl, vh.image,defaultOptions);

		return convertView;
	}
	class ViewHolder{
		TextView ps,text;
		ImageView image;
	}
}
