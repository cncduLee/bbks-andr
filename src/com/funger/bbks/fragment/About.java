package com.funger.bbks.fragment;

import com.funger.bbks.MainActivity;
import com.funger.bbks.R;
import com.funger.bbks.app.UIHelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class About extends Fragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	
	View view = inflater.inflate(R.layout.fragment_about, null);
	
	//bind header
	UIHelper.bindHeader(((MainActivity) getActivity()), view, "关于");
	
	return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);

    }
}
