package com.funger.bbks.fragment;

import com.funger.bbks.MainActivity;
import com.funger.bbks.R;
import com.funger.bbks.app.UIHelper;
import com.funger.ebook.ac.BookListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class LeftFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	// Main view
	View view = inflater.inflate(R.layout.fragment_left, null);

	// 回主页菜单
        LinearLayout mainPage = (LinearLayout) view.findViewById(R.id.left_mainPage);
        mainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showLeft();
            }
        });

	// search菜单
	LinearLayout search = (LinearLayout) view
		.findViewById(R.id.menu_search);
	search.setOnClickListener(new View.OnClickListener() {

	    public void onClick(View v) {
		FragmentTransaction ft = getActivity()
			.getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.center_frame, new Search());
		ft.commit();
		((MainActivity) getActivity()).showLeft();
//		UIHelper.ToastMessage(getActivity(), "for ebook test!");
//		Intent in = new Intent(getActivity(), BookListActivity.class);
//		getActivity().startActivity(in);
		
	    }
	});

	// 书城菜单
	LinearLayout bookhome = (LinearLayout) view
		.findViewById(R.id.menu_bookhome);
	bookhome.setOnClickListener(new View.OnClickListener() {
		@Override
        public void onClick(View v) {
            FragmentTransaction ft = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.center_frame, new BookHome());
            ft.commit();
            ((MainActivity) getActivity()).showLeft();

        }
	});
	
	// ebook菜单
	LinearLayout bookshelf = (LinearLayout) view
		.findViewById(R.id.menu_bookshelf);
	bookshelf.setOnClickListener(new View.OnClickListener() {

	    public void onClick(View v) {
//		FragmentTransaction ft = getActivity()
//			.getSupportFragmentManager().beginTransaction();
//		ft.replace(R.id.center_frame, new BookShelf());
//		ft.commit();
//		((MainActivity) getActivity()).showLeft();
		
		//UIHelper.ToastMessage(getActivity(), "for ebook test!");
		Intent in = new Intent(getActivity(), BookListActivity.class);
		getActivity().startActivity(in);
	    }
	});
	
	// help菜单
	LinearLayout help = (LinearLayout) view
		.findViewById(R.id.menu_help);
	help.setOnClickListener(new View.OnClickListener() {

	    public void onClick(View v) {
		FragmentTransaction ft = getActivity()
			.getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.center_frame, new Help());
		ft.commit();
		((MainActivity) getActivity()).showLeft();
	    }
	});
		
	// help菜单
	LinearLayout about = (LinearLayout) view
		.findViewById(R.id.menu_about);
	about.setOnClickListener(new View.OnClickListener() {

	    public void onClick(View v) {
		FragmentTransaction ft = getActivity()
			.getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.center_frame, new About());
		ft.commit();
		((MainActivity) getActivity()).showLeft();
	    }
	});
	
	return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
    }

}
