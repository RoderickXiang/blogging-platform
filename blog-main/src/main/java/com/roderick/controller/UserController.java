package com.roderick.controller;

import com.roderick.pojo.User;
import com.roderick.service.ArticleService;
import com.roderick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    UserService userService;
    ArticleService articleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 用户信息页面，包含文章、问答之类的模块
     *
     * @param uid 用户uid
     * @return 用户信息页面
     */
    @GetMapping("/{uid}")
    public String userInfoPage(@PathVariable String uid, Model model) {
        User user = userService.getUserByUid(uid);
        user.setPosts(userService.getPostsByUid(uid));
        user.setPassword("");   //清除密码
        model.addAttribute("user", user);
        return "user/info";
    }
}
