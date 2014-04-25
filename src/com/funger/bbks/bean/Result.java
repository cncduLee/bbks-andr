package com.funger.bbks.bean;

import java.io.Serializable;

public class Result extends BaseEntity{
    private static final long serialVersionUID = -5082095906316053642L;
    
    private long id;
    private String url;
    private String title;
    private String content;
    private String imagSrc;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getImagSrc() {
        return imagSrc;
    }
    public void setImagSrc(String imagSrc) {
        this.imagSrc = imagSrc;
    }
    
    
    
    
}
