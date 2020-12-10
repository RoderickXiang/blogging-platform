package com.roderick.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@TableName("blog_user_info")
public class UserInfo implements Serializable {

    @TableId
    private String uid;
    private Integer gender; //0-保密 1-男 2-女
    private Date birthday;
    private String email;
    private String mobile;
    private String signature;
    private String github;
    private String website;

    @TableField(exist = false)
    private String formattedBirthday;   //前端格式化的时间 yyyy-MM-dd

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFormattedBirthday() {
        if (this.birthday != null) {
            //时间格式转换
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(this.birthday);
        } else {
            return null;
        }
    }

    public void setFormattedBirthday(String formattedBirthday) {
        this.formattedBirthday = formattedBirthday;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "uid='" + uid + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", signature='" + signature + '\'' +
                ", github='" + github + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
