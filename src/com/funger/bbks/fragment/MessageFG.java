package com.funger.bbks.fragment;

import java.util.ArrayList;
import java.util.List;

import com.funger.bbks.MainActivity;
import com.funger.bbks.R;
import com.funger.bbks.app.AppContext;
import com.funger.bbks.app.AppException;
import com.funger.bbks.app.UIHelper;
import com.funger.bbks.bean.MessageJson;
import com.funger.bbks.bean.Messages;
import com.funger.bbks.ui.adapter.ListViewMessageAdapter;
import com.funger.bbks.view.PullToRefreshListView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MessageFG extends Fragment {

    private Button message_type_isend;
    private Button message_type_irecived;
    private Button message_type_unread;

    private PullToRefreshListView mlvMessage;
    private ListViewMessageAdapter lvMessageAdapter;
    private List<Messages> lvMessageData = new ArrayList<Messages>();
    private View lvmessage_footer;
    private TextView lvmessage_foot_more;
    private ProgressBar lvmessage_foot_progress;

        
    private Handler mMessageHandler;
    private int type;
    private int pageIndex;
    
    private final static int DATA_LOAD_ING = 0x001;
    private final static int DATA_LOAD_COMPLETE = 0x002;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	
	View view = inflater.inflate(R.layout.fragment_message, null);
	
	//bind header
	UIHelper.bindHeader(((MainActivity) getActivity()), view, "消息");
	
	return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	this.initView(getView());
	this.initData();
    }
    
    /**
     * 头部按钮展示
     * 
     * @param type
     */
    private void headButtonSwitch(int type) {
	switch (type) {
	case DATA_LOAD_ING:
	    lvmessage_foot_progress.setVisibility(View.VISIBLE);
	    break;
	case DATA_LOAD_COMPLETE:
	    lvmessage_foot_progress.setVisibility(View.GONE);
	    break;
	}
    }

    // 初始化视图控件
    private void initView(View view) {

	message_type_isend = (Button)view.findViewById(R.id.message_type_isend);
    	message_type_irecived = (Button)view.findViewById(R.id.message_type_irecived);
    	message_type_unread = (Button)view.findViewById(R.id.message_type_unread);
    	
    	message_type_isend.setOnClickListener(this.MessageBtnClick(message_type_isend));
    	message_type_irecived.setOnClickListener(this.MessageBtnClick(message_type_irecived));
    	message_type_unread.setOnClickListener(this.MessageBtnClick(message_type_unread));

    	
    	lvmessage_footer = getActivity().getLayoutInflater().inflate(R.layout.listview_footer, null);
    	lvmessage_foot_more = (TextView)lvmessage_footer.findViewById(R.id.listview_foot_more);
    	lvmessage_foot_progress = (ProgressBar)lvmessage_footer.findViewById(R.id.listview_foot_progress);

    	lvMessageAdapter = new ListViewMessageAdapter(getActivity(), lvMessageData, R.layout.message_listitem); 
    	mlvMessage = (PullToRefreshListView)view.findViewById(R.id.message_listview);
    	
    	mlvMessage.addFooterView(lvmessage_footer);//添加底部视图  必须在setAdapter前
    	mlvMessage.setAdapter(lvMessageAdapter); 
    	
    }

    // 初始化控件数据
    private void initData() {
	mMessageHandler = new Handler() {
	    public void handleMessage(Message msg) {
		headButtonSwitch(DATA_LOAD_COMPLETE);
		if (msg.what >= 0) {
		    List<Messages> ms = (List<Messages>) msg.obj;
		    if(pageIndex == 0){
			lvMessageData.clear();
		    }
		    lvMessageData.addAll(ms);
		}
		
		if(msg.what < 20){
			lvMessageAdapter.notifyDataSetChanged();
			lvmessage_foot_more.setText(R.string.load_full);
		}else if(msg.what == 20){					
			lvMessageAdapter.notifyDataSetChanged();
			lvmessage_foot_more.setText(R.string.load_more);
		}
	    }
	};
    }

    /**
     * 线程加载好友列表数据
     * 
     * @param type
     *            0:显示自己的粉丝 1:显示自己的关注者
     * @param pageIndex
     *            当前页数
     * @param handler
     *            处理器
     * @param action
     *            动作标识
     */
    private void loadLvMessageData(final int type, final int pageIndex,final Handler handler) {
	headButtonSwitch(DATA_LOAD_ING);
	new Thread() {
	    public void run() {
		Message msg = new Message();
		try {
		    MessageJson fs = ((AppContext)getActivity().getApplication()).getMessages(pageIndex, type);
		    if(fs != null && fs.getIsSuccess()){
			msg.what = fs.getRows().size();
			msg.obj = fs.getRows();
		    }else{
			//没有找到好友信息
			msg.what = 0;
		    }
		} catch (AppException e) {
		    e.printStackTrace();
		    msg.what = -1;
		    msg.obj  = e;
		}
		handler.sendMessage(msg);
	    }
	}.start();
    }

    private View.OnClickListener MessageBtnClick(final Button btn) {
	return new View.OnClickListener() {
	    public void onClick(View v) {
		if (btn == message_type_isend){
		    message_type_isend.setEnabled(false);
		    message_type_irecived.setEnabled(true);
		    message_type_unread.setEnabled(true);
		    type = 1;
		}
		
		if (btn == message_type_irecived){
		    message_type_isend.setEnabled(true);
		    message_type_irecived.setEnabled(false);
		    message_type_unread.setEnabled(true);
		    type = 2;
		}
		
		if (btn == message_type_unread){
		    message_type_isend.setEnabled(true);
		    message_type_irecived.setEnabled(true);
		    message_type_unread.setEnabled(false);
		    type = 3;
		}
		
		//重置pageNo
		pageIndex = 0;
		loadLvMessageData(type,pageIndex, mMessageHandler);
	    }
	    
	};
    }
    
}
