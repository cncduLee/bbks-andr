package com.funger.bbks.bean;

import java.util.Date;

public class Dynamic extends BaseEntity {

    private Long id;
    private Long creatBy;
    private Date createAt;
    private Date updateAt;
    private String content;
    private Integer count;// 回复数量
    private String createdname;
    private String createdAvatar;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getCreatBy() {
        return creatBy;
    }
    public void setCreatBy(Long creatBy) {
        this.creatBy = creatBy;
    }
    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    public Date getUpdateAt() {
        return updateAt;
    }
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    public String getCreatedname() {
        return createdname;
    }
    public void setCreatedname(String createdname) {
        this.createdname = createdname;
    }
    public String getCreatedAvatar() {
        return createdAvatar;
    }
    public void setCreatedAvatar(String createdAvatar) {
        this.createdAvatar = createdAvatar;
    }
    
    

}
