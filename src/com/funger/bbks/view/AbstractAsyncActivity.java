package com.funger.bbks.view;

import com.funger.bbks.app.AppManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;

/**
 * @AUTHOR:LPM[shuoli1990@gmail.com]
 * @DATE:2013-10-4
 * @VERSION:V_0.1
 */
public abstract class AbstractAsyncActivity extends Activity implements AsyncActivity {

    protected static final String TAG = AbstractAsyncActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private boolean destroyed = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to manager
        AppManager.getAppManager().addActivity(this);
        
    }
    
    // ***************************************
    // Activity methods
    // ***************************************
    @Override
    protected void onDestroy() {
	super.onDestroy();
	this.destroyed = true;
    }

    // ***************************************
    // Public methods
    // ***************************************
    public void showLoadingProgressDialog() {
	this.showProgressDialog("Loading. Please wait...");
    }

    public void showProgressDialog(CharSequence message) {
	if (this.progressDialog == null) {
	    this.progressDialog = new ProgressDialog(this);
	    this.progressDialog.setIndeterminate(true);
	}

	this.progressDialog.setMessage(message);
	this.progressDialog.show();
    }

    public void dismissProgressDialog() {
	if (this.progressDialog != null && !this.destroyed) {
	    this.progressDialog.dismiss();
	}
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_BACK) {
	    AppManager.getAppManager().finishActivity();
	    return true;
	}
	return super.onKeyDown(keyCode, event);
    }
}
