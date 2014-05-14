package com.funger.bbks.fragment;

import com.funger.bbks.MainActivity;
import com.funger.bbks.R;
import com.funger.bbks.api.URLs;
import com.funger.bbks.app.AppContext;
import com.funger.bbks.app.UIHelper;
import com.funger.bbks.bean.BookJson;
import com.funger.bbks.fragment.BookHome.ContentTask;
import com.funger.bbks.ui.adapter.StaggeredAdapter;
import com.funger.bbks.ui.adapter.StaggeredEbookAdapter;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.huewu.pla.lib.internal.PLA_AdapterView.OnItemClickListener;
import com.huewu.pla.lib.view.XListView;
import com.huewu.pla.lib.view.XListView.IXListViewListener;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 
 * 浏览电子书
 * @TODO TODO
 * @AUTHOR:LPM[shuoli1990@gmail.com]
 * @DATE:2014-5-14
 * @VERSION:V_0.1
 */
public class Ebook extends Fragment implements IXListViewListener,OnItemClickListener {

    private String tag = "EbookListFragment";
    
    private XListView xListView;
    private StaggeredEbookAdapter mAdapter;

    private ContentTask task = new ContentTask(getActivity(), 2);;
    private int currentPage = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	super.onCreateView(inflater, container, savedInstanceState);
	View v = inflater.inflate(R.layout.fregment_ebook, null);

	xListView = (XListView) v.findViewById(R.id.xlist_ebooklist);
	xListView.setPullLoadEnable(true);
	xListView.setXListViewListener(this);
	xListView.setClickable(true);
	xListView.setOnItemClickListener(this);
	
	mAdapter = new StaggeredEbookAdapter(getActivity(), xListView);
	UIHelper.bindHeader( ((MainActivity)getActivity()) , v, "电子书店");
	
	return v;
    }

    /**
     * 添加内容
     * 
     * @param pageindex
     * @param type
     *            1为下拉刷新 2为加载更多
     */
    private void AddItemToContainer(int pageindex, int type) {
	if (task.getStatus() != Status.RUNNING) {
	    System.out.println("--task--");
	    ContentTask task = new ContentTask(getView().getContext(), type);
	    task.execute(pageindex,10);
	}
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	Log.d(tag, "onActivityCreated");
    }

    @Override
    public void onStart() {
	super.onResume();
	
	xListView.setAdapter(mAdapter);
	AddItemToContainer(++currentPage, 2);
	
	Log.d(tag, "onsResume ok...");
    }

    @Override
    public void onRefresh() {
	AddItemToContainer(++currentPage, 1);
    }

    @Override
    public void onLoadMore() {
	AddItemToContainer(++currentPage, 2);
    }

    class ContentTask extends AsyncTask<Integer, Integer, BookJson> {

	private Context mContext;
	private int mType;

	public ContentTask(Context context, int type) {
	    super();
	    this.mContext = context;
	    this.mType = type;
	}

	@Override
	protected BookJson doInBackground(Integer... params) {
	    try{
		int pageNo = params[0];
		int pageSize = params[1];
		return ((AppContext)getActivity().getApplication()).EBookFind(0,pageNo,pageSize);
	    }catch (Exception e) {
		e.printStackTrace();
		return null;
	    }
	}

	@Override
	protected void onPostExecute(BookJson result) {
	    if (result == null)
		return;
	    
	    if (mType == 1) {
		// 在前面补上
		mAdapter.addItemTop(result.getRows());
		// 停止刷新
		xListView.stopRefresh();

	    } else if (mType == 2) {
		// 停止加载
		xListView.stopLoadMore();
		// 在后面追加
		mAdapter.addItemLast(result.getRows());
	    }
	    
	    Log.d(tag, "handle results...");
	}

	@Override
	protected void onPreExecute() {
	}
    }

	@Override
	public void onItemClick(PLA_AdapterView<?> parent, View view, int position,
			long id) {
	    	if(position > 0){position--;}
		UIHelper.showBookDetail(getActivity(), mAdapter.getmInfos().get(position));
	}
}
