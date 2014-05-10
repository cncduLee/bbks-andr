package com.funger.bbks.bean;

import java.util.Date;

public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private Long id;// 主键id
    private String username;// 登录名称
    private String password;// 登录密码
    private Date createDate;// 创建日期
    private Date updateDate;// 创建日期
    private String delFlag; // 删除标记（0：正常；1：删除
    private String email;// 邮箱
    private String isCompany;// 默认为普通用户,No表示公司用户
    private String avatar;// 头像
    private String description;// 心情说明
    private Integer reading;// 在读书籍//1-n
    private Integer liking;// 喜欢读书籍//1-n
    private Integer wantRead;// 在读书籍//1-n
    private Integer hasRead;// 已读书籍//1-n
    private Integer flowings;// 关注数
    private Integer floweds;// 粉丝数
    private Integer messages;// 粉丝数
    private String location;// 位置
    private String address;// 地址
    private String weibo;// 微博
    private String blogs;// 博客
    private String introduction;// 自述
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Date getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    public String getDelFlag() {
        return delFlag;
    }
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getIsCompany() {
        return isCompany;
    }
    public void setIsCompany(String isCompany) {
        this.isCompany = isCompany;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getReading() {
        return reading;
    }
    public void setReading(Integer reading) {
        this.reading = reading;
    }
    public Integer getLiking() {
        return liking;
    }
    public void setLiking(Integer liking) {
        this.liking = liking;
    }
    public Integer getWantRead() {
        return wantRead;
    }
    public void setWantRead(Integer wantRead) {
        this.wantRead = wantRead;
    }
    public Integer getHasRead() {
        return hasRead;
    }
    public void setHasRead(Integer hasRead) {
        this.hasRead = hasRead;
    }
    public Integer getFlowings() {
        return flowings;
    }
    public void setFlowings(Integer flowings) {
        this.flowings = flowings;
    }
    public Integer getFloweds() {
        return floweds;
    }
    public void setFloweds(Integer floweds) {
        this.floweds = floweds;
    }
    public Integer getMessages() {
        return messages;
    }
    public void setMessages(Integer messages) {
        this.messages = messages;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getWeibo() {
        return weibo;
    }
    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }
    public String getBlogs() {
        return blogs;
    }
    public void setBlogs(String blogs) {
        this.blogs = blogs;
    }
    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

}
