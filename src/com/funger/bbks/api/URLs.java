package com.funger.bbks.api;

import java.io.Serializable;
import java.net.URL;
import java.net.URLEncoder;

import com.funger.bbks.common.StringUtils;


/**
 * 
 * @author LPM
 *
 */
public class URLs implements Serializable{
	public final static String HOST = "192.168.1.3:8080";//10.0.2.2 192.168.1.3  www.oschina.net
	public final static String HTTP = "http://";
	public final static String HTTPS = "https://";
	
	
	
	public final static String API = HTTP + HOST +  "/bbks/api";
	
	//book
	public final static String API_BOOK_FIND = API + "/book/find";
	public final static String API_BOOK_SEARCH = API + "/book/search";
	
	//user
	public final static String API_USER_LOGIN = API + "/user/login";
	
	
	
	public final static int URL_OBJ_TYPE_OTHER = 0x000;
	public final static int URL_OBJ_TYPE_BOOK = 0x001;
	public final static int URL_OBJ_TYPE_MY = 0x002;
	
	    
	private int objId;
	private String objKey = "";
	private int objType;
	
	/**
	 * 转化URL为URLs实体
	 * @param path
	 * @return 不能转化的链接返回null
	 */
	public final static URLs parseURL(String path){
		if(StringUtils.isEmpty(path))return null;
		path = formatURL(path);
		URLs urls = null;
		String objId = "";
		try {
			URL url = new URL(path);
		}catch (Exception e) {
			urls = null;
		}
		return urls;
	}
	/**
	 * 对URL进行格式处理
	 * @param path
	 * @return
	 */
	private final static String formatURL(String path) {
		if(path.startsWith("http://") || path.startsWith("https://"))
			return path;
		return "http://" + URLEncoder.encode(path);
	}


	public int getObjId() {
		return objId;
	}


	public void setObjId(int objId) {
		this.objId = objId;
	}


	public String getObjKey() {
		return objKey;
	}


	public void setObjKey(String objKey) {
		this.objKey = objKey;
	}


	public int getObjType() {
		return objType;
	}


	public void setObjType(int objType) {
		this.objType = objType;
	}	
	
	
	
	
}
