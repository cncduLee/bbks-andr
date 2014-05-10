package com.funger.bbks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.funger.bbks.app.AppContext;
import com.funger.bbks.app.AppException;
import com.funger.bbks.app.UIHelper;
import com.funger.bbks.bean.User;
import com.funger.bbks.bean.UserJson;
import com.funger.bbks.common.StringUtils;
import com.funger.bbks.view.AbstractAsyncActivity;

public class LoginDialog extends AbstractAsyncActivity {
    private EditText username;
    private EditText userpwd;
    private Button loginbtn, exitbtn;
    
    private CheckBox isRemenber;
    private InputMethodManager imm;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);//hiden title
	setContentView(R.layout.dialog_login);
	
	imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        initData();
	bindComponents();
	addListeners();
    }

    private void bindComponents() {
	username = (EditText) findViewById(R.id.login_username);
	userpwd = (EditText) findViewById(R.id.login_password);
	loginbtn = (Button) findViewById(R.id.login_submit);
	exitbtn = (Button) findViewById(R.id.login_exit);
	isRemenber = (CheckBox) findViewById(R.id.chek_remenberme);
	
	username.setText("shouli1990@gmail.com");
	userpwd.setText("aaaaaa");
    }
    
    private void addListeners(){
	exitbtn.setOnClickListener(UIHelper.finish(this));
	loginbtn.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		//隐藏软键盘
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  
		String name = username.getText().toString();
		String pwd = userpwd.getText().toString();
		boolean isRemenbered = isRemenber.isChecked();
		
		if(StringUtils.isEmpty(name)){
		    UIHelper.ToastMessage(v.getContext(), R.string.msg_login_email_null);
		    return;
		}
		if(StringUtils.isEmpty(pwd)){
		    UIHelper.ToastMessage(v.getContext(), R.string.msg_login_pwd_null);
		    return;
		}
		login(name,pwd,isRemenbered);	
	    }
	});
    }
    
    
    private void initData() {
	handler = new Handler() {
	    public void handleMessage(Message msg) {
		if(msg.what > 0){
		    //成功
		    User u = (User) msg.obj;
		    AppContext appContext = (AppContext) getApplication();
		    //TODO....
		    UIHelper.finish(LoginDialog.this);
		}else{
		    //失败
		    UIHelper.ToastMessage(LoginDialog.this, "登录失败，请稍后重试！");
		}
		
	    }
	};
    }

    private void login(final String name, final String pwd, boolean isRemenbered) {
	new Thread() {
	    public void run() {
		Message msg = new Message();
		
		try {
		    UserJson uj = ((AppContext)getApplication()).login(name, pwd);
		    if(uj.getIsSuccess()){
			msg.what = 1;	
		    }
		    msg.obj = uj.getObj();
		} catch (Exception e) {
		    e.printStackTrace();
		    msg.what = -1;
		    msg.obj = e;
		}
		handler.sendMessage(msg);
	    }
	}.start();
    }
}