package com.wenyu.kjw.adapter;
import java.util.List;

import com.android.volley.RequestQueue;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.wenyu.Data.Myhome;
import com.wenyu.kaijiw.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyhomeAdapter extends BaseAdapter {
	Context context;
	private List<Myhome> list;
	private DisplayImageOptions defaultOptions;
	private ImageLoader imageloader;
	public MyhomeAdapter(Context context, List<Myhome> list) {
		super();
		this.context = context;
		this.list = list;
		imageloader = ImageLoader.getInstance();
		defaultOptions = new DisplayImageOptions.Builder()
		//���ؿ�ʼĬ�ϵ�ͼƬ
		.showStubImage(R.drawable.z2)
		//urlΪ��ʱ��ʾ��ͼƬ
		.showImageForEmptyUri(R.drawable.z1)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		//����ͼƬ����������ʾͼƬ
		.showImageOnFail(R.drawable.z3)
		.displayer(new RoundedBitmapDisplayer(10))
		.build();

	}
	public void setData(List<Myhome> list){
		this.list = list;
	}
	@Override
	public int getCount() {
		if(list.size()==0){
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

		final String imageUrl = list.get(position).getImag();
		//��ImageView���ñ�ǣ�ͼƬ��url�����Է�ֹͼƬ���ػ���
		h.img.setTag(imageUrl);
		ImageLoader imageloader = ImageLoader.getInstance();
		imageloader.init(ImageLoaderConfiguration.createDefault(context));

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.cacheOnDisc(true)
		//���ؿ�ʼĬ�ϵ�ͼƬ
		.showStubImage(R.drawable.z1)
		//urlΪ��ʱ��ʾ��ͼƬ
		.showImageForEmptyUri(R.drawable.z2)
		
		//����ͼƬ����������ʾͼƬ
		.showImageOnFail(R.drawable.z3)
		.build();

		imageloader.displayImage(imageUrl, h.img,defaultOptions);

		return convertView;
	}
	class ViewHolder{
		ImageView img;
		TextView name;
	}

}
