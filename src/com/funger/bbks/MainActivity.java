package com.funger.bbks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import com.funger.bbks.app.AppManager;
import com.funger.bbks.app.UIHelper;
import com.funger.bbks.fragment.BookHome;
import com.funger.bbks.fragment.LeftFragment;
import com.funger.bbks.fragment.RightFragment;
import com.funger.bbks.fragment.DefaultListFragment;
import com.funger.bbks.view.SlidingMenu;

public class MainActivity extends FragmentActivity {
    private SlidingMenu mSlidingMenu;
    private LeftFragment leftFragment;
    private RightFragment rightFragment;
    private BookHome centerFragment;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	AppManager.getAppManager().addActivity(this);
	// 去标题栏
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	// 设置view
	setContentView(R.layout.activity_main);

	mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
	mSlidingMenu.setLeftView(getLayoutInflater().inflate(
		R.layout.view_fram_left, null));
	mSlidingMenu.setRightView(getLayoutInflater().inflate(
		R.layout.view_fram_right, null));
	mSlidingMenu.setCenterView(getLayoutInflater().inflate(
		R.layout.view_fram_center, null));

	ft = this.getSupportFragmentManager().beginTransaction();
	leftFragment = new LeftFragment();
	rightFragment = new RightFragment();
	ft.replace(R.id.left_frame, leftFragment);
	ft.replace(R.id.right_frame, rightFragment);

	centerFragment = new BookHome();//
	ft.replace(R.id.center_frame, centerFragment);
	ft.commit();

    }

    public void showLeft() {
	mSlidingMenu.showLeftView();
    }

    public void showRight() {
	mSlidingMenu.showRightView();
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_BACK) {
	    UIHelper.exitApp(this);
	    return true;
	}
	return super.onKeyDown(keyCode, event);
    }
}
