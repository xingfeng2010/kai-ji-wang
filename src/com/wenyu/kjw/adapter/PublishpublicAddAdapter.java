package com.wenyu.kjw.adapter;

import java.util.List;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.wenyu.Data.Publishpublic;
import com.wenyu.kaijiw.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PublishpublicAddAdapter extends BaseAdapter {
	Context context;
	List<Publishpublic> list;
	public PublishpublicAddAdapter(Context context, List<Publishpublic> list) {
		super();
		this.context = context;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.childadd, null);
			h = new ViewHolder();
			h.img = (ImageView) convertView.findViewById(R.id.imageView1);
			h.name = (TextView) convertView.findViewById(R.id.textView1);
			h.value = (TextView) convertView.findViewById(R.id.textView2);
			h.add = (TextView) convertView.findViewById(R.id.text);
			convertView.setTag(h);
		}else{
			h = (ViewHolder)convertView.getTag();
		}
		h.name.setText(list.get(position).getName());
		final String imageUrl = list.get(position).getCoverimage();
		//��ImageView���ñ�ǣ�ͼƬ��url�����Է�ֹͼƬ���ػ���
		h.img.setTag(imageUrl);
		ImageLoader imageloader = ImageLoader.getInstance();
		imageloader.init(ImageLoaderConfiguration.createDefault(context));
//		imageloader.clearMemoryCache();
//		imageloader.clearDiscCache();
		
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		//���ؿ�ʼĬ�ϵ�ͼƬ
		.showStubImage(R.drawable.ic_empty)
		//urlΪ��ʱ��ʾ��ͼƬ
		.showImageForEmptyUri(R.drawable.filmonimg)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		
		//����ͼƬ����������ʾͼƬ
		.showImageOnFail(R.drawable.ic_error)
		.build();
		
		imageloader.displayImage(imageUrl, h.img,defaultOptions);
		
		return convertView;
	}
	class ViewHolder{
		ImageView img;
		TextView name,value,add;
	}

}
