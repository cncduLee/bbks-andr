package com.funger.bbks.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.funger.bbks.MainActivity;
import com.funger.bbks.R;
import com.funger.bbks.api.Helper;
import com.funger.bbks.app.UIHelper;
import com.funger.bbks.bean.DuitangInfo;
import com.funger.bbks.ui.adapter.StaggeredAdapter;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.huewu.pla.lib.internal.PLA_AdapterView.OnItemClickListener;
import com.huewu.pla.lib.view.XListView;
import com.huewu.pla.lib.view.XListView.IXListViewListener;

public class BookHome extends Fragment implements IXListViewListener,OnItemClickListener {

    private String tag = "BookListFragment";
    
    private XListView xListView;
    private StaggeredAdapter mAdapter;

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
	View v = inflater.inflate(R.layout.fragment_booklist, null);

	xListView = (XListView) v.findViewById(R.id.xlist_booklist);
	xListView.setPullLoadEnable(true);
	xListView.setXListViewListener(this);
	xListView.setClickable(true);
	xListView.setOnItemClickListener(this);
	
	mAdapter = new StaggeredAdapter(getActivity(), xListView);
	UIHelper.bindHeader( ((MainActivity)getActivity()) , v, "书城");
	
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
	    String url = "http://www.duitang.com/album/1733789/masn/p/"
		    + pageindex + "/10/";
	    Log.d("MainActivity", "current url:" + url);
	    ContentTask task = new ContentTask(getView().getContext(), type);
	    task.execute(url);
	}
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	Log.d(tag, "onActivityCreated");
    }

    @Override
    public void onResume() {
	super.onResume();
	
	xListView.setAdapter(mAdapter);
	AddItemToContainer(currentPage, 2);
	
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

    class ContentTask extends AsyncTask<String, Integer, List<DuitangInfo>> {

	private Context mContext;
	private int mType = 1;

	public ContentTask(Context context, int type) {
	    super();
	    mContext = context;
	    mType = type;
	}

	@Override
	protected List<DuitangInfo> doInBackground(String... params) {
	    try {
		return parseNewsJSON(params[0]);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    return null;
	}

	@Override
	protected void onPostExecute(List<DuitangInfo> result) {
	    if (result == null)
		return;
	    
	    if (mType == 1) {
		// 在前面补上
		mAdapter.addItemTop(result);
		// 停止刷新
		xListView.stopRefresh();

	    } else if (mType == 2) {
		// 停止加载
		xListView.stopLoadMore();
		// 在后面追加
		mAdapter.addItemLast(result);
	    }
	    
	    Log.d(tag, "handle results...");
	}

	@Override
	protected void onPreExecute() {
	}

	public List<DuitangInfo> parseNewsJSON(String url) throws IOException {
	    List<DuitangInfo> duitangs = new ArrayList<DuitangInfo>();
	    String json = "";
	    if (Helper.checkConnection(mContext)) {
		try {
		    json = Helper.getStringFromUrl(url);

		} catch (IOException e) {
		    Log.e("IOException is : ", e.toString());
		    e.printStackTrace();
		    return duitangs;
		}
	    }
	    Log.d("MainActiivty", "json:" + json);

	    try {
		if (null != json) {
		    JSONObject newsObject = new JSONObject(json);
		    JSONObject jsonObject = newsObject.getJSONObject("data");
		    JSONArray blogsJson = jsonObject.getJSONArray("blogs");

		    for (int i = 0; i < blogsJson.length(); i++) {
			JSONObject newsInfoLeftObject = blogsJson
				.getJSONObject(i);
			DuitangInfo newsInfo1 = new DuitangInfo();
			newsInfo1
				.setAlbid(newsInfoLeftObject.isNull("albid") ? ""
					: newsInfoLeftObject.getString("albid"));
			newsInfo1
				.setIsrc(newsInfoLeftObject.isNull("isrc") ? ""
					: newsInfoLeftObject.getString("isrc"));
			newsInfo1.setMsg(newsInfoLeftObject.isNull("msg") ? ""
				: newsInfoLeftObject.getString("msg"));
			newsInfo1.setHeight(newsInfoLeftObject.getInt("iht"));
			duitangs.add(newsInfo1);
		    }
		}
	    } catch (JSONException e) {
		e.printStackTrace();
	    }
	    return duitangs;
	}
    }

	@Override
	public void onItemClick(PLA_AdapterView<?> parent, View view, int position,
			long id) {
		// TODO detail page
		UIHelper.showBookDetail(getActivity(), mAdapter.getmInfos().get(position));
	}
}
