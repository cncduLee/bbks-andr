package com.funger.bbks.bean;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;

public class FriendJson extends JsonResult{
    private Friend obj;
    private List<Friend> rows;
    public Friend getObj() {
        return obj;
    }
    public void setObj(Friend obj) {
        this.obj = obj;
    }
    public List<Friend> getRows() {
        return rows;
    }
    public void setRows(List<Friend> rows) {
        this.rows = rows;
    }
    
    public static FriendJson getBean(String jsonStr){
	return new Gson().fromJson(jsonStr, FriendJson.class);
    }
    
    public static FriendJson getBean(InputStream inStream){
	System.out.println("get--datas--->");
	return new Gson().fromJson(new InputStreamReader(inStream), FriendJson.class);
    }
}
