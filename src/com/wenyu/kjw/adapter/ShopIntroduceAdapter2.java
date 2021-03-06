package com.wenyu.kjw.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wenyu.Data.IntroData2;
import com.wenyu.kaijiw.R;

public class ShopIntroduceAdapter2 extends BaseAdapter {
	private List<IntroData2> shopintro;
	private Context context;
	private ViewHolder vh;
	public ShopIntroduceAdapter2(List<IntroData2> shopintro,Context context) {
		super();
		this.shopintro = shopintro;
		this.context = context;
	}

	@Override
	public int getCount() {
		return	shopintro.size();
	}

	@Override
	public Object getItem(int arg0) {
		return (shopintro.size()==0)?0:shopintro.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return (shopintro.size()==0)?0:arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		vh = new ViewHolder();
		if(convertView ==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.shopintro
					, null);
			vh.text2 = 	(TextView) convertView.findViewById(R.id.textView1);
			vh.text3 = 	(TextView) convertView.findViewById(R.id.textView2);
			convertView.setTag(vh);
		}else{
		vh=	(ViewHolder) convertView.getTag();
		}
		 
		vh.text2.setText(shopintro.get(position).Name);
		vh.text3.setText(shopintro.get(position).Value);
		return convertView;
	}
	class ViewHolder{
		TextView text2,text3;
	}
	}


