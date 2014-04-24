package com.funger.bbks.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.funger.bbks.MainActivity;
import com.funger.bbks.R;
import com.funger.bbks.app.UIHelper;

public class RightFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.fragment_right, null);

	// 回主页菜单
    LinearLayout mainPage = (LinearLayout) view.findViewById(R.id.right_mainPage);
    mainPage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity) getActivity()).showRight();

        }
    });
	
	// baseInfo menu
	LinearLayout baseinfo = (LinearLayout) view
		.findViewById(R.id.menu_baseinfo);
	baseinfo.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View arg0) {
		FragmentTransaction ft = getActivity()
			.getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.center_frame, new BaseInfo());
		ft.commit();

		((MainActivity) getActivity()).showRight();
	    }
	});

	// dynamic menu
	LinearLayout dynamic = (LinearLayout) view
		.findViewById(R.id.menu_dynamic);
	dynamic.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View arg0) {
		FragmentTransaction ft = getActivity()
			.getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.center_frame, new Dynamic());
		ft.commit();

		((MainActivity) getActivity()).showRight();
	    }
	});

	// message menu
	LinearLayout message = (LinearLayout) view
		.findViewById(R.id.menu_message);
	message.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View arg0) {
		FragmentTransaction ft = getActivity()
			.getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.center_frame, new Message());
		ft.commit();

		((MainActivity) getActivity()).showRight();
	    }
	});

	// social menu
	LinearLayout social = (LinearLayout) view
		.findViewById(R.id.menu_social);
	social.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View arg0) {
		FragmentTransaction ft = getActivity()
			.getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.center_frame, new Social());
		ft.commit();

		((MainActivity) getActivity()).showRight();
	    }
	});
	
	// at menu
	LinearLayout at = (LinearLayout) view
		.findViewById(R.id.menu_at);
	at.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View arg0) {
		FragmentTransaction ft = getActivity()
			.getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.center_frame, new Setting());
		ft.commit();
		
		((MainActivity) getActivity()).showRight();
	    }
	});
		
	
	return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);

    }
}
