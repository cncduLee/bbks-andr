package com.funger.bbks.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class SlidingMenu extends RelativeLayout {

	private SlidingView mSlidingView;
	private View mMenuView;
	private View mDetailView;

	public SlidingMenu(Context context) {
		super(context);
	}

	public SlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void addViews(View left, View center, View right) {
		setLeftView(left);
		setRightView(right);
		setCenterView(center);
	}

	@SuppressWarnings("deprecation")
	public void setLeftView(View view) {
		LayoutParams behindParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.FILL_PARENT);
		behindParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);//
		addView(view, behindParams);
		mMenuView = view;
	}

	@SuppressWarnings("deprecation")
	public void setRightView(View view) {
		LayoutParams behindParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.FILL_PARENT);
		behindParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);//
		addView(view, behindParams);
		mDetailView = view;
	}

	
	@SuppressWarnings("deprecation")
	public void setCenterView(View view) {
		LayoutParams aboveParams = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		mSlidingView = new SlidingView(getContext());
		mSlidingView.setView(view);
		addView(mSlidingView, aboveParams);
		mSlidingView.setMenuView(mMenuView);
		mSlidingView.setDetailView(mDetailView);
		mSlidingView.invalidate();
	}

	public void showLeftView() {
		mSlidingView.showLeftView();
	}

	public void showRightView() {
		mSlidingView.showRightView();
	}
}
