package com.roderick.service;

import com.roderick.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User getUserByUid(String uid);

    /**
     * 获取用户发布的文章数
     */
    Integer getPostsByUid(String uid);
}
