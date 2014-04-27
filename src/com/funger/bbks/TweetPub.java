package com.funger.bbks;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.funger.bbks.app.AppContext;
import com.funger.bbks.app.UIHelper;
import com.funger.bbks.common.FileUtils;
import com.funger.bbks.common.ImageUtils;
import com.funger.bbks.common.MediaUtils;
import com.funger.bbks.common.StringUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 发表动态
 * 
 * @author LPM
 * @version 1.0
 */
public class TweetPub extends BaseActivity {

    private FrameLayout mForm;
    private ImageView mBack;
    private EditText mContent;
    private Button mPublish;
    private ImageView mFace;
    private ImageView mPick;
    private ImageView mAtme;
    private ImageView mSoftware;
    private ImageView mImage;
    private LinearLayout mClearwords;
    private TextView mNumberwords;

    private GridView mGridView;

    private File imgFile;
    private String theLarge;
    private String theThumbnail;
    private InputMethodManager imm;

    public static LinearLayout mMessage;
    public static Context mContext;

    private static final int MAX_TEXT_LENGTH = 160;// 最大输入字数
    private static final String TEXT_ATME = "@请输入用户名 ";
    private static final String TEXT_SOFTWARE = "#请输入软件名#";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.tweet_pub);

	mContext = this;
	// 软键盘管理类
	imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

	// 初始化基本视图
	this.initView();
	// 初始化表情视图
	//this.initGridView();
    }

    @Override
    protected void onDestroy() {
	mContext = null;
	super.onDestroy();
    }

    @Override
    protected void onResume() {
	super.onResume();
	if (mGridView.getVisibility() == View.VISIBLE) {
	    // 隐藏表情
	    hideFace();
	}
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_BACK) {
	    if (mGridView.getVisibility() == View.VISIBLE) {
		// 隐藏表情
		hideFace();
	    } else {
		return super.onKeyDown(keyCode, event);
	    }
	}
	return true;
    }

    // 初始化视图控件
    private void initView() {
	mForm = (FrameLayout) findViewById(R.id.tweet_pub_form);
	mBack = (ImageView) findViewById(R.id.tweet_pub_back);
	mMessage = (LinearLayout) findViewById(R.id.tweet_pub_message);
	mImage = (ImageView) findViewById(R.id.tweet_pub_image);
	mPublish = (Button) findViewById(R.id.tweet_pub_publish);
	mContent = (EditText) findViewById(R.id.tweet_pub_content);
	mFace = (ImageView) findViewById(R.id.tweet_pub_footbar_face);
	mPick = (ImageView) findViewById(R.id.tweet_pub_footbar_photo);
	mAtme = (ImageView) findViewById(R.id.tweet_pub_footbar_atme);
	mSoftware = (ImageView) findViewById(R.id.tweet_pub_footbar_software);
	mClearwords = (LinearLayout) findViewById(R.id.tweet_pub_clearwords);
	mNumberwords = (TextView) findViewById(R.id.tweet_pub_numberwords);

	mBack.setOnClickListener(UIHelper.finish(this));
//	mPublish.setOnClickListener(publishClickListener);
//	mImage.setOnLongClickListener(imageLongClickListener);
	mFace.setOnClickListener(faceClickListener);
	mPick.setOnClickListener(pickClickListener);
	mAtme.setOnClickListener(atmeClickListener);
	mSoftware.setOnClickListener(softwareClickListener);
	mClearwords.setOnClickListener(clearwordsClickListener);

	// @某人
	String atme = getIntent().getStringExtra("at_me");
	int atuid = getIntent().getIntExtra("at_uid", 0);

	// 编辑器添加文本监听
	mContent.addTextChangedListener(new TextWatcher() {
	    public void onTextChanged(CharSequence s, int start, int before,
		    int count) {
		// 保存当前EditText正在编辑的内容
		// ((AppContext)getApplication()).setProperty(tempTweetKey,
		// s.toString());
		// 显示剩余可输入的字数
		mNumberwords.setText((MAX_TEXT_LENGTH - s.length()) + "");
	    }

	    public void beforeTextChanged(CharSequence s, int start, int count,
		    int after) {
	    }

	    public void afterTextChanged(Editable s) {
	    }
	});
	// 编辑器点击事件
	mContent.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		// 显示软键盘
		showIMM();
	    }
	});
	// 设置最大输入字数
	InputFilter[] filters = new InputFilter[1];
	filters[0] = new InputFilter.LengthFilter(MAX_TEXT_LENGTH);
	mContent.setFilters(filters);

    }


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
	    imm.hideSoftInputFromWindow(mContent.getWindowToken(), 0);
	    // 显示表情
	    showFace();
	} else {
	    // 显示软键盘
	    imm.showSoftInput(mContent, 0);
	    // 隐藏表情
	    hideFace();
	}
    }

    private View.OnClickListener faceClickListener = new View.OnClickListener() {
	public void onClick(View v) {
	    showOrHideIMM();
	}
    };

    private View.OnClickListener pickClickListener = new View.OnClickListener() {
	public void onClick(View v) {
	    // 隐藏软键盘
	    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	    // 隐藏表情
	    hideFace();

//	    CharSequence[] items = {
//		    TweetPub.this.getString(R.string.img_from_album),
//		    TweetPub.this.getString(R.string.img_from_camera) };
//	    imageChooseItem(items);
	}
    };

    private View.OnClickListener atmeClickListener = new View.OnClickListener() {
	public void onClick(View v) {
	    // 显示软键盘
	    showIMM();

	    // 在光标所在处插入“@用户名”
	    int curTextLength = mContent.getText().length();
	    if (curTextLength < MAX_TEXT_LENGTH) {
		String atme = TEXT_ATME;
		int start, end;
		if ((MAX_TEXT_LENGTH - curTextLength) >= atme.length()) {
		    start = mContent.getSelectionStart() + 1;
		    end = start + atme.length() - 2;
		} else {
		    int num = MAX_TEXT_LENGTH - curTextLength;
		    if (num < atme.length()) {
			atme = atme.substring(0, num);
		    }
		    start = mContent.getSelectionStart() + 1;
		    end = start + atme.length() - 1;
		}
		if (start > MAX_TEXT_LENGTH || end > MAX_TEXT_LENGTH) {
		    start = MAX_TEXT_LENGTH;
		    end = MAX_TEXT_LENGTH;
		}
		mContent.getText().insert(mContent.getSelectionStart(), atme);
		mContent.setSelection(start, end);// 设置选中文字
	    }
	}
    };

    private View.OnClickListener softwareClickListener = new View.OnClickListener() {
	public void onClick(View v) {
	    // 显示软键盘
	    showIMM();

	    // 在光标所在处插入“#软件名#”
	    int curTextLength = mContent.getText().length();
	    if (curTextLength < MAX_TEXT_LENGTH) {
		String software = TEXT_SOFTWARE;
		int start, end;
		if ((MAX_TEXT_LENGTH - curTextLength) >= software.length()) {
		    start = mContent.getSelectionStart() + 1;
		    end = start + software.length() - 2;
		} else {
		    int num = MAX_TEXT_LENGTH - curTextLength;
		    if (num < software.length()) {
			software = software.substring(0, num);
		    }
		    start = mContent.getSelectionStart() + 1;
		    end = start + software.length() - 1;
		}
		if (start > MAX_TEXT_LENGTH || end > MAX_TEXT_LENGTH) {
		    start = MAX_TEXT_LENGTH;
		    end = MAX_TEXT_LENGTH;
		}
		mContent.getText().insert(mContent.getSelectionStart(),
			software);
		mContent.setSelection(start, end);// 设置选中文字
	    }
	}
    };

    private View.OnClickListener clearwordsClickListener = new View.OnClickListener() {
	public void onClick(View v) {
	    String content = mContent.getText().toString();
	    if (!StringUtils.isEmpty(content)) {
//		UIHelper.showClearWordsDialog(v.getContext(), mContent,
//			mNumberwords);
	    }
	}
    };
    
    @Override
    protected void onActivityResult(final int requestCode,
	    final int resultCode, final Intent data) {
	if (resultCode != RESULT_OK)
	    return;

	final Handler handler = new Handler() {
	    public void handleMessage(Message msg) {
		if (msg.what == 1 && msg.obj != null) {
		    // 显示图片
		    mImage.setImageBitmap((Bitmap) msg.obj);
		    mImage.setVisibility(View.VISIBLE);
		}
	    }
	};
    }
}