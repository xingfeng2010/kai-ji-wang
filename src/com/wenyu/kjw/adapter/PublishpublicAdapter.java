package com.wenyu.kjw.adapter;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.wenyu.Data.Publishpublic;
import com.wenyu.kaijiw.R;

public class PublishpublicAdapter extends BaseAdapter {
	Context context;
	List<Publishpublic> list;
	public PublishpublicAdapter(Context context, List<Publishpublic> list) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.child, null);
			h = new ViewHolder();
			h.img = (ImageView) convertView.findViewById(R.id.imageView1);
			h.name = (TextView) convertView.findViewById(R.id.textView1);
			h.value = (TextView) convertView.findViewById(R.id.textView2);
			h.imageView2 = (ImageView) convertView.findViewById(R.id.imageView1);
			convertView.setTag(h);
		}else{
			h = (ViewHolder)convertView.getTag();
		}
		
		h.name.setText(list.get(position).getName());
//		h.value.setText(list.get(position).);
		h.imageView2.setImageResource(R.drawable.publish);
		final String imageUrl = list.get(position).getCoverimage();
		//给ImageView设置标记（图片的url），以防止图片加载混乱
		h.img.setTag(imageUrl);
		ImageLoader imageloader = ImageLoader.getInstance();
		imageloader.init(ImageLoaderConfiguration.createDefault(context));
//		imageloader.clearMemoryCache();
//		imageloader.clearDiscCache();
		
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		//加载开始默认的图片
		.showStubImage(R.drawable.ic_empty)
		//url为空时显示该图片
		.showImageForEmptyUri(R.drawable.filmonimg)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		//加载图片出现问题显示图片
		.showImageOnFail(R.drawable.ic_error)
		.build();
		
		imageloader.displayImage(imageUrl, h.img,defaultOptions);
		
		return convertView;
	}
	class ViewHolder{
		ImageView img,imageView2;
		TextView name,value;
	}

}
