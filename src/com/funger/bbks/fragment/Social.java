package com.funger.bbks.fragment;

import java.util.ArrayList;
import java.util.List;

import com.funger.bbks.MainActivity;
import com.funger.bbks.R;
import com.funger.bbks.app.AppContext;
import com.funger.bbks.app.AppException;
import com.funger.bbks.app.UIHelper;
import com.funger.bbks.bean.Friend;
import com.funger.bbks.bean.FriendJson;
import com.funger.bbks.ui.adapter.ListViewFriendAdapter;
import com.funger.bbks.view.PullToRefreshListView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Social extends Fragment {
    
    //private ProgressBar mProgressbar;

    private Button friend_type_fans;
    private Button friend_type_follower;

    private PullToRefreshListView mlvFriend;
    private ListViewFriendAdapter lvFriendAdapter;
    private List<Friend> lvFriendData = new ArrayList<Friend>();
    private View lvFriend_footer;
    private TextView lvFriend_foot_more;
    private ProgressBar lvFriend_foot_progress;

        
    private Handler mFriendHandler;
    private int type;
    private int pageIndex;
    
    private final static int DATA_LOAD_ING = 0x001;
    private final static int DATA_LOAD_COMPLETE = 0x002;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {

	View view = inflater.inflate(R.layout.fragment_social, null);

	// bind header
	UIHelper.bindHeader(((MainActivity) getActivity()), view, "圈子");

	return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	
	this.initView(getView());
	this.initData();
    }

    @Override
    public void onResume() {
	super.onResume();
    }

    @Override
    public void onStart() {
	super.onStart();
	friend_type_follower.performClick();
    }

    /**
     * 头部按钮展示
     * 
     * @param type
     */
    private void headButtonSwitch(int type) {
	switch (type) {
	case DATA_LOAD_ING:
	    lvFriend_foot_progress.setVisibility(View.VISIBLE);
	    break;
	case DATA_LOAD_COMPLETE:
	    lvFriend_foot_progress.setVisibility(View.GONE);
	    break;
	}
    }

    // 初始化视图控件
    private void initView(View view) {

	friend_type_fans = (Button)view.findViewById(R.id.friend_type_fans);
    	friend_type_follower = (Button)view.findViewById(R.id.friend_type_follower);
    	
    	friend_type_fans.setOnClickListener(this.friendBtnClick(friend_type_fans));
    	friend_type_follower.setOnClickListener(this.friendBtnClick(friend_type_follower));
    	
    	
    	//设置粉丝与关注的数量
    	AppContext appContext = (AppContext) getActivity().getApplication();
    	int followers = appContext.getSession().getFloweds();
    	int fans = appContext.getSession().getFlowings();
    	
    	friend_type_follower.setText(getString(R.string.user_friend_follower, followers));
    	friend_type_fans.setText(getString(R.string.user_friend_fans, fans));
    	

    	lvFriend_footer = getActivity().getLayoutInflater().inflate(R.layout.listview_footer, null);
    	lvFriend_foot_more = (TextView)lvFriend_footer.findViewById(R.id.listview_foot_more);
    	lvFriend_foot_progress = (ProgressBar)lvFriend_footer.findViewById(R.id.listview_foot_progress);

    	lvFriendAdapter = new ListViewFriendAdapter(getActivity(), lvFriendData, R.layout.friend_listitem); 
    	mlvFriend = (PullToRefreshListView)view.findViewById(R.id.friend_listview);
    	
    	mlvFriend.addFooterView(lvFriend_footer);//添加底部视图  必须在setAdapter前
    	mlvFriend.setAdapter(lvFriendAdapter); 
    	
    }

    // 初始化控件数据
    private void initData() {
	mFriendHandler = new Handler() {
	    public void handleMessage(Message msg) {
		headButtonSwitch(DATA_LOAD_COMPLETE);
		if (msg.what >= 0) {
		    List<Friend> friends = (List<Friend>) msg.obj;
		    if(pageIndex == 0){
			lvFriendData.clear();
		    }
		    lvFriendData.addAll(friends);
		}
		
		if(msg.what < 20){
			lvFriendAdapter.notifyDataSetChanged();
			lvFriend_foot_more.setText(R.string.load_full);
		}else if(msg.what == 20){					
			lvFriendAdapter.notifyDataSetChanged();
			lvFriend_foot_more.setText(R.string.load_more);
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
    private void loadLvFriendData(final int type, final int pageIndex,final Handler handler) {
	headButtonSwitch(DATA_LOAD_ING);
	new Thread() {
	    public void run() {
		Message msg = new Message();
		try {
		    FriendJson fs = ((AppContext)getActivity().getApplication()).getFriends(pageIndex, type);
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

    private View.OnClickListener friendBtnClick(final Button btn) {
	return new View.OnClickListener() {
	    public void onClick(View v) {
		if (btn == friend_type_fans){
		    friend_type_fans.setEnabled(false);
		    type = 1;
		}else{
		    friend_type_fans.setEnabled(true);
		}
		
		if (btn == friend_type_follower){
		    type = 2;
		    friend_type_follower.setEnabled(false);
		}else{
		    friend_type_follower.setEnabled(true);
		}
		
		//重置pageNo
		pageIndex = 0;
		loadLvFriendData(type,pageIndex, mFriendHandler);
	    }
	    
	};
    }

}
