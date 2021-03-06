package com.roderick.service;

import com.roderick.pojo.User;
import com.roderick.vo.RegisterFrom;
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

    /**
     * 创建用户
     *
     * @param registerFrom 前端传来的新用户表单
     */
    void createUser(RegisterFrom registerFrom);

    /**
     * 更新用户
     *
     * @param user 用户对象
     */
    void updateUser(User user);

    /**
     * 更新用户头像
     */
    void updateUserAvatar(String uid, String avatar);
}
