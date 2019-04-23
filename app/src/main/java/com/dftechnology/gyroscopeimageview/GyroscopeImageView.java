package com.dftechnology.gyroscopeimageview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;


public class GyroscopeImageView extends ImageView {

	private double mScaleX;
	private double mScaleY;
	private float mLenX;
	private float mLenY;
	private int mOffsetX;
	private int mOffsetY;
	private float mLastX;
	private float mMatrixX;
	private boolean mEnabled;

	public GyroscopeImageView(Context context) {
		super(context);
		init();
	}

	public GyroscopeImageView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public GyroscopeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		setScaleType(ScaleType.CENTER_CROP);
	}

	@Override
	public void setScaleType(ScaleType scaleType) {
		super.setScaleType(ScaleType.CENTER_CROP);
	}

	public float getOffsetX() {
		return mOffsetX;
	}

	public float getOffsetY() {
		return mOffsetY;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		GyroscopeManager gyroscopeManager = GyroscopeManager.getInstance();

		if (gyroscopeManager != null) {
			gyroscopeManager.addView(this);
		}
	}

	/**
	 * # 1 OnTouch：针对整个布局
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {

			case MotionEvent.ACTION_DOWN:
				//按下
				mLastX = event.getX();
				//记录下
				updateMatrixX();
				disableSensor();
				break;
			case MotionEvent.ACTION_MOVE:

				//移动
				float moveX = event.getX();
				int diffX = (int) (mLastX - moveX);
				Log.i("GyroscopeImageView", "diffX:" + diffX + ",getMatrixX:" + getMatrixX());
				scroll(diffX);
				mLastX = moveX;
				break;
			case MotionEvent.ACTION_UP:
				//松开
				enableSensor();
				break;
		}
		return true;
	}

	private void updateMatrixX() {
		float[] rect = new float[9];
		getImageMatrix().getValues(rect);
		//横向上的缩放值
		mMatrixX = rect[0];
	}

	private void scroll(int diffX) {
		int scrollX = 0;
		if (diffX > 0) {//向左滑，窗口往右动
			if (getDrawable() != null) {
				if (getDrawable().getIntrinsicWidth() * mMatrixX - getMatrixX() - getWidth() - diffX > 0) {//此时说明还没有滑动到边界
					scrollX = diffX;
				} else {
					scrollX = (int) (getDrawable().getIntrinsicWidth() * mMatrixX - getMatrixX() - getWidth());
				}
			}
		} else if (diffX < 0) {//向右滑，窗口往左动
			if (getMatrixX() - Math.abs(diffX) > 0) {//此时说明够用
				scrollX = diffX;
			} else {
				//此时 需注意边界处理，防抖动
				scrollX = -(int) getMatrixX();
			}
		}
		scrollBy(scrollX, 0);
	}

	private float getMatrixX() {
		if (getDrawable() != null)
			return getScrollX() + getDrawable().getIntrinsicWidth() * mMatrixX / 2f - getWidth() / 2f;
		return 0;
	}


	private void enableSensor() {
		this.mEnabled = true;
	}

	private void disableSensor() {
		this.mEnabled = false;
	}


	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		GyroscopeManager gyroscopeManager = GyroscopeManager.getInstance();
		if (gyroscopeManager != null) {
			gyroscopeManager.removeView(this);
		}
	}

	public void update(double scaleX, double scaleY) {
		if (mEnabled) {
			mScaleX = scaleX;
			mScaleY = scaleY;
			mOffsetX = (int) (mLenX * mScaleX);
			mOffsetY = (int) (mLenY * mScaleY);
			scroll((-mOffsetX));
		} else {
			mOffsetX = (int) (1260.0 * scaleX);
			updateMatrixX();
			scroll((-mOffsetX));
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
		int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
		if (getDrawable() != null) {
			int drawableWidth = getDrawable().getIntrinsicWidth();
			int drawableHeight = getDrawable().getIntrinsicHeight();
			mLenX = Math.abs((drawableWidth - width) * 0.5f);
			mLenY = Math.abs((drawableHeight - height) * 0.5f);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (getDrawable() == null || isInEditMode()) {
			super.onDraw(canvas);
			return;
		}

		super.onDraw(canvas);
	}


	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}