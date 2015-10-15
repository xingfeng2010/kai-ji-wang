package com.wenyu.kjw.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
//import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wenyu.kaijiw.R;

public class TextAdapter extends ArrayAdapter<String> {

	private Context mContext;
	private List<String> mListData;
	private String[] mArrayData;
	private int selectedPos = -1;// 选择text的id
	private String selectedText = "";// 选中的Text
	private int normalDrawbleId;// 选中背景选择器id
	private Drawable selectedDrawble;// 选中的背景对�?
	private float textSize;// 文字大小
	private OnClickListener onClickListener;// 点击监听
	private OnItemClickListener mOnItemClickListener;

	/**
	 * 有二级菜单的
	 * 
	 * @param context
	 * @param listData
	 * @param sId
	 * @param nId
	 */
	public TextAdapter(Context context, List<String> listData, int sId, int nId) {
		super(context, R.string.no_data, listData);
		mContext = context;
		mListData = listData;
		selectedDrawble = mContext.getResources().getDrawable(sId);
		normalDrawbleId = nId;

		init();
	}

	/**
	 * 无二级菜单的
	 * 
	 * @param context
	 * @param arrayData
	 * @param sId
	 * @param nId
	 */
	public TextAdapter(Context context, String[] arrayData, int sId, int nId) {
		super(context, R.string.no_data, arrayData);
		mContext = context;
		mArrayData = arrayData;
		selectedDrawble = mContext.getResources().getDrawable(sId);
		normalDrawbleId = nId;
		init();
	}

	private void init() {
		onClickListener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				selectedPos = (Integer) view.getTag();
				setSelectedPosition(selectedPos);
				// setSelectedPositionNoNotify(selectedPos);
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(view, selectedPos);
				}
			}
		};
	}

	/**
	 * 设置选中的position,并�?知列表刷�?
	 */
	public void setSelectedPosition(int pos) {
		if (mListData != null && pos < mListData.size()) {
			selectedPos = pos;
			selectedText = mListData.get(pos);
//			selectedText.
			notifyDataSetChanged();
		} else if (mArrayData != null && pos < mArrayData.length) {
			selectedPos = pos;
			selectedText = mArrayData[pos];
			notifyDataSetChanged();
		}
	}

	/**
	 * 设置选中的position,但不通知刷新
	 */
	public void setSelectedPositionNoNotify(int pos) {
		selectedPos = pos;
		if (mListData != null && pos < mListData.size()) {
			selectedText = mListData.get(pos);
		} else if (mArrayData != null && pos < mArrayData.length) {
			selectedText = mArrayData[pos];
		}
	}

	/**
	 * 获取选中的position
	 */
	public int getSelectedPosition() {
		if (mArrayData != null && selectedPos < mArrayData.length) {
			return selectedPos;
		}
		if (mListData != null && selectedPos < mListData.size()) {
			return selectedPos;
		}

		return -1;
	}

	/**
	 * 设置列表字体大小
	 */
	public void setTextSize(float tSize) {
		textSize = tSize;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view;
		if (convertView == null) {
			view = (TextView) LayoutInflater.from(mContext).inflate(
					R.layout.choose_item, parent, false);
		} else {
			view = (TextView) convertView;
		}
		view.setTag(position);
		String mString = "";
		if (mListData != null) {
			if (position < mListData.size()) {
				mString = mListData.get(position);
			}
		} else if (mArrayData != null) {
			if (position < mArrayData.length) {
				mString = mArrayData[position];
			}
		}
//		if (mString.contains("不限"))
//			view.setText("不限");
//		else
			view.setText(mString);
		view.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

		if (selectedText != null && selectedText.equals(mString)) {
			view.setBackgroundDrawable(selectedDrawble);// 设置选中的图�?
			view.setTextColor(Color.RED);
		} else {
			view.setTextColor(Color.BLACK);
			view.setBackgroundDrawable(mContext.getResources().getDrawable(
					normalDrawbleId));// 设置选中状�?背景图片
		}
		view.setPadding(20, 0, 0, 0);
		view.setOnClickListener(onClickListener);
		return view;
	}

	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}

	/**
	 * 重新定义菜单选项单击接口
	 */
	public interface OnItemClickListener {
		public void onItemClick(View view, int position);
		public void onItemClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3);
	}

	

}
