package com.funger.bbks.ui.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bitmapfun.util.ImageFetcher;
import com.funger.bbks.R;
import com.funger.bbks.api.URLs;
import com.funger.bbks.app.AppContext;
import com.funger.bbks.app.UIHelper;
import com.funger.bbks.bean.Messages;
import com.funger.bbks.common.StringUtils;

/**
 * 用户留言Adapter类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class ListViewMessageAdapter extends BaseAdapter {
	private Context context;// 运行上下文
	private List<Messages> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private int itemViewResource;// 自定义项视图源
	private ImageFetcher mImageFetcher;


	static class ListItemView { // 自定义控件集合
		public ImageView userface;
		public TextView username;
		public TextView date;
		public TextView messageCount;
		public TextView client;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public ListViewMessageAdapter(Context context, List<Messages> data,
			int resource) {
		this.context = context;
		this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
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
			listItemView.userface = (ImageView) convertView
					.findViewById(R.id.message_listitem_userface);
			listItemView.username = (TextView) convertView
					.findViewById(R.id.message_listitem_username);
			listItemView.date = (TextView) convertView
					.findViewById(R.id.message_listitem_date);
			listItemView.messageCount = (TextView) convertView
					.findViewById(R.id.message_listitem_messageCount);
			listItemView.client = (TextView) convertView
					.findViewById(R.id.message_listitem_client);

			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		// 设置文字和图片
		Messages msg = listItems.get(position);
		
		listItemView.username.setText("发送给："+msg.getToname());
		listItemView.username.setTag(msg);// 设置隐藏参数(实体类)
		
		listItemView.date.setText(StringUtils.friendly_time(msg.getCreatAt().toString()));
		listItemView.messageCount.setText("共有 0 条留言");

		listItemView.client.setText("来自:手机");
		
		if (StringUtils.isEmpty(listItemView.client.getText().toString()))
			listItemView.client.setVisibility(View.GONE);
		else
			listItemView.client.setVisibility(View.VISIBLE);

		String faceURL = msg.getToavatar();
		
		if(StringUtils.isEmpty(faceURL)){
		    listItemView.userface.setImageResource(R.drawable.widget_dface);
		}else if(faceURL.startsWith("http://localhost:8080")){
		    faceURL = faceURL.replace("http://localhost:8080", URLs.HTTP + URLs.HOST);
		    mImageFetcher.loadImage(faceURL, listItemView.userface);
		}else{
		    mImageFetcher.loadImage(faceURL, listItemView.userface);
		}
		
		listItemView.userface.setTag(msg);

		return convertView;
	}
}