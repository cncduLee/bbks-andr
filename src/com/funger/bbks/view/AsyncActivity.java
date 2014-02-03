package com.funger.bbks.view;

/**
 * @DESC:...
 * @TODO TODO
 * @AUTHOR:LPM[shuoli1990@gmail.com]
 * @DATE:2013-10-4
 * @VERSION:V_0.1
 */
public interface AsyncActivity {

	public void showLoadingProgressDialog();

	public void showProgressDialog(CharSequence message);

	public void dismissProgressDialog();

}
