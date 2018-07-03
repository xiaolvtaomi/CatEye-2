package com.cicinnus.cateye.module.more;


import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SlidingDrawer;

public class MySlidingDrawer extends SlidingDrawer {
	
	private int mHandleId = 0; // 抽屉行为控件ID
	private int[] mTouchableIds = null; // Handle 部分其他控件ID

	public int[] getTouchableIds() {
		return mTouchableIds;
	}

	public void setTouchableIds(int[] mTouchableIds) {
		this.mTouchableIds = mTouchableIds;
	}

	public int getHandleId() {
		return mHandleId;
	}

	public void setHandleId(int mHandleId) {
		this.mHandleId = mHandleId;
	}

	public MySlidingDrawer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MySlidingDrawer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/*
	 * 获取控件的屏幕区域
	 */
	public Rect getRectOnScreen(View view) {
		Rect rect = new Rect();
		int[] location = new int[2];
		View parent = view;
		if (view.getParent() instanceof View) {
			parent = (View) view.getParent();
		}
		parent.getLocationOnScreen(location);
		view.getHitRect(rect);
		rect.offset(location[0], location[1]);

		return rect;
	}

	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN && mHandleId != 0) {
			Log.i("MySlidingDrawer", "Hit handle===========7777777777777");
			if (onMySlidingDrawerClick != null) {
				onMySlidingDrawerClick.onMySlidingDrawerClick();
			}
		}
		
//		// 触摸位置转换为屏幕坐标
//		int[] location = new int[2];
//		int x = (int) event.getX();
//		int y = (int) event.getY();
//		this.getLocationOnScreen(location);
//		x += location[0];
//		y += location[1];
//
//		// handle部分独立按钮
//		if (mTouchableIds != null) {
//			for (int id : mTouchableIds) {
//				View view = findViewById(id);
//				Rect rect = getRectOnScreen(view);
//				if (rect.contains(x, y)) {
//					Log.i("MySlidingDrawer on touch", String.format(
//							"Action=%d Button=%s", event.getAction(),
//							((Button) view).getText().toString()));
//					// return
//					// boolean result = view.dispatchTouchEvent(event);
//					// Log.i("MySlidingDrawer dispatchTouchEvent", "" + result);
//					return false;
//				}
//			}
//		}
//
//		// 抽屉行为控件
//		if (event.getAction() == MotionEvent.ACTION_DOWN && mHandleId != 0) {
//			View view = findViewById(mHandleId);
//			Log.i("MySlidingDrawer on touch", String.format("%d,%d", x, y));
//			Rect rect = getRectOnScreen(view);
//			Log.i("MySlidingDrawer handle screen rect", String
//					.format("%d,%d %d,%d", rect.left, rect.top, rect.right,
//							rect.bottom));
//			if (rect.contains(x, y)) {// 点击抽屉控件时交由系统处理
//				Log.i("MySlidingDrawer", "Hit handle===========7777777777777");
//				if (onMySlidingDrawerClick != null) {
//					onMySlidingDrawerClick.onMySlidingDrawerClick();
//				}
//			} else {
//				return false;
//			}
//		}
//
//		if (event.getAction() == MotionEvent.ACTION_UP) {
//			View view2 = findViewById(mHandleId);
//			Rect rect = getRectOnScreen(view2);
//			Log.i("MySlidingDrawer", "Hit handle =====444444444444444444");
//			if (rect.contains(x, y)) {
//				Log.i("MySlidingDrawer", "Hit handle ==================8888");
//			} else {
//				return false;
//			}
//		}

		return super.onInterceptTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
	private OnMySlidingDrawerClick onMySlidingDrawerClick;

	public interface OnMySlidingDrawerClick {
		public void onMySlidingDrawerClick();
	}

	public void setOnMySlidingDrawerClick(
			OnMySlidingDrawerClick onMySlidingDrawerClick) {
		this.onMySlidingDrawerClick = onMySlidingDrawerClick;
	}
}


