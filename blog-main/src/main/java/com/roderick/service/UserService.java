package com.roderick.service;

import com.roderick.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User getUserByUid(String uid);

    /**
     * 获取用户发布的文章数
     */
    Integer getPostsByUid(String uid);

    /**
     * 查询用户名是否可用
     *
     * @param username 被验证的用户名
     */
    Boolean checkUsername(String username);
}
