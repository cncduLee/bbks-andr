package com.funger.bbks.fragment;


import com.funger.bbks.MainActivity;
import com.funger.bbks.R;
import com.funger.bbks.app.UIHelper;
import com.funger.bbks.common.StringUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class Search extends Fragment implements OnClickListener{
    private String tag = "SearchFragment";

    private Button searchBth;
    private EditText searchText;
    private InputMethodManager imm;
    private final static int DATA_LOAD_ING = 0x001;
    private final static int DATA_LOAD_COMPLETE = 0x002;
    private ProgressBar progressBar;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

	View view = inflater.inflate(R.layout.fragment_search, null);
	//bind header
	UIHelper.bindHeader(((MainActivity) getActivity()), view, "搜索");
    	imm = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);

		
	searchBth = (Button) view.findViewById(R.id.search_btn);
	searchText = (EditText) view.findViewById(R.id.search_editer);
	progressBar = (ProgressBar) view.findViewById(R.id.search_progress);
	
	searchBth.setOnClickListener(this);
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
	
	return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	
    }

    @Override
    public void onClick(View v) {
	//获取input str
	String searchStr = searchText.getText().toString();
	if(StringUtils.isEmpty(searchStr)){
		UIHelper.ToastMessage(getActivity(), "请输入搜索内容");
		return;
	}
	Log.d(tag, "get search string:"+searchStr);
	headButtonSwitch(DATA_LOAD_ING);
	//TODO
    }
    
    /**
     * 头部按钮展示
     * 
     * @param type
     */
    private void headButtonSwitch(int type) {
	switch (type) {
	case DATA_LOAD_ING:
	    searchBth.setClickable(false);
	    progressBar.setVisibility(View.VISIBLE);
	    break;
	case DATA_LOAD_COMPLETE:
	    searchBth.setClickable(true);
	    progressBar.setVisibility(View.GONE);
	    break;
	}
    }
}
