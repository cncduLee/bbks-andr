package com.funger.bbks.ui.adapter;

import java.util.List;

import com.example.android.bitmapfun.util.ImageFetcher;
import com.funger.bbks.R;
import com.funger.bbks.bean.Book;
import com.funger.bbks.bean.Result;
import com.funger.bbks.common.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListViewSearchAdapter extends BaseAdapter {
    private Context 			context;// 运行上下文
    private List<Book> 		        listItems;// 数据集合
    private LayoutInflater 		listContainer;// 视图容器
    private int 			itemViewResource;//自定义项视图源
    private ImageFetcher 		mImageFetcher;

    static class ListItemView {
	// 自定义控件集合
	public TextView title;
	public TextView content;
	public ImageView img;
    }

    /**
     * 实例化Adapter
     * 
     * @param context
     * @param data
     * @param resource
     */
    public ListViewSearchAdapter(Context context, List<Book> data,
	    int resource) {
	this.context = context;
	this.listContainer = LayoutInflater.from(context);// 创建视图容器并设置上下文
	this.itemViewResource = resource;
	this.listItems = data;
	
	mImageFetcher = new ImageFetcher(context, 240);
	mImageFetcher.setLoadingImage(R.drawable.empty_photo);
    }

    public int getCount() {
	return listItems.size();
    }

    public Object getItem(int arg0) {
	return null;
    }

    public long getItemId(int arg0) {
	return 0;
    }

    /**
     * ListView Item设置
     */
    public View getView(int position, View convertView, ViewGroup parent) {
	// Log.d("method", "getView");

	// 自定义视图
	ListItemView listItemView = null;

	if (convertView == null) {
	    // 获取list_item布局文件的视图
	    convertView = listContainer.inflate(this.itemViewResource, null);

	    listItemView = new ListItemView();
	    // 获取控件对象
	    listItemView.title = (TextView) convertView
		    .findViewById(R.id.book_list_title);
	    listItemView.content = (TextView) convertView
		    .findViewById(R.id.book_list_content);
	    listItemView.img = (ImageView) convertView
		    .findViewById(R.id.book_list_image);

	    // 设置控件集到convertView
	    convertView.setTag(listItemView);
	} else {
	    listItemView = (ListItemView) convertView.getTag();
	}

	// 设置文字和图片
	Book res = listItems.get(position);
	listItemView.title.setText(res.getBookName());
	listItemView.title.setTag(res);// 设置隐藏参数(实体类)
	listItemView.content.setText(res.getOutline());
	mImageFetcher.loadImage(res.getCoverPic(), listItemView.img);

	return convertView;
    }

}
