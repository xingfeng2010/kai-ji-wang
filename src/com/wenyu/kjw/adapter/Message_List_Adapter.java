package com.wenyu.kjw.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wenyu.Data.MyMessage;
import com.wenyu.kaijiw.R;

public class Message_List_Adapter  extends BaseAdapter{
	private  List<MyMessage> strs;
	private Context context;
	private ViewHolder vh;
	public Message_List_Adapter(List<MyMessage> strs, Context context) {
		super();
		this.strs = strs;
		this.context = context;
		vh = new ViewHolder();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView ==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.message_list_item
					, null);
			vh.text = 	(TextView) convertView.findViewById(R.id.messageps);
			vh.message = (TextView) convertView.findViewById(R.id.messagedesc);
			convertView.setTag(vh);
		}else{
		vh =(ViewHolder) convertView.getTag();
		}
		MyMessage str =strs.get(position);
		vh.text.setText(str.getName());
		vh.message.setText(str.getDesc());
		return convertView;
	}
	class ViewHolder{
		TextView text;
		TextView message;
	}
}
