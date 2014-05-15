package com.funger.bbks.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import com.funger.bbks.R;
import com.funger.bbks.api.URLs;
import com.funger.bbks.bean.Book;
import com.funger.bbks.common.StringUtils;
import com.funger.ebook.helper.LocalBook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnCancelListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 下载管理
 * 
 */
public class DownloadManager {

    private static final int DOWN_NOSDCARD = 0;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private static final int DOWN_EMPTY = 3;
    

    private static final int DIALOG_TYPE_LATEST = 0;
    private static final int DIALOG_TYPE_FAIL = 1;

    private static DownloadManager downlaodManager;

    private Context mContext;
    private String downlaodUrl;
    // 通知对话框
    private Dialog noticeDialog;
    // 下载对话框
    private Dialog downloadDialog;
    // 进度条
    private ProgressBar mProgress;
    // 显示下载数值
    private TextView mProgressText;
    // 进度值
    private int progress;
    // 下载线程
    private Thread downLoadThread;
    // 终止标记
    private boolean interceptFlag;
    // 下载包保存路径
    private String savePath = "";
    // apk保存完整路径
    private String txtFilePath = "";
    // 临时下载文件路径
    private String tmpFilePath = "";
    // 下载文件大小
    private String txtFileSize;
    // 已下载文件大小
    private String tmpFileSize;
    
    
    private ProgressDialog progressDialog;
    private Book ebook;
    
    private LocalBook localBook;
    private String PATH = "path";
    private String TYPE = "type";

    private Handler mHandler = new Handler() {
	public void handleMessage(Message msg) {
	    switch (msg.what) {
	    case DOWN_UPDATE:
		mProgress.setProgress(progress);
		mProgressText.setText(tmpFileSize + "/" + txtFileSize);
		break;
	    case DOWN_OVER:
		downloadDialog.dismiss();
		Toast.makeText(mContext, "下载完成，为您安装", 3000).show();
		installBook();
		break;
	    case DOWN_EMPTY:
		downloadDialog.dismiss();
		Toast.makeText(mContext, "无法下载文件，连接无法正常访问", 3000).show();
		break;
	    case DOWN_NOSDCARD:
		downloadDialog.dismiss();
		Toast.makeText(mContext, "无法下载文件，请检查SD卡是否挂载", 3000).show();
		break;
	    }
	};
    };

    public static DownloadManager getdownlaodManager(Book ebook,Context context) {
	if (downlaodManager == null) {
	    downlaodManager = new DownloadManager();
	}
	downlaodManager.mContext = context;
	downlaodManager.interceptFlag = false;

	downlaodManager.ebook = ebook;
	String url = ebook.getTxtSrc();
	
	if(!StringUtils.isEmpty(url) && url.startsWith("http://localhost:8080")){
	    url = url.replace("http://localhost:8080", URLs.HTTP + URLs.HOST);
	    downlaodManager.downlaodUrl = url; 
	}
	downlaodManager.localBook = new LocalBook(context, "localbook");
	return downlaodManager;
    }

  

