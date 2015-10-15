package com.example.test;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.wenyu.kaijiw.R;

public class ImageAdapter extends BaseAdapter {

	private ArrayList<String> data;
	private LayoutInflater inflater;
	private int width;
	private AsyncBitmapLoader asyncBitmapLoader;



	public ImageAdapter(Context context, ArrayList<String> data, int width) {
		this.data = data;
		this.inflater = LayoutInflater.from(context);
		this.width = width;
		this.asyncBitmapLoader = new AsyncBitmapLoader();
	}

	public ImageAdapter(Context context,ArrayList<String> data) {
		super();
		this.inflater = LayoutInflater.from(context);
		this.data = data;
		this.asyncBitmapLoader = new AsyncBitmapLoader();
	}

	@Override
	public int getCount() {
		int count = 0;
		if (data != null) {
			count = data.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		String item = null;
		if (data != null) {
			item = data.get(position%data.size());
		}
		return item;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.cell, null);
		}
		ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
		BitmapDrawable bitmapDrawable = asyncBitmapLoader.loadBitmap(
				data.get(position%data.size()), iv, new ImageCallBack() {
					@Override
					public void imageLoad(ImageView imageView,
							BitmapDrawable bitmap) {
						if(bitmap != null){
							imageView.setImageBitmap(bitmap.getBitmap());
						}

					}
				});
		if (bitmapDrawable != null) {
			iv.setImageBitmap(bitmapDrawable.getBitmap());
		} else {
			iv.setImageResource(R.drawable.z1);
		}
		return convertView;
	}

}
