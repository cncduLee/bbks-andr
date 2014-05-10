package com.funger.bbks.bean;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class BookJson extends JsonResult{
    private Book obj;
    private List<Book> rows;
    
    public Book getObj() {
        return obj;
    }
    public void setObj(Book obj) {
        this.obj = obj;
    }
    public List<Book> getRows() {
        return rows;
    }
    public void setRows(List<Book> rows) {
        this.rows = rows;
    }
    
    public static BookJson getBean(String jsonStr){
	return new Gson().fromJson(jsonStr, BookJson.class);
    }
    
    public static BookJson getBean(InputStream inStream){
	System.out.println("get--datas--->");
	return new Gson().fromJson(new InputStreamReader(inStream), BookJson.class);
    }
}
