package com.funger.bbks.bean;

import java.io.Serializable;

public class DuitangInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int height;
	private String albid = "";
	private String msg = "";
	private String isrc = "";

	public int getWidth(){
		return 200;
	}
	public String getAlbid() {
		return albid;
	}

	public void setAlbid(String albid) {
		this.albid = albid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getIsrc() {
		return isrc;
	}

	public void setIsrc(String isrc) {
		this.isrc = isrc;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
