package com.funger.bbks.fragment;

import java.util.ArrayList;
import java.util.List;

import com.funger.bbks.MainActivity;
import com.funger.bbks.R;
import com.funger.bbks.app.AppContext;
import com.funger.bbks.app.AppException;
import com.funger.bbks.app.UIHelper;
import com.funger.bbks.bean.Dynamic;
import com.funger.bbks.bean.DynamicJson;

import com.funger.bbks.ui.adapter.ListViewTweetAdapter;
import com.funger.bbks.view.PullToRefreshListView;
import com.google.gson.Gson;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DynamicFG extends Fragment {
   
    private PullToRefreshListView mlvDynamic;
    private ListViewTweetAdapter lvDynamicAdapter;
    private List<Dynamic> lvDynamicData = new ArrayList<Dynamic>();
    private View lvDynamic_footer;
    private TextView lvDynamic_foot_more;
    private ProgressBar lvDynamic_foot_progress;

    private Handler mDynamicHandler;
    private int type;
    private int pageIndex;

    private final static int DATA_LOAD_ING = 0x001;
    private final static int DATA_LOAD_COMPLETE = 0x002;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	
	View view = inflater.inflate(R.layout.fragment_dynamic, null);
	
	//bind header
	UIHelper.bindHeader(((MainActivity) getActivity()), view, "动态");
	
	return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	this.initView(getView());
	this.initData();
    }
    
    @Override
    public void onStart() {
	super.onStart();
	loadLvDynamicData(type,pageIndex,mDynamicHandler);
    }

    /**
     * 头部按钮展示
     * 
     * @param type
     */
    private void headButtonSwitch(int type) {
	switch (type) {
	case DATA_LOAD_ING:
	    lvDynamic_foot_progress.setVisibility(View.VISIBLE);
	    break;
	case DATA_LOAD_COMPLETE:
	    lvDynamic_foot_progress.setVisibility(View.GONE);
	    break;
	}
    }

    // 初始化视图控件
    private void initView(View view) {

	lvDynamic_footer = getActivity().getLayoutInflater().inflate(
		R.layout.listview_footer, null);
	lvDynamic_foot_more = (TextView) lvDynamic_footer
		.findViewById(R.id.listview_foot_more);
	lvDynamic_foot_progress = (ProgressBar) lvDynamic_footer
		.findViewById(R.id.listview_foot_progress);

	lvDynamicAdapter = new ListViewTweetAdapter(getActivity(),
		lvDynamicData, R.layout.tweet_listitem);
	mlvDynamic = (PullToRefreshListView) view
		.findViewById(R.id.dynamic_listview);

	mlvDynamic.addFooterView(lvDynamic_footer);// 添加底部视图 必须在setAdapter前
	mlvDynamic.setAdapter(lvDynamicAdapter);
	
	lvDynamic_footer.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View arg0) {
		loadLvDynamicData(type,pageIndex,mDynamicHandler);
	    }
	});
    }

    // 初始化控件数据
    private void initData() {
	mDynamicHandler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
		headButtonSwitch(DATA_LOAD_COMPLETE);
		if (msg.what > 0) {
		    List<Dynamic> ms = (List<Dynamic>) msg.obj;
		    if (pageIndex == 0) {
			lvDynamicData.clear();
		    }
		    lvDynamicData.addAll(ms);
		}
		if (msg.what < 0) {
		    lvDynamicAdapter.notifyDataSetChanged();
		    lvDynamic_foot_more.setText(R.string.load_error);
		} else if (msg.what == 0) {
		    lvDynamicAdapter.notifyDataSetChanged();
		    lvDynamic_foot_more.setText(R.string.load_empty);
		} else if (msg.what < 20) {
		    lvDynamicAdapter.notifyDataSetChanged();
		    lvDynamic_foot_more.setText(R.string.load_full);
		} else if (msg.what >= 20) {
		    lvDynamicAdapter.notifyDataSetChanged();
		    lvDynamic_foot_more.setText(R.string.load_more);
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
    private void loadLvDynamicData(final int type, final int pageIndex,
	    final Handler handler) {
	headButtonSwitch(DATA_LOAD_ING);
	new Thread() {
	    public void run() {
		Message msg = new Message();
		try {
		    DynamicJson mj = ((AppContext) getActivity()
			    .getApplication()).getDynamics(pageIndex, type);
		    
		    System.out.println(new Gson().toJson(mj));
		    
		    if (mj != null && mj.getIsSuccess()) {
			msg.what = mj.getRows().size();
			msg.obj = mj.getRows();
		    } else {
			// 没有找到好友信息
			msg.what = 0;
		    }
		} catch (AppException e) {
		    e.printStackTrace();
		    msg.what = -1;
		    msg.obj = e;
		}
		handler.sendMessage(msg);
	    }
	}.start();
    }
}
