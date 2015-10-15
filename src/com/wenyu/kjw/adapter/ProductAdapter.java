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
import com.wenyu.Data.ProductData;
import com.wenyu.kaijiw.R;

public class ProductAdapter extends BaseAdapter {
	private List<ProductData> pcs;
	private Context context;

	public ProductAdapter(List<ProductData> pcs, Context context) {
		super();
		this.pcs = pcs;
		this.context = context;
	}

	@Override
	public int getCount() {
		if(pcs==null){
			return 0;
		}
		return pcs.size();
	}

	@Override
	public Object getItem(int arg0) {
		if(pcs.size()<=0){
			return 0;
		}else{
			return pcs.get(arg0);
		}
			
		}
	

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		ViewHolder h;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.products, null);
			h = new ViewHolder();
			h.img = (ImageView) convertView.findViewById(R.id.imageView1);
			h.name = (TextView) convertView.findViewById(R.id.textView1);
			h.value = (TextView) convertView.findViewById(R.id.textView2);
			convertView.setTag(h);
		}else{
			h = (ViewHolder)convertView.getTag();
		}
		h.name.setText(pcs.get(position).getName()+"");
		h.value.setText(pcs.get(position).getStorename()+"");
		final String imageUrl = pcs.get(position).getImage();
		//给ImageView设置标记（图片的url），以防止图片加载混乱
		h.img.setTag(imageUrl);
		ImageLoader imageloader = ImageLoader.getInstance();
		imageloader.init(ImageLoaderConfiguration.createDefault(context));
		imageloader.clearMemoryCache();
		imageloader.clearDiscCache();
		
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		//加载开始默认的图片
		.showStubImage(R.drawable.ic_empty)
		//url为空时显示该图片
		.showImageForEmptyUri(R.drawable.filmonimg)
		.cacheInMemory(true)
		.cacheOnDisc(true)
//		.cacheOnDisc(false)
		//加载图片出现问题显示图片
		.showImageOnFail(R.drawable.ic_error)
		.build();
		
		imageloader.displayImage(imageUrl, h.img,defaultOptions);
		
		return convertView;
	}
	class ViewHolder{
		ImageView img;
		TextView name,value;
	}
}
