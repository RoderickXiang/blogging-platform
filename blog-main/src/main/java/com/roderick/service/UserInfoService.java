package com.roderick.service;

import com.roderick.pojo.UserInfo;
import com.roderick.vo.UserInfoFrom;

public interface UserInfoService {

    UserInfo getUserInfoByUid(String uid);

    void updateUserInfo(UserInfoFrom userInfoFrom);
}
