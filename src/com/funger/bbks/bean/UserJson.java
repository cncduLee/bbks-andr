package com.funger.bbks.bean;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;

public class UserJson extends JsonResult{

    private User obj;
    private List<User> rows;

    public User getObj() {
        return obj;
    }

    public void setObj(User obj) {
        this.obj = obj;
    }

    public List<User> getRows() {
        return rows;
    }

    public void setRows(List<User> rows) {
        this.rows = rows;
    }
    
    public static UserJson getBean(String jsonStr){
	return new Gson().fromJson(jsonStr, UserJson.class);
    }
    
    public static UserJson getBean(InputStream inStream){
	System.out.println("get--datas--->");
	return new Gson().fromJson(new InputStreamReader(inStream), UserJson.class);
    }
    
}
