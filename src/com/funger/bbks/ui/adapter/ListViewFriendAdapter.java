package com.funger.bbks.ui.adapter;

import java.util.List;

import com.example.android.bitmapfun.util.ImageFetcher;
import com.funger.bbks.R;
import com.funger.bbks.api.URLs;
import com.funger.bbks.bean.Friend;
import com.funger.bbks.common.StringUtils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 用户粉丝、关注Adapter类
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-5-24
 */
public class ListViewFriendAdapter extends BaseAdapter {
	private Context 					context;//运行上下文
	private List<Friend> 				listItems;//数据集合
	private LayoutInflater 				listContainer;//视图容器
	private int 						itemViewResource;//自定义项视图源 
	private ImageFetcher mImageFetcher;
	
	static class ListItemView{				//自定义控件集合  
            public ImageView face;  
            public ImageView gender;
            public TextView name;  
            public TextView description;
	}  

	/**
	 * 实例化Adapter
	 * @param context
	 * @param data
	 * @param resource
	 */
	public ListViewFriendAdapter(Context context, List<Friend> data,int resource) {
		this.context = context;			
		this.listContainer = LayoutInflater.from(context);	//创建视图容器并设置上下文
		this.itemViewResource = resource;
		this.listItems = data;
		mImageFetcher = new ImageFetcher(context, 240);
		mImageFetcher.setLoadingImage(R.drawable.empty_photo);
		
		//this.bmpManager = new BitmapManager(BitmapFactory.decodeResource(context.getResources(), R.drawable.widget_dface_loading));
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
		//Log.d("method", "getView");
		
		//自定义视图
		ListItemView  listItemView = null;
		
		if (convertView == null) {
			//获取list_item布局文件的视图
			convertView = listContainer.inflate(this.itemViewResource, null);
			
			listItemView = new ListItemView();
			//获取控件对象
			listItemView.name = (TextView)convertView.findViewById(R.id.friend_listitem_name);
			listItemView.description = (TextView)convertView.findViewById(R.id.friend_listitem_expertise);
			listItemView.face = (ImageView)convertView.findViewById(R.id.friend_listitem_userface);
			listItemView.gender = (ImageView)convertView.findViewById(R.id.friend_listitem_gender);
			
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}else {
			listItemView = (ListItemView)convertView.getTag();
		}	
		
		//设置文字和图片
		Friend friend = listItems.get(position);
		
		listItemView.name.setText(friend.getUserName());
		listItemView.name.setTag(friend);//设置隐藏参数(实体类)
		listItemView.description.setText(friend.getDescription());
		
		if(friend.getGender().equals("0"))
			listItemView.gender.setImageResource(R.drawable.widget_gender_man);
		else
			listItemView.gender.setImageResource(R.drawable.widget_gender_woman);
		
		String faceURL = friend.getFace();
		
		if(StringUtils.isEmpty(faceURL)){
		    listItemView.face.setImageResource(R.drawable.widget_dface);
		}else if(faceURL.startsWith("http://localhost:8080")){
		    faceURL = faceURL.replace("http://localhost:8080", URLs.HTTP + URLs.HOST);
		    mImageFetcher.loadImage(faceURL, listItemView.face);
		}else{
		    mImageFetcher.loadImage(faceURL, listItemView.face);
		}
		
		listItemView.face.setTag(friend);
		
		return convertView;
	}
}