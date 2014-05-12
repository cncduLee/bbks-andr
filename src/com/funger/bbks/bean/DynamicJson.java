package com.funger.bbks.bean;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;

public class DynamicJson extends JsonResult{
    private static final long serialVersionUID = 1L;
    private Dynamic obj;
    private List<Dynamic> rows;
    public Dynamic getObj() {
        return obj;
    }
    public void setObj(Dynamic obj) {
        this.obj = obj;
    }
    public List<Dynamic> getRows() {
        return rows;
    }
    public void setRows(List<Dynamic> rows) {
        this.rows = rows;
    }
    
    
    public static DynamicJson getBean(String jsonStr){
	return new Gson().fromJson(jsonStr, DynamicJson.class);
    }
    
    public static DynamicJson getBean(InputStream inStream){
	return new Gson().fromJson(new InputStreamReader(inStream), DynamicJson.class);
    }

}
