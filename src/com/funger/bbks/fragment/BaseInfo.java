package com.funger.bbks.fragment;

import com.example.android.bitmapfun.util.ImageFetcher;
import com.funger.bbks.MainActivity;
import com.funger.bbks.R;
import com.funger.bbks.api.URLs;
import com.funger.bbks.app.AppContext;
import com.funger.bbks.app.UIHelper;
import com.funger.bbks.bean.User;
import com.funger.bbks.common.StringUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseInfo extends Fragment {
    private ImageFetcher mImageFetcher;
    private TextView flowers,funs,joinTime,description,address,username;
    private ImageView avatar,gender;
    
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	
	View view = inflater.inflate(R.layout.fragment_baseinfo, null);
	
	//bind header
	UIHelper.bindHeader(((MainActivity) getActivity()), view, "基本信息");
	
	return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	
	mImageFetcher = new ImageFetcher(getActivity(), 240);
	mImageFetcher.setLoadingImage(R.drawable.empty_photo);
	
	this.initView(getView());
	this.initData();
    }


    private void initView(View view) {
	this.address = (TextView) view.findViewById(R.id.user_info_address);
	this.description = (TextView) view.findViewById(R.id.user_info_description);
	this.funs = (TextView) view.findViewById(R.id.user_info_fans);
	this.flowers = (TextView) view.findViewById(R.id.user_info_followers);
	this.avatar =  (ImageView) view.findViewById(R.id.user_info_userface);
	this.gender = (ImageView) view.findViewById(R.id.user_info_gender);
	this.username = (TextView) view.findViewById(R.id.user_info_username);
    }
    

    private void initData() {
	User user = ((AppContext)getActivity().getApplication()).getSession();
	if(user == null){
	    UIHelper.showLogin(getActivity());
	}else{
	    
	    this.address.setText(user.getAddress());
	    this.description.setText(user.getDescription());
	    this.flowers.setText(user.getFloweds().toString());
	    this.funs.setText(user.getFlowings().toString());
	    this.username.setText(user.getUsername());
	    
	    if(StringUtils.isEmpty(user.getAvatar())){
		avatar.setImageResource(R.drawable.widget_dface);
	    }else if(user.getAvatar().startsWith("http://localhost:8080")){
		user.setAvatar(user.getAvatar().replace("http://localhost:8080", URLs.HTTP + URLs.HOST));
	    }
	    
	    mImageFetcher.loadImage(user.getAvatar(), avatar);
	    
	    if(user.getGender() == null || !user.getGender().equals("1")){
		gender.setImageResource(R.drawable.widget_gender_man);
	    }else{
		gender.setImageResource(R.drawable.widget_gender_woman);
	    }
	}
    }
}
