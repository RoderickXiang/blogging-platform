package com.roderick.vo;

import java.io.Serializable;

/**
 * 更新用户信息前端表单
 */
public class UserInfoFrom implements Serializable {

    private String uid;
    private String username;
    private Integer gender;
    private String birthday;
    private String password;
    private String signature;
    private String website;

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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "UserInfoFrom{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", gender=" + gender +
                ", birthday='" + birthday + '\'' +
                ", password='" + password + '\'' +
                ", signature='" + signature + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
