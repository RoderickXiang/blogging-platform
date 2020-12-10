package com.roderick.service;

import com.roderick.pojo.UserInfo;
import com.roderick.vo.UserInfoFrom;

public interface UserInfoService {

    UserInfo getUserInfoByUid(String uid);

    void updateUserInfo(UserInfoFrom userInfoFrom);

    /**
     * 通过UserInfo对象创建用户详情
     */
    void createUserInfo(UserInfo userInfo);
}
