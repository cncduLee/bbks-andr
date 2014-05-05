package com.funger.bbks.bean;

import java.io.Serializable;

/**
 * @DESC: 基础对象
 * @TODO TODO
 * @AUTHOR:LPM[shuoli1990@gmail.com]
 * @DATE:2014-5-5
 * @VERSION:V_0.1
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected Integer code;
    protected String message;
    protected Boolean isSuccess;
    
    public BaseEntity() {}
    public BaseEntity(Boolean isSuccess) {
	this.isSuccess = isSuccess;
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
