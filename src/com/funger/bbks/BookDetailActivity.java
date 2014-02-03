package com.funger.bbks;

import com.funger.bbks.bean.DuitangInfo;
import com.funger.bbks.view.AbstractAsyncActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * 
 * @DESC:book detail information
 * @TODO TODO
 * @AUTHOR:LPM[shuoli1990@gmail.com]
 * @DATE:2013-11-28
 * @VERSION:V_0.1
 */
public class BookDetailActivity extends AbstractAsyncActivity {
	
	private String tag = "BookDetailActivity";
	
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_book_detail);
	Intent intent  = getIntent();
	DuitangInfo info = (DuitangInfo) intent.getExtras().getSerializable("book_detail");
	
	Log.d(tag, "get info:::"+info.getMsg());

    }
}
