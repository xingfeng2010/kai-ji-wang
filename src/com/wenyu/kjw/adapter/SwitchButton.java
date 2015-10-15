package com.wenyu.kjw.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.wenyu.kaijiw.R;

public class SwitchButton extends View {
	
	public static final int DEFAULT_WIDTH = 100;
	public static final int DEFAULT_HEIGTH = 45;
	public static final int PER_POST_TIME = 20;
	public static final int CLICK = 0;
	public static final int LEFT  = 1;
	public static final int RIGHT  = 2;
	
	private int mSelectBg;
	private int mUnSelectBg;
	private int mBorderColor;
	private int mSelectCirlceColor;
	private int mUnSelectCircleColor;
	private boolean isChecked;
	private Paint mPaint;
	private int mWidth;
	private int mHeight;
	private int mClickTimeout;  
	private int mTouchSlop; 
	private float mAnimMove;
	private final float VELOCITY = 350;
	private float firstDownX;
	private float firstDownY;
	private float lastDownX;
	private int mCriclePostion;
	private int mStartCriclePos;
	private int mEndCirclePos;
	private boolean isScroll;
	private int status;
	private Handler mHander;
	private AnimRunnable mAnim;

	private OnCheckChangeListener mOnCheckChangeListener;
	
	
	public interface OnCheckChangeListener {
		
		void OnCheck(SwitchButton switchButton,boolean isChecked);
	}

	public SwitchButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		Resources res = getResources();
		int defaultSelectBg = res.getColor(R.color.default_switch_button_select_bg);
		int defaultUnSelectBg = res.getColor(R.color.default_switch_button_unselect_bg);
		int defaultBorderColor = res.getColor(R.color.default_switch_button_border_color);
		int defaultSelectCircleColor = res.getColor(R.color.default_switch_button_select_color);
		int defaultUnSelectCircleColor = res.getColor(R.color.default_switch_button_unselect_color);
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.SwitchButton);
		mSelectBg = a.getColor(R.styleable.SwitchButton_select_bg, defaultSelectBg);
		mUnSelectBg = a.getColor(R.styleable.SwitchButton_unselect_bg, defaultUnSelectBg);
		mBorderColor = a.getColor(R.styleable.SwitchButton_select_border_color, defaultBorderColor);
		mSelectCirlceColor = a.getColor(R.styleable.SwitchButton_select_cricle_color, defaultSelectCircleColor);
		mUnSelectCircleColor = a.getColor(R.styleable.SwitchButton_unselect_cricle_color, defaultUnSelectCircleColor);
		isChecked = a.getBoolean(R.styleable.SwitchButton_isChecked,false);
		a.recycle();
		initView(context);
	}

	private void initView(Context context) {
		
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		final float density = getResources().getDisplayMetrics().density;  
		mAnimMove = (int) (VELOCITY * density + 0.5f) / 150; //自动滚动速度
        mClickTimeout = ViewConfiguration.getPressedStateDuration()  
                + ViewConfiguration.getTapTimeout();  //点击时间
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();//触摸滚动Slop
        mHander = new Handler();
        mAnim = new AnimRunnable();
	}
	
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		mPaint.setColor(mBorderColor);
		mPaint.setStyle(Paint.Style.STROKE);
		RectF rect = new RectF(0, 0, mWidth, mHeight);
		canvas.drawRoundRect(rect, mHeight/2, mHeight/2, mPaint);
		mPaint.setColor(isChecked ? mSelectBg :mUnSelectBg);
		RectF innerRect = new RectF(1, 1, mWidth -1, mHeight -1);
		canvas.drawRoundRect(innerRect, mHeight/2 -1, mHeight/2 -1, mPaint);
		mPaint.setColor(isChecked ? mSelectCirlceColor : mUnSelectCircleColor);
		mPaint.setStyle(Paint.Style.FILL);
		canvas.drawCircle(mCriclePostion, mHeight/2, mHeight/2 -1, mPaint);
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(isScroll){
				return false;
			}
			firstDownX = x;
			firstDownY = y;
			lastDownX = x;
			mCriclePostion = isChecked ? mEndCirclePos : mStartCriclePos;
			break;
		case MotionEvent.ACTION_MOVE:
			float delaX = x - lastDownX;
			setCriclePositon(delaX);
			lastDownX  = x;
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			float totalX = x - firstDownX;
			float totalY = y - firstDownY;
			float time = event.getEventTime() - event.getDownTime();
			if(totalX < mTouchSlop && totalY <mTouchSlop && time < mClickTimeout){
				status = CLICK;
				startAutoScroll();
			}else{
				delaX = x - lastDownX;
				setCriclePositon(delaX);
				status = mCriclePostion < mWidth / 2 ? LEFT :RIGHT;
				startAutoScroll();
			}
			break;
		}
		return true;
	}
	
	//开始自动滚动
	private void startAutoScroll(){
		
		isScroll = true;
		mHander.postDelayed(mAnim, PER_POST_TIME);
		
	}
	
	
	class AnimRunnable implements Runnable{

		@Override
		public void run() {
			
			mHander.postDelayed(this, PER_POST_TIME);
			moveView();	
		}
		//移动
		private void moveView() {
			
			if((status == CLICK && isChecked) || status == LEFT){
				mCriclePostion -= mAnimMove;
				if(mCriclePostion < mHeight /2){
					mCriclePostion = mHeight/2;
					stopView(false);
				}
			}else if((status == CLICK && !isChecked) || status == RIGHT) {
				
				mCriclePostion += mAnimMove;
				if(mCriclePostion > mWidth -mHeight /2){
					mCriclePostion = mWidth -mHeight /2;
					stopView(true);
				}
			}
			invalidate();
			
		}
		//停止移动View
		private void stopView(boolean endChecked) {
			
			mHander.removeCallbacks(mAnim);
			isScroll = false;
			isChecked = endChecked;
			//回调监听事件
			if(mOnCheckChangeListener != null){
				mOnCheckChangeListener.OnCheck(SwitchButton.this, isChecked);
			}
		}
		
	}
	
	//设置圆心的位置
	private synchronized void setCriclePositon(float delaX){
		
		int pos = (int) (mCriclePostion + delaX);
		if(pos < mHeight / 2){
			mCriclePostion =  mHeight / 2;
		}else if(pos > mWidth - mHeight /2){
			mCriclePostion =  mWidth - mHeight /2;
		}else{
			mCriclePostion = pos;
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		if(widthMode == MeasureSpec.UNSPECIFIED || widthMode == MeasureSpec.AT_MOST){
			mWidth = DEFAULT_WIDTH;
		}else{
			mWidth = MeasureSpec.getSize(widthMeasureSpec);
		}
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if(heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST){
			mHeight = DEFAULT_HEIGTH;
		}else{
			mHeight = MeasureSpec.getSize(heightMeasureSpec);
		}
		mStartCriclePos = mHeight / 2;
		mEndCirclePos = mWidth - mHeight /2;
		mCriclePostion = isChecked ? mEndCirclePos :mStartCriclePos; 
		setMeasuredDimension(mWidth, mHeight);
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		
		this.isChecked = isChecked;
		invalidate();
	}



	public void setOnCheckChangeListener(OnCheckChangeListener onCheckChangeListener) {
		
		this.mOnCheckChangeListener = onCheckChangeListener;
	}
	
	
}