    /**
     * 显示下载通知框通知对话框
     */
    public void showNoticeDialog() {
	AlertDialog.Builder builder = new Builder(mContext);
	builder.setTitle("电子书下载");
	builder.setMessage("是否确定下载计划？");
	builder.setPositiveButton("立即下载", new OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
		dialog.dismiss();
		showDownloadDialog();
	    }
	});
	builder.setNegativeButton("稍后下载", new OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
		dialog.dismiss();
	    }
	});
	noticeDialog = builder.create();
	noticeDialog.show();
    }

    /**
     * 显示下载对话框
     */
    private void showDownloadDialog() {
	AlertDialog.Builder builder = new Builder(mContext);
	builder.setTitle("正在下载电子书");

	final LayoutInflater inflater = LayoutInflater.from(mContext);
	View v = inflater.inflate(R.layout.downlaod_progress, null);
	mProgress = (ProgressBar) v.findViewById(R.id.download_progress);
	mProgressText = (TextView) v.findViewById(R.id.downlaod_progress_text);

	builder.setView(v);
	builder.setNegativeButton("取消", new OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
		dialog.dismiss();
		interceptFlag = true;
	    }
	});
	builder.setOnCancelListener(new OnCancelListener() {
	    @Override
	    public void onCancel(DialogInterface dialog) {
		dialog.dismiss();
		interceptFlag = true;
	    }
	});
	downloadDialog = builder.create();
	downloadDialog.setCanceledOnTouchOutside(false);
	downloadDialog.show();

	downloadEbook();
    }

    private Runnable mdownTXTRunnable = new Runnable() {
	@Override
	public void run() {
	    try {
		
		String txtName = ebook.getBookName() +".txt";
		String tmptxt =  ebook.getBookName() + ".tmp";
		
		// 判断是否挂载了SD卡
		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
		    savePath = Environment.getExternalStorageDirectory()
			    .getAbsolutePath() + "/MOWUD/ebooks/";
		    File file = new File(savePath);
		    if (!file.exists()) {
			file.mkdirs();
		    }
		    txtFilePath = savePath + txtName;
		    tmpFilePath = savePath + tmptxt;
		}

		// 没有挂载SD卡，无法下载文件
		if (txtFilePath == null || txtFilePath == "") {
		    mHandler.sendEmptyMessage(DOWN_NOSDCARD);
		    return;
		}

		File txtFile = new File(txtFilePath);

		// 是否已下载该电子文件
		if (txtFile.exists()) {
		    downloadDialog.dismiss();
		    mHandler.sendEmptyMessage(DOWN_OVER);
		    return;
		}

		// 输出临时下载文件
		File tmpFile = new File(tmpFilePath);
		FileOutputStream fos = new FileOutputStream(tmpFile);
		
		if(StringUtils.isEmpty(downlaodUrl)){
		    // 通知安装
		    mHandler.sendEmptyMessage(DOWN_EMPTY);
		    return;
		}
		URL url = new URL(downlaodUrl);
		System.out.println("begain downlaod:"+downlaodUrl);
		HttpURLConnection conn = (HttpURLConnection) url
			.openConnection();
		conn.connect();
		int length = conn.getContentLength();
		InputStream is = conn.getInputStream();

		// 显示文件大小格式：2个小数点显示
		DecimalFormat df = new DecimalFormat("0.00");
		// 进度条下面显示的总文件大小
		txtFileSize = df.format((float) length / 1024 ) + "KB";

		int count = 0;
		byte buf[] = new byte[1024];

		do {
		    int numread = is.read(buf);
		    count += numread;
		    // 进度条下面显示的当前下载文件大小
		    tmpFileSize = df.format((float) count / 1024 ) + "KB";
		    // 当前进度值
		    progress = (int) (((float) count / length) * 100);
		    // 更新进度
		    mHandler.sendEmptyMessage(DOWN_UPDATE);
		    if (numread <= 0) {
			// 下载完成 - 将临时下载文件转成APK文件
			if (tmpFile.renameTo(txtFile)) {
			    // 通知安装
			    mHandler.sendEmptyMessage(DOWN_OVER);
			}
			break;
		    }
		    fos.write(buf, 0, numread);
		} while (!interceptFlag);// 点击取消就停止下载

		fos.close();
		is.close();
	    } catch (MalformedURLException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    }

	}
    };

    /**
     * 下载电子书
     * 
     * @param url
     */
    private void downloadEbook() {
	downLoadThread = new Thread(mdownTXTRunnable);
	downLoadThread.start();
    }

    /**
     * 
     * 加入到阅读列表
     * 
     * @param url
     */
    private void installBook() {
	
	showProgressDialog("正在为您导入电子书..."+txtFilePath);
	
	File file = new File(txtFilePath);
	if (!file.exists()) {
	    return;
	}
	
	System.out.println("--import txt begain--");
	
	try{
	    insertDB(file);
	}catch (Exception e) {
	    e.printStackTrace();
	}
	System.out.println("--import txt success--");
	dismissProgressDialog();
    }

    private void insertDB(File file) {
	SQLiteDatabase db = localBook.getWritableDatabase();
	String s = file.getParent();
	String s1 = file.toString();
	String sql = "insert into " + "localbook" + " (parent," + PATH + ", "
		+ TYPE + ",now,ready) values('" + s + "','" + s1 + "',3,0,null"
		+ ");";
	db.execSQL(sql);
	db.close();
    }
    
    private void showProgressDialog(CharSequence message) {
	if (this.progressDialog == null) {
	    this.progressDialog = new ProgressDialog(mContext);
	    this.progressDialog.setIndeterminate(true);
	}

	this.progressDialog.setMessage(message);
	this.progressDialog.show();
    }
    
    private void dismissProgressDialog() {
	if (this.progressDialog != null) {
	    this.progressDialog.dismiss();
	}
    }
}
