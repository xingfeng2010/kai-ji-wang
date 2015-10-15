package com.wenyu.kjw.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class SideBar extends View {
	// data
	// 触摸事件
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	// 26个字�?
	public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
			"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
			"#" };
	private int mChosenIndex = -1;// 选中
	private Paint mPaint = new Paint();

	// ui
	private TextView mTextDialog;

	public void setTextView(TextView mTextDialog) {
		this.mTextDialog = mTextDialog;
	}

	public void setTextDialogDismiss() {
		mChosenIndex = -1;
		invalidate();

		if (mTextDialog != null) {
			mTextDialog.setVisibility(View.INVISIBLE);
		}
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SideBar(Context context) {
		super(context);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// 获取焦点改变背景颜色.
		int height = getHeight();// 获取对应高度
		int width = getWidth(); // 获取对应宽度
		int singleHeight = height / b.length;// 获取每一个字母的高度

		for (int i = 0; i < b.length; i++) {
			mPaint.setColor(Color.rgb(33, 65, 98));
			// paint.setColor(Color.WHITE);
			mPaint.setTypeface(Typeface.DEFAULT_BOLD);
			mPaint.setAntiAlias(true);
			mPaint.setTextSize(20);

			// 选中的状�?
			if (i == mChosenIndex) {
				mPaint.setColor(Color.parseColor("#3399ff"));
				mPaint.setFakeBoldText(true);
			}

			// x坐标等于中间-字符串宽度的�?��.
			float xPos = width / 2 - mPaint.measureText(b[i]) / 2;
			float yPos = singleHeight * i + singleHeight;

			canvas.drawText(b[i], xPos, yPos, mPaint);
			mPaint.reset();// 重置画笔
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();// 点击y坐标
		final int oldChoose = mChosenIndex;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * b.length);// 点击y坐标�?��总高度的比例*b数组的长度就等于点击b中的个数.

		switch (action) {
		case MotionEvent.ACTION_UP:
			mChosenIndex = -1;
			invalidate();

			if (mTextDialog != null) {
				mTextDialog.setVisibility(View.INVISIBLE);
			}
			break;
		default:
			if (oldChoose != c) {
				if (c >= 0 && c < b.length) {
					if (listener != null) {
						listener.onTouchingLetterChanged(b[c]);
					}
					if (mTextDialog != null) {
						mTextDialog.setText(b[c]);
						mTextDialog.setVisibility(View.VISIBLE);
					}

					mChosenIndex = c;
					invalidate();
				}
			}

			break;
		}

		return true;
	}

	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}
}