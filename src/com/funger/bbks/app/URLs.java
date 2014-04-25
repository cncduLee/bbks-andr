package com.funger.bbks.app;

import java.io.Serializable;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.funger.bbks.common.StringUtils;

public class URLs implements Serializable {
    public final static String HOST = "192.168.1.213";// 192.168.1.213
							// www.oschina.net
    public final static String HTTP = "http://";
    public final static String HTTPS = "https://";

    private final static String URL_SPLITTER = "/";
    private final static String URL_UNDERLINE = "_";

    private final static String URL_API_HOST = HTTP + HOST + URL_SPLITTER;

    private final static String URL_HOST = "oschina.net";
    private final static String URL_WWW_HOST = "www." + URL_HOST;
    private final static String URL_MY_HOST = "my." + URL_HOST;

    private final static String URL_TYPE_BOOK = URL_WWW_HOST + URL_SPLITTER + "book" + URL_SPLITTER;
    private final static String URL_TYPE_MY = URL_WWW_HOST + URL_SPLITTER + "book" + URL_SPLITTER;

    
    public final static int URL_OBJ_TYPE_OTHER = 0x000;
    public final static int URL_OBJ_TYPE_BOOK = 0x001;
    public final static int URL_OBJ_TYPE_MY = 0x002;

    
    private int objId;
    private String objKey = "";
    private int objType;

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

    /**
     * 转化URL为URLs实体
     * 
     * @param path
     * @return 不能转化的链接返回null
     */
    public final static URLs parseURL(String path) {
	if (StringUtils.isEmpty(path))
	    return null;
	path = formatURL(path);
	URLs urls = null;
	String objId = "";
	try {
	    URL url = new URL(path);
	    // 站内链接
	    if (url.getHost().contains(URL_HOST)) {
		urls = new URLs();
		// www
		if (path.contains(URL_WWW_HOST)) {
		    // 图书
		    if (path.contains(URL_TYPE_BOOK)) {
			objId = parseObjId(path, URL_TYPE_BOOK);
			urls.setObjId(StringUtils.toInt(objId));
			urls.setObjType(URL_OBJ_TYPE_BOOK);
		    }else if(path.contains(URL_TYPE_MY)){
			//my
			objId = parseObjId(path, URL_TYPE_MY);
			urls.setObjId(StringUtils.toInt(objId));
			urls.setObjType(URL_OBJ_TYPE_MY);
		    }
		    // other
		    else {
			urls.setObjKey(path);
			urls.setObjType(URL_OBJ_TYPE_OTHER);
		    }
		}
	    }else{
		urls.setObjKey(path);
		urls.setObjType(URL_OBJ_TYPE_OTHER);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    urls = null;
	}
	return urls;
    }

    /**
     * 解析url获得objId
     * 
     * @param path
     * @param url_type
     * @return
     */
    private final static String parseObjId(String path, String url_type) {
	String objId = "";
	int p = 0;
	String str = "";
	String[] tmp = null;
	p = path.indexOf(url_type) + url_type.length();
	str = path.substring(p);
	if (str.contains(URL_SPLITTER)) {
	    tmp = str.split(URL_SPLITTER);
	    objId = tmp[0];
	} else {
	    objId = str;
	}
	return objId;
    }

    /**
     * 解析url获得objKey
     * 
     * @param path
     * @param url_type
     * @return
     */
    private final static String parseObjKey(String path, String url_type) {
	path = URLDecoder.decode(path);
	String objKey = "";
	int p = 0;
	String str = "";
	String[] tmp = null;
	p = path.indexOf(url_type) + url_type.length();
	str = path.substring(p);
	if (str.contains("?")) {
	    tmp = str.split("?");
	    objKey = tmp[0];
	} else {
	    objKey = str;
	}
	return objKey;
    }

    /**
     * 对URL进行格式处理
     * 
     * @param path
     * @return
     */
    private final static String formatURL(String path) {
	if (path.startsWith("http://") || path.startsWith("https://"))
	    return path;
	return "http://" + URLEncoder.encode(path);
    }
}
