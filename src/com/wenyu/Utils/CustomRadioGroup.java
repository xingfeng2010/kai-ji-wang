package com.wenyu.Utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wenyu.kaijiw.R;



/**
 * 定制的 组件 描述： 横向排列N个条目组件 关联Resource: layout: custom_radio_button.xml drawable:
 * page_indicator_focused.png
 */

public class CustomRadioGroup extends LinearLayout {
	public static CustomRadioGroup sSingleton;

	/**
	 * IMAGE/TEXT : 条目的图片/文字 START/DIF : 初始值/和目标的差值 R/G/B ： 目标颜色RGB格式下的R/G/B
	 */
	private static final int TEXT_START_COLOR = Color.GRAY, END_COLOR = Color
			.parseColor("#45c01a"), TEXT_START_R = Color.red(TEXT_START_COLOR),
			TEXT_START_G = Color.green(TEXT_START_COLOR), TEXT_START_B = Color
					.blue(TEXT_START_COLOR), TEXT_DIF_R = Color.red(END_COLOR)
					- TEXT_START_R, TEXT_DIF_G = Color.green(END_COLOR) - TEXT_START_G,
			TEXT_DIF_B = Color.blue(END_COLOR) - TEXT_START_B;

	// 相关的资源ID：
	private final int ID_LAYOUT = R.layout.custom_radio_button,
			ID_IMAGE_TOP = R.id.custom_radio_button_image_top,
			ID_IMAGE_BOTTOM = R.id.custom_radio_button_image_botom,
			ID_TEXT = R.id.custom_radio_button_text,
			ID_NEWS = R.id.custom_radio_button_news;

	// 条目变更监听
	private OnItemChangedListener mOnItemChangedListener;

	// 条目的LinearLayout.LayoutParams
	private LayoutParams mItemLayoutParams = new LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
	private LayoutInflater mInflater;

	// 当前选择的条目
	private int mCheckedIndex = 0;

	// 条目列表
	private List<RadioButton> mRadioBtnsList = new ArrayList<CustomRadioGroup.RadioButton>();

	public CustomRadioGroup(Context c) {
		super(c);
		init();
	}

	public CustomRadioGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * 构造函数里适用的初始化部分
	 */
	private void init() {
		sSingleton = this;
		mInflater = LayoutInflater.from(getContext());
		mItemLayoutParams.weight = 1;
		setOrientation(HORIZONTAL);
	}

	/**
	 * @param f
	 *            颜色渐变参考值。
	 */
	private static int getNewColor(float f) {
		int newR, newG, newB;
		newR = (int) (TEXT_DIF_R * f) + TEXT_START_R;
		newG = (int) (TEXT_DIF_G * f) + TEXT_START_G;
		newB = (int) (TEXT_DIF_B * f) + TEXT_START_B;
		return Color.rgb(newR, newG, newB);
	}

