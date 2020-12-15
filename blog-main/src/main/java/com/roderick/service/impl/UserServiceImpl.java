package com.roderick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.roderick.mapper.ArticleMapper;
import com.roderick.mapper.UserMapper;
import com.roderick.mapper.UserRoleMapper;
import com.roderick.pojo.Article;
import com.roderick.pojo.User;
import com.roderick.pojo.UserInfo;
import com.roderick.pojo.UserRole;
import com.roderick.service.UserInfoService;
import com.roderick.service.UserService;
import com.roderick.util.UUIDUtil;
import com.roderick.vo.RegisterFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

    ArticleMapper articleMapper;
    UserMapper userMapper;
    UserRoleMapper userRoleMapper;
    PasswordEncoder passwordEncoder;
    HttpSession session;
    UserInfoService userInfoService;

    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setSession(HttpSession session) {
        this.session = session;
    }

    @Autowired
    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    //自定义登入逻辑
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //数据库查询
        User realUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (realUser == null) {
            throw new UsernameNotFoundException("用户名未找到");
        }
        //获取用户角色
        UserRole userRole = userRoleMapper.selectById(realUser.getRoleId());

        //设置session --其实应该放在登入验证之后
        session.setAttribute("loginUser", realUser);
        //username 来自于前端
        //password 来自于数据库
        return new org.springframework.security.core.userdetails.
                User(username, realUser.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + userRole.getUserRole()));
    }

    @Override
    public User getUserByUid(String uid) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("uid", uid));
    }

    @Override
    public Integer getPostsByUid(String uid) {
        return articleMapper.selectCount(new QueryWrapper<Article>().eq("author_uid", uid));
    }

    @Override
    public Boolean checkUsername(String username) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        return user == null;
    }

    @Override
    public void createUser(RegisterFrom registerFrom) {
        //创建用户
        User user = new User();
        user.setUid(UUIDUtil.getUUID());
        if (registerFrom.getUsername() != null) {
            user.setUsername(registerFrom.getUsername());
        }
        String encode = passwordEncoder.encode(registerFrom.getPassword());
        user.setPassword(encode);
        user.setRoleId(1);
        user.setIsDeleted(0);
        userMapper.insert(user);

        //创建用户详情
        UserInfo userInfo = new UserInfo(user.getUid());
        userInfoService.createUserInfo(userInfo);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    @Override
    public void updateUserAvatar(String uid, String avatar) {
        User user = new User();
        user.setAvatar(avatar);
        userMapper.update(user, new UpdateWrapper<User>().eq("uid", uid));
        //修改session更新头像信息
        session.setAttribute("loginUser",this.getUserByUid(uid));
    }
}
