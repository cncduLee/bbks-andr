package com.funger.bbks.fragment;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.funger.bbks.MainActivity;
import com.funger.bbks.R;
import com.funger.bbks.app.AppContext;
import com.funger.bbks.app.UIHelper;
import com.funger.bbks.bean.Book;
import com.funger.bbks.bean.DuitangInfo;
import com.funger.bbks.bean.Result;
import com.funger.bbks.common.StringUtils;
import com.funger.bbks.ui.adapter.ListViewSearchAdapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Search extends Fragment{
    private String tag = "SearchFragment";

    private Button searchBth;
    private EditText searchText;
    private ListView listView;
    
    private View lvSearch_footer;
    private TextView lvSearch_foot_more;
    private ProgressBar lvSearch_foot_progress;
    
    private ListViewSearchAdapter lvAdapter;
    
    private List<Book> data = new ArrayList<Book>();
    private InputMethodManager imm;
    private String curSearchContent;
    private Handler handler;
    
    private final static int DATA_LOAD_ING = 0x001;
    private final static int DATA_LOAD_COMPLETE = 0x002;
    private ProgressBar progressBar;
    private static int pageIndex = 1;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

	View view = inflater.inflate(R.layout.fragment_search, null);
	//bind header
	UIHelper.bindHeader(((MainActivity) getActivity()), view, "搜索");
    	
    	
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
	lvSearch_footer.setVisibility(View.VISIBLE);

	switch (type) {
	case DATA_LOAD_ING:
	    searchBth.setClickable(false);
	    progressBar.setVisibility(View.VISIBLE);
	    lvSearch_foot_progress.setVisibility(View.VISIBLE);
	    lvSearch_foot_more.setText(R.string.load_ing);

	    break;
	case DATA_LOAD_COMPLETE:
	    searchBth.setClickable(true);
	    progressBar.setVisibility(View.GONE);
	    lvSearch_foot_progress.setVisibility(View.GONE);
	    break;
	}
    }

    private void initView(View view){
    	imm = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
    	lvAdapter = new ListViewSearchAdapter(getActivity(), data, R.layout.search_list_item);
	
	searchBth = (Button) view.findViewById(R.id.search_btn);
	searchText = (EditText) view.findViewById(R.id.search_editer);
	progressBar = (ProgressBar) view.findViewById(R.id.search_progress);
	listView = (ListView) view.findViewById(R.id.search_list_view); 
	
	lvSearch_footer = getActivity().getLayoutInflater().inflate(R.layout.listview_footer, null);
    	lvSearch_foot_more = (TextView)lvSearch_footer.findViewById(R.id.listview_foot_more);
    	lvSearch_foot_progress = (ProgressBar)lvSearch_footer.findViewById(R.id.listview_foot_progress);
    	
	listView.addFooterView(lvSearch_footer);
	listView.setAdapter(lvAdapter);
	lvSearch_footer.setVisibility(View.GONE);
	
	searchBth.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		searchText.clearFocus();
		//获取input str
		curSearchContent = searchText.getText().toString();
		loadSearchResult(1, handler);
		pageIndex = 1;
	    }
	});
	searchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		public void onFocusChange(View v, boolean hasFocus) {
			if(hasFocus){  
				imm.showSoftInput(v, 0);  
	        }  
	        else{  
	            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  
	        }  
		}
	}); 
    	listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
		    int position, long id) {
		// 点击底部栏无效
		if (view == lvSearch_footer){
		    loadSearchResult(++pageIndex, handler);
		    return;
		}
		    

		Book res = null;
		if (view instanceof TextView) {
		    res = (Book) view.getTag();
		} else {
		    TextView title = (TextView) view
			    .findViewById(R.id.book_list_title);
		    res = (Book) title.getTag();
		}
		if (res == null)
		    return;
		
		// 跳转
		UIHelper.showBookDetail(getActivity(), res);
	    }
	});
    }
    
    private void initData() {
	handler = new Handler() {
	    public void handleMessage(Message msg) {
		headButtonSwitch(DATA_LOAD_COMPLETE);
		if(msg.what > 0){
		    List<Book> datas = (List<Book>) msg.obj;
		    data.clear();
		    data.addAll(datas);
		    lvAdapter.notifyDataSetChanged();
		    if(msg.what > 10){
			lvSearch_foot_more.setText(R.string.load_full);	
		    }else{
			lvSearch_foot_more.setText(R.string.load_more);
		    }
		    
		}else{
		    lvSearch_foot_more.setText(R.string.load_error);
		}
		
	    }
	};
    }
    
    private void loadSearchResult(final int pageIndex, final Handler handler) {
	if (StringUtils.isEmpty(curSearchContent)) {
	    UIHelper.ToastMessage(getActivity(), "请输入搜索内容");
	    return;
	}
	headButtonSwitch(DATA_LOAD_ING);
	listView.setVisibility(ListView.VISIBLE);
	new Thread() {
	    public void run() {
		Message msg = new Message();
		try {
		    // SearchList searchList =
		    // ((AppContext)getApplication()).getSearchList(catalog,
		    // curSearchContent, pageIndex, 20);
		    // 查询数据
		    List<Book> data = ((AppContext)getActivity().getApplication()).bookSearch(pageIndex,curSearchContent).getRows();
		    if(data != null){
			msg.what = data.size();
		    }else{
			msg.what = -1;
		    }
		    msg.obj = data;
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
