package com.roderick.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@TableName("blog_question")
public class Question {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private Integer views;
    private String authorUid;
    private String authorName;
    private String category;
    private Date createTime;
    private Date modifiedTime;
    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private String formattedCreateTime;    //前端输出已经格式化的时间，免得显示太草包
    @TableField(exist = false)
    private String formattedModifiedTime;    //前端输出已经格式化的修改时间，免得显示太草包

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getAuthorUid() {
        return authorUid;
    }

    public void setAuthorUid(String authorUid) {
        this.authorUid = authorUid;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getFormattedCreateTime() {
        if (this.createTime != null) {
            //时间格式转换
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(this.createTime);
        } else {
            return null;
        }
    }

    public void setFormattedCreateTime(String formattedCreateTime) {
        this.formattedCreateTime = formattedCreateTime;
    }

    public String getFormattedModifiedTime() {
        if (this.modifiedTime != null) {
            //时间格式转换
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(this.modifiedTime);
        } else {
            return null;
        }
    }

    public void setFormattedModifiedTime(String formattedModifiedTime) {
        this.formattedModifiedTime = formattedModifiedTime;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", views=" + views +
                ", authorUid='" + authorUid + '\'' +
                ", authorName='" + authorName + '\'' +
                ", category='" + category + '\'' +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", isDeleted=" + isDeleted +
                ", formattedCreateTime='" + formattedCreateTime + '\'' +
                ", formattedModifiedTime='" + formattedModifiedTime + '\'' +
                '}';
    }
}