	/**
	 * 添加条目
	 * 
	 * @param unSelected
	 *            没有选中时的图片
	 * @param selected
	 *            选中时的图片
	 * @param text
	 *            文本内容
	 */
	public void addItem(int unSelected, int selected, String text) {
		RadioButton radioBtn = new RadioButton(unSelected, selected, text);
		final int i = mRadioBtnsList.size();

		radioBtn.mView.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				setCheckedIndex(i);
			}
		});

		addView(radioBtn.mView);
		mRadioBtnsList.add(radioBtn);
	}

	/**
	 * 获取选中的条目索引
	 */
	public int getCheckedIndex() {
		return mCheckedIndex;
	}

	/**
	 * 两个item 改变透明度
	 * 
	 * @param leftIndex
	 *            左边的条目索引
	 * @param rightIndex
	 *            右边的条目索引
	 * @param alpha
	 *            [0,1)透明度
	 */
	public void itemChangeChecked(int leftIndex, int rightIndex, float alpha) {
		if (leftIndex < 0 || leftIndex >= mRadioBtnsList.size() || rightIndex < 0
				|| rightIndex >= mRadioBtnsList.size()) {
			return;
		}

		RadioButton a = mRadioBtnsList.get(leftIndex);
		RadioButton b = mRadioBtnsList.get(rightIndex);
		if(hasHoneycomb()){
			a.mTopImageView.setAlpha(alpha);
			a.mBottomImageView.setAlpha(1 - alpha);
			b.mTopImageView.setAlpha(1 - alpha);
			b.mBottomImageView.setAlpha(alpha);
		}
		
		int aColor = getNewColor(1 - alpha);
		int bColor = getNewColor(alpha);

		a.mTextView.setTextColor(aColor);
		b.mTextView.setTextColor(bColor);
	}

	public void itemChangeChecked(int index, float alpha) {
		if (index < 0 || index >= mRadioBtnsList.size()) {
			return;
		}

		RadioButton a = mRadioBtnsList.get(index);

		if(hasHoneycomb()){
			a.mTopImageView.setAlpha(1 - alpha);
			a.mBottomImageView.setAlpha(alpha);	
		}

		int aColor = getNewColor(alpha);

		a.mTextView.setTextColor(aColor);
	}

	/**
	 * 閫夋嫨鍒跺畾绱㈠紩鐨勬潯鐩?	 */
	public void setCheckedIndex(int index) {
		for (int i = 0; i < mRadioBtnsList.size(); i++) {
			if (i == index) {
				mRadioBtnsList.get(i).setChecked(true);
				mRadioBtnsList.get(i).mTextView.setTextColor(END_COLOR);
			} else {
				mRadioBtnsList.get(i).setChecked(false);
				mRadioBtnsList.get(i).mTextView.setTextColor(TEXT_START_COLOR);
			}
		}

		this.mCheckedIndex = index;

		if (this.mOnItemChangedListener != null) {
			mOnItemChangedListener.onItemChanged();
		}
	}

	/**
	 * 璁剧疆鎸囧畾绱㈠紩鐨勬潯鐩殑娑堟伅鏁伴噺
	 * 
	 * @param index
	 *            鏉＄洰鐨勭储寮?	 * @param count
	 *            娑堟伅鐨勬暟閲?	 */
	public void setItemNewsCount(int index, int count) {
		mRadioBtnsList.get(index).setNewsCount(count);
	}

	/**
	 * 璁剧疆鏉＄洰鍙樻洿鐩戝惉鍣?	 * 
	 * @param onItemChangedListener
	 */
	public void setOnItemChangedListener(OnItemChangedListener onItemChangedListener) {
		this.mOnItemChangedListener = onItemChangedListener;
	}

	/**
	 * 鑷畾涔夌殑RadioButton
	 */
	private class RadioButton {
		View mView; // 条目样式

		// 条目的图片
		ImageView mTopImageView;
		ImageView mBottomImageView;

		// 条目的文字，消息
		TextView mTextView;
		TextView mNewsTextView;

		public RadioButton(int unSelected, int selected, String string) {
			mView = mInflater.inflate(ID_LAYOUT, null);
			mTopImageView = (ImageView) mView.findViewById(ID_IMAGE_TOP);
			mBottomImageView = (ImageView) mView.findViewById(ID_IMAGE_BOTTOM);
			mTextView = (TextView) mView.findViewById(ID_TEXT);
			mNewsTextView = (TextView) mView.findViewById(ID_NEWS);

			mTopImageView.setImageResource(unSelected);
			mBottomImageView.setImageResource(selected);
			if(hasHoneycomb()){
				mTopImageView.setAlpha(1.0f);
				mBottomImageView.setAlpha(0.0f);
			}
			mTextView.setText(string);
			mNewsTextView.setVisibility(INVISIBLE);
			mView.setLayoutParams(mItemLayoutParams);
		}

		/**
		 * 设置消息数量
		 * 
		 * @param count
		 *            消息数量为0,不显示news的TextView，消息数量>0 显示news的TextView
		 */
		void setChecked(boolean checked) {
			if (checked) {
				if(hasHoneycomb()){
					mTopImageView.setAlpha(0.0f);
					mBottomImageView.setAlpha(1.0f);	
				}
			} else {
				if(hasHoneycomb()){
					mTopImageView.setAlpha(1.0f);
					mBottomImageView.setAlpha(0.0f);	
				}
			}
		}

		void setNewsCount(int count) {
			if (count < 0) {
				mNewsTextView.setVisibility(INVISIBLE);
			} else {
				RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) mNewsTextView
						.getLayoutParams();

				if (count == 0) {
					params.width = CommonUtil.dpToPx(getResources(), 10);
					params.height = CommonUtil.dpToPx(getResources(), 10);
					mNewsTextView.setText("");
				} else {
					params.width = CommonUtil.dpToPx(getResources(), 20);
					params.height = CommonUtil.dpToPx(getResources(), 20);
					mNewsTextView.setText(count + "");
				}

				mNewsTextView.setLayoutParams(params);
				mNewsTextView.setVisibility(VISIBLE);
			}
		}
	}


	public interface OnItemChangedListener {
		public void onItemChanged();
	}
	
	private boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
	}
}
