package com.funger.bbks;


import com.example.android.bitmapfun.util.ImageFetcher;
import com.funger.bbks.app.AppManager;
import com.funger.bbks.app.UIHelper;
import com.funger.bbks.bean.Book;
import com.funger.bbks.common.StringUtils;
import com.funger.bbks.ui.adapter.GridViewFaceAdapter;
import com.funger.bbks.view.AbstractAsyncActivity;

import android.R.color;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

/**
 * 
 * @DESC:book detail information
 * @TODO TODO
 * @AUTHOR:LPM[shuoli1990@gmail.com]
 * @DATE:2013-11-28
 * @VERSION:V_0.1
 * 
 *                ================== erro:No package identifier when getting
 *                value for resource number
 *                sovle:title.setText(info.getMsg()+""); instead of
 *                title.setText(info.getMsg()); ==================
 */
public class BookDetailActivity extends AbstractAsyncActivity {

    private String tag = "BookDetailActivity";
    private ImageFetcher mImageFetcher;
    private Book info;

    private TextView title;
    private TextView author;
    private TextView press;
    private TextView price;
    private TextView content;
    private TextView directory;
    private TextView authorIntr;
    private ImageView cover;
    
    private Button bookShelf,bookComment,bookRead,bookDownload;

    private LinearLayout header;
    private ImageView back;
    private Context context;
    private InputMethodManager imm;
    private ImageView mFace;
    private GridView mGridView;
    private GridViewFaceAdapter mGVFaceAdapter;
    private LinearLayout eflag;


