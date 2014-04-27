package com.funger.bbks;

import com.funger.bbks.app.AppContext;
import com.funger.bbks.app.UIHelper;
import com.funger.bbks.common.StringUtils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * 发表评论
 * 
 * @author LPM
 * @version 1.0
 */
public class CommentPub extends BaseActivity {

    public final static int CATALOG_NEWS = 1;
    public final static int CATALOG_POST = 2;
    public final static int CATALOG_TWEET = 3;
    public final static int CATALOG_ACTIVE = 4;
    public final static int CATALOG_MESSAGE = 4;// 动态与留言都属于消息中心
    public final static int CATALOG_BLOG = 5;

    private ImageView mBack;
    private EditText mContent;
    private CheckBox mZone;
    private Button mPublish;
    private ProgressDialog mProgress;

    private int _catalog;
    private int _id;
    private int _uid;
    private String _content;
    private int _isPostToMyZone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.comment_pub);

	this.initView();

    }

    private void initView() {
	// TODO Auto-generated method stub
	
    }

 

   
}
