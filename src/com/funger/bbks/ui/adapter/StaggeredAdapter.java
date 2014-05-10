package com.funger.bbks.ui.adapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.bitmapfun.util.ImageFetcher;
import com.funger.bbks.R;
import com.funger.bbks.bean.Book;
import com.funger.bbks.view.ScaleImageView;
import com.huewu.pla.lib.view.XListView;

public class StaggeredAdapter extends BaseAdapter {
    private Context mContext;
    private LinkedList<Book> mInfos;
    private XListView mListView;
    private ImageFetcher mImageFetcher;

    public StaggeredAdapter(Context context, XListView xListView) {
	
	mInfos = new LinkedList<Book>();
	
	this.mContext = context;
	this.mListView = xListView;

	mImageFetcher = new ImageFetcher(context, 240);
	mImageFetcher.setLoadingImage(R.drawable.empty_photo);
	
    }

    public StaggeredAdapter(Context context, XListView xListView,
	    ImageFetcher imageFetcher) {
	mInfos = new LinkedList<Book>();
	
	mContext = context;
	mListView = xListView;
	
	this.mImageFetcher = imageFetcher;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

	ViewHolder holder;
	Book bookInfo = mInfos.get(position);

	if (convertView == null) {
	    LayoutInflater layoutInflator = LayoutInflater.from(parent.getContext());
	    
	    convertView = layoutInflator.inflate(R.layout.infos_list, null);
	    holder = new ViewHolder();
	    
	    holder.imageView = (ScaleImageView) convertView
		    .findViewById(R.id.news_pic);
	    holder.contentView = (TextView) convertView
		    .findViewById(R.id.news_title);
	    holder.timeView = (TextView) convertView.findViewById(R.id.news_time);
	    convertView.setTag(holder);
	}

	holder = (ViewHolder) convertView.getTag();
//	holder.imageView.setImageWidth(bookInfo.getWidth());
//	holder.imageView.setImageHeight(bookInfo.getHeight());
	holder.contentView.setText(bookInfo.getBookName());
	holder.timeView.setText(bookInfo.getAuthor());
	mImageFetcher.loadImage(bookInfo.getCoverPic(), holder.imageView);
	
	return convertView;
    }

    class ViewHolder {
	ScaleImageView imageView;
	TextView contentView;
	TextView timeView;
    }

    @Override
    public int getCount() {
	return mInfos.size();
    }

    @Override
    public Object getItem(int arg0) {
	return mInfos.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
	return 0;
    }

    public void addItemLast(List<Book> datas) {
	mInfos.addAll(datas);
	notifyDataSetChanged();
    }

    public void addItemTop(List<Book> datas) {
	for (Book info : datas) {
	    mInfos.addFirst(info);
	}
	notifyDataSetChanged();
    }

	public LinkedList<Book> getmInfos() {
		return mInfos;
	}
    
    
}
