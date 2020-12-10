package com.roderick.service.impl;

import com.roderick.mapper.UserInfoMapper;
import com.roderick.pojo.User;
import com.roderick.pojo.UserInfo;
import com.roderick.service.UserInfoService;
import com.roderick.service.UserService;
import com.roderick.vo.UserInfoFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    HttpSession httpSession;
    UserInfoMapper userInfoMapper;
    UserService userService;

    @Autowired
    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Autowired
    public void setUserInfoMapper(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserInfo getUserInfoByUid(String uid) {
        return userInfoMapper.selectById(uid);
    }

    @Override
    public void updateUserInfo(UserInfoFrom userInfoFrom) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(userInfoFrom.getUid());

        //修改用户名
        User user = userService.getUserByUid(userInfoFrom.getUid());
        if (!user.getUsername().equals(userInfoFrom.getUsername())) {
            user.setUsername(userInfoFrom.getUsername());
            userService.updateUser(user);
        }
        //todo 修改密码

        userInfo.setGender(userInfoFrom.getGender());
        //修改出生日期
        if (!"".equals(userInfoFrom.getBirthday())) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parse = dateFormat.parse(userInfoFrom.getBirthday());
                userInfo.setBirthday(parse);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //修改签名
        userInfo.setSignature(userInfoFrom.getSignature());
        //修改个人网站
        userInfo.setWebsite(userInfoFrom.getWebsite());
        userInfoMapper.updateById(userInfo);
        //刷新session中登入的用户信息
        httpSession.setAttribute("loginUser", userService.getUserByUid(userInfoFrom.getUid()));
    }
}
