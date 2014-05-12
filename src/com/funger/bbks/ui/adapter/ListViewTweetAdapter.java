package com.funger.bbks.ui.adapter;

import java.util.List;

import com.example.android.bitmapfun.util.ImageFetcher;
import com.funger.bbks.R;
import com.funger.bbks.api.URLs;
import com.funger.bbks.bean.Dynamic;
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
 * 动弹Adapter类
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class ListViewTweetAdapter extends BaseAdapter {
	private Context 					context;//运行上下文
	private List<Dynamic> 				listItems;//数据集合
	private LayoutInflater 				listContainer;//视图容器
	private int 						itemViewResource;//自定义项视图源
	private ImageFetcher mImageFetcher;

	static class ListItemView{				//自定义控件集合  
			public ImageView userface;  
	        public TextView username;  
		    public TextView date;  
		    public TextView content;
		    public TextView commentCount;
		    public TextView client;
		    public ImageView image;
		    
		    
	 }  

	/**
	 * 实例化Adapter
	 * @param context
	 * @param data
	 * @param resource
	 */
	public ListViewTweetAdapter(Context context, List<Dynamic> data,int resource) {
		this.context = context;			
		this.listContainer = LayoutInflater.from(context);	//创建视图容器并设置上下文
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
		//Log.d("method", "getView");
		
		//自定义视图
		ListItemView  listItemView = null;
		
		if (convertView == null) {
			//获取list_item布局文件的视图
			convertView = listContainer.inflate(this.itemViewResource, null);
			
			listItemView = new ListItemView();
			//获取控件对象
			listItemView.userface = (ImageView)convertView.findViewById(R.id.tweet_listitem_userface);
			listItemView.username = (TextView)convertView.findViewById(R.id.tweet_listitem_username);
			listItemView.content = (TextView)convertView.findViewById(R.id.tweet_listitem_content);
			listItemView.image= (ImageView)convertView.findViewById(R.id.tweet_listitem_image);
			listItemView.date= (TextView)convertView.findViewById(R.id.tweet_listitem_date);
			listItemView.commentCount= (TextView)convertView.findViewById(R.id.tweet_listitem_commentCount);
			listItemView.client= (TextView)convertView.findViewById(R.id.tweet_listitem_client);
			
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}else {
			listItemView = (ListItemView)convertView.getTag();
		}
				
		//设置文字和图片
		Dynamic tweet = listItems.get(position);
		listItemView.username.setText(tweet.getCreatedname());
		listItemView.username.setTag(tweet);//设置隐藏参数(实体类)
		
//		listItemView.content.setText(tweet.getBody());
//		listItemView.content.parseLinkText();
		listItemView.content.setText(tweet.getContent());
		listItemView.content.setTag(tweet);//设置隐藏参数(实体类)
		
		listItemView.date.setText(StringUtils.friendly_time(tweet.getCreateAt()));
		listItemView.commentCount.setText(tweet.getCount().toString());
		
		listItemView.client.setText("来自手机：");
		
		if(StringUtils.isEmpty(listItemView.client.getText().toString()))
			listItemView.client.setVisibility(View.GONE);
		else
			listItemView.client.setVisibility(View.VISIBLE);
		
		String faceURL = tweet.getCreatedAvatar();

		if(StringUtils.isEmpty(faceURL)){
		    listItemView.userface.setImageResource(R.drawable.widget_dface);
		}else if(faceURL.startsWith("http://localhost:8080")){
		    faceURL = faceURL.replace("http://localhost:8080", URLs.HTTP + URLs.HOST);
		    mImageFetcher.loadImage(faceURL, listItemView.userface);
		}else{
		    mImageFetcher.loadImage(faceURL, listItemView.userface);
		}
		
		listItemView.userface.setTag(tweet);
//		
//		String imgSmall = tweet.getImgSmall();
//		if(!StringUtils.isEmpty(imgSmall)) {
//			bmpManager.loadBitmap(imgSmall, listItemView.image, BitmapFactory.decodeResource(context.getResources(), R.drawable.image_loading));
//			listItemView.image.setOnClickListener(imageClickListener);
//			listItemView.image.setTag(tweet.getImgBig());
//			listItemView.image.setVisibility(ImageView.VISIBLE);
//		}else{
//			listItemView.image.setVisibility(ImageView.GONE);
//		}
		
		return convertView;
	}
	
}