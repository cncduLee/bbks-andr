package com.funger.bbks.bean;

import java.util.Date;

public class Book extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private Long id;// 主键
    private String bookName;// 图书名
    private String author;// 作者
    private String isbn;// 图书编号
    private String translator;// 译者
    private String press;// 出版社
    private String version;// 版本
    private String directory;// 目录
    private String outline;// 图书概述
    private String delFlag; // 删除标记（0：正常；1：删除）
    private String coverPic;// 封面图片

    private boolean isFree;// 免费阅读：true,收费false
    private Double price;// 价格
    private String allPrice;// 竞价
    private Double pubPrice;// 竞价
    private Integer wantRead;// 想读人数
    private Integer likeCount;// 喜欢人数
    private Integer isReading;// 在读人数
    private Integer hasRead;// 读过人数
    private Integer commentCount;// 读过人数

    private String bookSrc;// 电子书源文件
    private String eFlag;// 电子书
    private String txtSrc;//txt文件路径
	
    private String authorintro;// 作者简介<215
    private String relationship;// 关系列，存放：{'dd':'20','amazon':'231',}<50
    private Integer islock;// 如果该数据被豆瓣网操作过，则被标记成枷锁状态，值为1，其他数据则不能操作
    private Date createdAt;
    private Date updateAt;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getTranslator() {
        return translator;
    }
    public void setTranslator(String translator) {
        this.translator = translator;
    }
    public String getPress() {
        return press;
    }
    public void setPress(String press) {
        this.press = press;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getDirectory() {
        return directory;
    }
    public void setDirectory(String directory) {
        this.directory = directory;
    }
    public String getOutline() {
        return outline;
    }
    public void setOutline(String outline) {
        this.outline = outline;
    }
    public String getDelFlag() {
        return delFlag;
    }
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
    public String getCoverPic() {
        return coverPic;
    }
    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }
    public boolean isFree() {
        return isFree;
    }
    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getAllPrice() {
        return allPrice;
    }
    public void setAllPrice(String allPrice) {
        this.allPrice = allPrice;
    }
    public Double getPubPrice() {
        return pubPrice;
    }
    public void setPubPrice(Double pubPrice) {
        this.pubPrice = pubPrice;
    }
    public Integer getWantRead() {
        return wantRead;
    }
    public void setWantRead(Integer wantRead) {
        this.wantRead = wantRead;
    }
    public Integer getLikeCount() {
        return likeCount;
    }
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
    public Integer getIsReading() {
        return isReading;
    }
    public void setIsReading(Integer isReading) {
        this.isReading = isReading;
    }
    public Integer getHasRead() {
        return hasRead;
    }
    public void setHasRead(Integer hasRead) {
        this.hasRead = hasRead;
    }
    public Integer getCommentCount() {
        return commentCount;
    }
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
    public String getBookSrc() {
        return bookSrc;
    }
    public void setBookSrc(String bookSrc) {
        this.bookSrc = bookSrc;
    }
    public String geteFlag() {
        return eFlag;
    }
    public void seteFlag(String eFlag) {
        this.eFlag = eFlag;
    }
    public String getTxtSrc() {
        return txtSrc;
    }
    public void setTxtSrc(String txtSrc) {
        this.txtSrc = txtSrc;
    }
    public String getAuthorintro() {
        return authorintro;
    }
    public void setAuthorintro(String authorintro) {
        this.authorintro = authorintro;
    }
    public String getRelationship() {
        return relationship;
    }
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
    public Integer getIslock() {
        return islock;
    }
    public void setIslock(Integer islock) {
        this.islock = islock;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Date getUpdateAt() {
        return updateAt;
    }
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
    
    
    
}
