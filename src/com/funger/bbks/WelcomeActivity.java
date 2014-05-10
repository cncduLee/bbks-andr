package com.funger.bbks;

import com.funger.bbks.app.AppContext;
import com.funger.bbks.app.AppManager;
import com.funger.bbks.app.UIHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	// 去标题
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	// 全屏
	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
	// 设置welcome.xml
	setContentView(R.layout.activity_welcome);
	
	//加入控制
	AppManager.getAppManager().addActivity(this);
		
	
	ImageView imageView = (ImageView) findViewById(R.id.welcome_background);

	AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
	animation.setDuration(1000);// 2s动画
	imageView.startAnimation(animation);
	
	animation.setAnimationListener(new AnimationListener() {
	    public void onAnimationEnd(Animation arg0) {
		redirectTo();
	    }

	    public void onAnimationRepeat(Animation arg0) {
	    }

	    public void onAnimationStart(Animation arg0) {
	    }
	});
	
	//出去化application
	AppContext appContext = (AppContext) getApplication();
	//初始化一些资源，读取保存的状态值等
	
    }

    private void redirectTo() {
	Intent intent = new Intent(this, LoginActivity.class);
	startActivity(intent);
	this.finish();
    }
    
    
}
