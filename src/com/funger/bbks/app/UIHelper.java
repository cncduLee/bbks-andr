package com.funger.bbks.app;

import com.funger.bbks.BookDetailActivity;
import com.funger.bbks.LoginActivity;
import com.funger.bbks.MainActivity;
import com.funger.bbks.R;
import com.funger.bbks.api.URLs;
import com.funger.bbks.bean.BaseEntity;
import com.funger.bbks.bean.DuitangInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UIHelper {
    
    /**
     * open the login dialog
     * @param context
     */
    public static void showLogin(Context context) {
	Intent in = new Intent(context, LoginActivity.class);
	// put some info to the extra
	context.startActivity(in);
    }
    
    public static void showBookDetail(Context context,BaseEntity obj){
    	Intent in = new Intent(context, BookDetailActivity.class);
    	in.putExtra("book_detail", obj);
    	context.startActivity(in);
    }
    
    public static void showMyDetail(Context context,DuitangInfo obj){
	//TODO
//    	Intent in = new Intent(context, BookDetailActivity.class);
//    	in.putExtra("book_detail", obj);
//    	context.startActivity(in);
    }
    
    public static void showMain(Context context) {
    	Intent in = new Intent(context, MainActivity.class);
    	context.startActivity(in);
    }

    /**
     * url跳转
     * 
     * @param context
     * @param url
     */
    public static void showUrlRedirect(Context context, String url,BaseEntity obj) {
	URLs urls = URLs.parseURL(url);
	if (urls != null) {
	    showLinkRedirect(context, urls.getObjType(), obj);
	} else {
	    openBrowser(context, url);
	}
    }
    
    public static void showLinkRedirect(Context context, int objType,BaseEntity obj) {
	switch (objType) {
	case URLs.URL_OBJ_TYPE_BOOK:
		showBookDetail(context, obj);
		break;
	case URLs.URL_OBJ_TYPE_MY:	
	}
}
    
    /**
     * exit app
     * @param context
     */
    public static void exitApp(final Context context) {
	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	builder.setIcon(android.R.drawable.ic_dialog_info);
	builder.setTitle(R.string.app_menu_surelogout);
	builder.setPositiveButton(R.string.sure,
		new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
			// 退出
			AppManager.getAppManager().AppExit(context);
		    }
		});
	builder.setNegativeButton(R.string.cancle,
		new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		    }
		});
	builder.show();
    }

    /**
     * main activity header 设置
     * @param v
     */
    public static void bindHeader(final MainActivity ma, View v,String title) {
	ImageView iv_left = (ImageView) v.findViewById(R.id.iv_left);
	ImageView iv_right = (ImageView) v.findViewById(R.id.iv_right);
	TextView iv_center =  (TextView) v.findViewById(R.id.iv_center);
	iv_center.setText(title);
	
	iv_left.setOnClickListener(new OnClickListener() {

	    public void onClick(View v) {
		ma.showLeft();
	    }
	});

	iv_right.setOnClickListener(new OnClickListener() {

	    public void onClick(View v) {
		ma.showRight();
	    }
	});
    }

    
    /**
     * 弹出Toast消息
     * 
     * @param msg
     */
    public static void ToastMessage(Context cont, String msg) {
	Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    public static void ToastMessage(Context cont, int msg) {
	Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    public static void ToastMessage(Context cont, String msg, int time) {
	Toast.makeText(cont, msg, time).show();
    }

    /**
     * 点击返回监听事件::exit from current activity
     * @param activity
     * @return
     */
    public static View.OnClickListener finish(final Activity activity) {
	return new View.OnClickListener() {
	    public void onClick(View v) {
		activity.finish();
	    }
	};
    }
    
    

    /**
     * 打开浏览器
     * @param context
     * @param url
     */
    public static void openBrowser(Context context, String url) {
	try {
	    Uri uri = Uri.parse(url);
	    Intent it = new Intent(Intent.ACTION_VIEW, uri);
	    context.startActivity(it);
	} catch (Exception e) {
	    e.printStackTrace();
	    ToastMessage(context, "无法浏览此网页", 500);
	}
    }

    /**
     * 获取TextWatcher对象
     * 
     * @param context
     * @param tmlKey
     * @return
     */
    public static TextWatcher getTextWatcher(final Activity context,
	    final String temlKey) {
	return new TextWatcher() {
	    public void onTextChanged(CharSequence s, int start, int before,
		    int count) {
		// 保存当前EditText正在编辑的内容
		((AppContext) context.getApplication()).setProperty(temlKey,
			s.toString());
	    }

	    public void beforeTextChanged(CharSequence s, int start, int count,
		    int after) {
	    }

	    public void afterTextChanged(Editable s) {
	    }
	};
    }
}
