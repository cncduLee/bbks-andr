package com.funger.bbks;

import com.example.android.bitmapfun.util.ImageFetcher;
import com.funger.bbks.app.AppManager;
import com.funger.bbks.app.UIHelper;
import com.funger.bbks.bean.DuitangInfo;
import com.funger.bbks.view.AbstractAsyncActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.NoCopySpan.Concrete;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @DESC:book detail information
 * @TODO TODO
 * @AUTHOR:LPM[shuoli1990@gmail.com]
 * @DATE:2013-11-28
 * @VERSION:V_0.1
 * 
 * ==================
 * erro:No package identifier when getting value for resource number
 * sovle:title.setText(info.getMsg()+""); instead of title.setText(info.getMsg());
 * ==================
 */
public class BookDetailActivity extends AbstractAsyncActivity {

	private String tag = "BookDetailActivity";
	private ImageFetcher mImageFetcher;
	private DuitangInfo info;
	
	private TextView title;
	private TextView author;
	private TextView catlog;
	private TextView price;
	private TextView content;
	private ImageView cover;
	
	private LinearLayout header;
	private ImageView back;
	private Context context;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//hiden title
		setContentView(R.layout.activity_book_detail);
		context = this;
		AppManager.getAppManager().addActivity(this);
		

		Intent intent = getIntent();
		info = (DuitangInfo) intent.getExtras().getSerializable("book_detail");
		
		mImageFetcher = new ImageFetcher(this, 240);
		mImageFetcher.setLoadingImage(R.drawable.empty_photo);
		
		initComponent();
		addListeners();
		adapteeData();
	}
	
	private void initComponent() {
		title = (TextView) findViewById(R.id.bookContent_title);
		author = (TextView) findViewById(R.id.bookContent_author);
		catlog = (TextView) findViewById(R.id.bookContent_catlog);
		price = (TextView) findViewById(R.id.bookContent_price);
		cover = (ImageView) findViewById(R.id.bookContent_cover);
		content = (TextView) findViewById(R.id.bookContent_content);
		
		header = (LinearLayout) findViewById(R.id.pop_header);
		back = (ImageView) header.findViewById(R.id.pop_back);
	}
	
	private void addListeners(){
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				UIHelper.showMain(context);
				AppManager.getAppManager().finishActivity();
			}
		});
	}
	
	private void adapteeData(){
		title.setText(info.getMsg()+"");
		author.setText(info.getAlbid()+"");
		catlog.setText(info.getWidth()+"");
		price.setText(info.getHeight()+"");
		content.setText(info.getMsg()+"");
		mImageFetcher.loadImage(info.getIsrc()+"", cover);
	}
}
