package com.roderick.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

@TableName("blog_user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String uid;
    private String username;
    private String password;
    private String avatar;
    private Integer roleId;
    private Date createTime;
    private Date modifiedTime;
    @TableLogic
    private Integer isDeleted;  //逻辑删除

    @TableField(exist = false)
    private Integer posts;   //发布的文章数

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getPosts() {
        if (this.posts == null){
            return 0;
        }
        return posts;
    }

    public void setPosts(Integer posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", roleId=" + roleId +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", isDeleted=" + isDeleted +
                ", posts=" + posts +
                '}';
    }
}
