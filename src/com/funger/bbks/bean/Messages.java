package com.funger.bbks.bean;

import java.util.Date;

public class Messages {
    private Long id;
    private Integer isRead;// 阅读的标记
    private Integer isReply;// 是否是消息的回复
    private Long fromu;// 发件人
    private Long tou;// 收件人
    private String content;
    private Date creatAt;
    private Date updateAt;
    // 冗余信息
    private String fromname;
    private String toname;
    private String fromavatar;
    private String toavatar;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getIsRead() {
        return isRead;
    }
    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
    public Integer getIsReply() {
        return isReply;
    }
    public void setIsReply(Integer isReply) {
        this.isReply = isReply;
    }
    public Long getFromu() {
        return fromu;
    }
    public void setFromu(Long fromu) {
        this.fromu = fromu;
    }
    public Long getTou() {
        return tou;
    }
    public void setTou(Long tou) {
        this.tou = tou;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Date getCreatAt() {
        return creatAt;
    }
    public void setCreatAt(Date creatAt) {
        this.creatAt = creatAt;
    }
    public Date getUpdateAt() {
        return updateAt;
    }
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
    public String getFromname() {
        return fromname;
    }
    public void setFromname(String fromname) {
        this.fromname = fromname;
    }
    public String getToname() {
        return toname;
    }
    public void setToname(String toname) {
        this.toname = toname;
    }
    public String getFromavatar() {
        return fromavatar;
    }
    public void setFromavatar(String fromavatar) {
        this.fromavatar = fromavatar;
    }
    public String getToavatar() {
        return toavatar;
    }
    public void setToavatar(String toavatar) {
        this.toavatar = toavatar;
    }
}
