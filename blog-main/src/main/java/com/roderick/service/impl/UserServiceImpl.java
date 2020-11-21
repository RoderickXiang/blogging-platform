package com.roderick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.roderick.mapper.UserMapper;
import com.roderick.pojo.User;
import com.roderick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserServiceImpl implements UserService {

    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //自定义登入逻辑
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //数据库查询
        User realUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (realUser == null) {
            throw new UsernameNotFoundException("用户名未找到");
        }
        //username 来自于前端
        //password 来自于数据库
        return new org.springframework.security.core.userdetails.
                User(username, realUser.getPassword(), Collections.singleton(new SimpleGrantedAuthority("a")));
    }
}