    private ViewSwitcher mFootViewSwitcher;
    private ImageView mFootEditebox;
    private EditText mFootEditer;
    private Button mFootPubcomment;
    private ProgressDialog mProgress;

    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);// hiden title
	setContentView(R.layout.activity_book_detail);
	context = this;
	AppManager.getAppManager().addActivity(this);

	Intent intent = getIntent();
	info = (Book) intent.getExtras().getSerializable("book_detail");

	mImageFetcher = new ImageFetcher(this, 240);
	mImageFetcher.setLoadingImage(R.drawable.empty_photo);

	initComponent();
	 //初始化表情视图
      	this.initGridView();
      	
	addListeners();
	adapteeData();
    }

    private void initComponent() {
	title = (TextView) findViewById(R.id.bookContent_title);
	author = (TextView) findViewById(R.id.bookContent_author);
	press = (TextView) findViewById(R.id.bookContent_press);
	price = (TextView) findViewById(R.id.bookContent_price);
	cover = (ImageView) findViewById(R.id.bookContent_cover);
	content = (TextView) findViewById(R.id.bookContent_content);
	directory = (TextView) findViewById(R.id.bookContent_directory);
	authorIntr = (TextView) findViewById(R.id.bookContent_authorintro);
	eflag = (LinearLayout) findViewById(R.id.bookContent_eflag);

	header = (LinearLayout) findViewById(R.id.pop_header);
	back = (ImageView) header.findViewById(R.id.pop_back);
	
	bookShelf = (Button) findViewById(R.id.book_add_bookshelf);
	bookComment = (Button) findViewById(R.id.book_add_bookcomment);
	bookRead = (Button) findViewById(R.id.book_add_bookread);
	bookDownload = (Button) findViewById(R.id.book_add_bookdownload);
	

	imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
	mFace = (ImageView) findViewById(R.id.book_detail_foot_face);
    	mFace.setOnClickListener(facesClickListener);

	mFootViewSwitcher = (ViewSwitcher) findViewById(R.id.book_detail_foot_viewswitcher);
	mFootPubcomment = (Button) findViewById(R.id.book_detail_foot_pubcomment);
	mFootPubcomment.setOnClickListener(commentpubClickListener);
	mFootEditebox = (ImageView) findViewById(R.id.book_detail_footbar_editebox);
	mFootEditebox.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		mFootViewSwitcher.showNext();
		mFootEditer.setVisibility(View.VISIBLE);
		mFootEditer.requestFocus();
		mFootEditer.requestFocusFromTouch();
		imm.showSoftInput(mFootEditer, 0);// 显示软键盘
	    }
	});
	mFootEditer = (EditText) findViewById(R.id.book_detail_foot_editer);
	mFootEditer.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		// 显示软键盘&隐藏表情
		showIMM();
	    }
	});
	mFootEditer.setOnKeyListener(new View.OnKeyListener() {
	    public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		    if (mFootViewSwitcher.getDisplayedChild() == 1) {
			mFootViewSwitcher.setDisplayedChild(0);
			mFootEditer.clearFocus();// 隐藏软键盘
			mFootEditer.setVisibility(View.GONE);// 隐藏编辑框
			hideFace();// 隐藏表情
		    }
		    return true;
		}
		return false;
	    }
	});

    }

    private void addListeners() {
	back.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View arg0) {
		//UIHelper.showMain(context);
		AppManager.getAppManager().finishActivity();
	    }
	});
	bookDownload.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View arg0) {
		UIHelper.ToastMessage(getApplication(), "download");
	    }
	});
	bookRead.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View arg0) {
		UIHelper.ToastMessage(getApplication(), "bookRead click");
	    }
	});
	
	
	
    }

    private void adapteeData() {
	title.setText(info.getBookName());
	author.setText(info.getAuthor());
	price.setText(info.getPubPrice() == null?"--":info.getPubPrice().toString());
	content.setText(info.getOutline().length()>1000?info.getOutline().substring(0, 999):info.getOutline());
	
	if(info.getPress() != null){
	    press.setText(info.getPress());
	}else{
	    press.setText("暂无...");
	}
	if(info.getDirectory() != null){
	    directory.setText(info.getDirectory().length()>1000?info.getDirectory().substring(0, 999):info.getDirectory());
	}else{
	    directory.setText("暂无...");
	}
	if(info.getAuthor() != null){
	    authorIntr.setText(info.getAuthorintro());
	}else{
	    authorIntr.setText("暂无...");
	}
	
	if(!StringUtils.isEmpty(info.geteFlag()) && info.geteFlag().equals("1")){
	    //电子书
	    bookRead.setEnabled(true);
	    bookDownload.setEnabled(true);
	    bookRead.setTextColor(Color.RED);
	    bookDownload.setTextColor(Color.RED);
	}else{
	    //两个btn不能使用
	    bookRead.setEnabled(false);
	    bookDownload.setEnabled(false);
	}
	
	mImageFetcher.loadImage(info.getCoverPic(), cover);
    }
    
    //评论发布事件
    private View.OnClickListener commentpubClickListener = new View.OnClickListener() {
	public void onClick(View arg0) {
	    // TODO
	};
    };
    // 表情控件点击事件
    private View.OnClickListener facesClickListener = new View.OnClickListener() {
	public void onClick(View v) {
	    showOrHideIMM();
	}
    };

    private void showIMM() {
	mFace.setTag(1);
	showOrHideIMM();
    }

    private void showFace() {
	mFace.setImageResource(R.drawable.widget_bar_keyboard);
	mFace.setTag(1);
	mGridView.setVisibility(View.VISIBLE);
    }

    private void hideFace() {
	mFace.setImageResource(R.drawable.widget_bar_face);
	mFace.setTag(null);
	mGridView.setVisibility(View.GONE);
    }

    private void showOrHideIMM() {
	if (mFace.getTag() == null) {
	    // 隐藏软键盘
	    imm.hideSoftInputFromWindow(mFootEditer.getWindowToken(), 0);
	    // 显示表情
	    showFace();
	} else {
	    // 显示软键盘
	    imm.showSoftInput(mFootEditer, 0);
	    // 隐藏表情
	    hideFace();
	}
    }
    
    //初始化表情控件
    private void initGridView() {
    	mGVFaceAdapter = new GridViewFaceAdapter(this);
    	mGridView = (GridView)findViewById(R.id.book_detail_foot_faces);
    	mGridView.setAdapter(mGVFaceAdapter);
    	mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//插入的表情
				SpannableString ss = new SpannableString(view.getTag().toString());
				Drawable d = getResources().getDrawable((int)mGVFaceAdapter.getItemId(position));
				d.setBounds(0, 0, 35, 35);//设置表情图片的显示大小
				ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
				ss.setSpan(span, 0, view.getTag().toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);				 
				//在光标所在处插入表情
				mFootEditer.getText().insert(mFootEditer.getSelectionStart(), ss);				
			}    		
    	});
    }
}
