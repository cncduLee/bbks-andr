package com.funger.bbks.bean;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;

public class MessageJson extends JsonResult {

    private Messages obj;
    private List<Messages> rows;

    public Messages getObj() {
	return obj;
    }

    public void setObj(Messages obj) {
	this.obj = obj;
    }

    public List<Messages> getRows() {
	return rows;
    }

    public void setRows(List<Messages> rows) {
	this.rows = rows;
    }

    public static MessageJson getBean(String jsonStr) {
	return new Gson().fromJson(jsonStr, MessageJson.class);
    }

    public static MessageJson getBean(InputStream inStream) {
	System.out.println("get--datas--->");
	return new Gson().fromJson(new InputStreamReader(inStream),
		MessageJson.class);
    }

}
