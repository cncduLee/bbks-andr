package com.funger.bbks.bean;

import java.io.Serializable;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Intro descrption here
 * @author Lee [shouli1990@gmail.com]
 * @Version V0.0.1
 * @Date 2014-5-2
 * @since 下午6:54:05
 */
public class JsonResult implements Serializable {
    protected Integer code;
    protected String message;
    protected Boolean isSuccess;
    protected Long user_id;

    public JsonResult() {
    }

    public Long getUser_id() {
	return user_id;
    }

    public void setUser_id(Long user_id) {
	this.user_id = user_id;
    }

    public Integer getCode() {
	return code;
    }

    public void setCode(Integer code) {
	this.code = code;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public Boolean getIsSuccess() {
	return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
	this.isSuccess = isSuccess;
    }

  
}
