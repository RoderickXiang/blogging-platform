package com.roderick.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roderick.pojo.Article;
import com.roderick.pojo.User;
import com.roderick.pojo.UserInfo;
import com.roderick.service.ArticleService;
import com.roderick.service.UserInfoService;
import com.roderick.service.UserService;
import com.roderick.util.PageUtil;
import com.roderick.vo.UserInfoFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    UserService userService;
    UserInfoService userInfoService;
    ArticleService articleService;
    PageUtil pageUtil;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Autowired
    public void setPageUtil(PageUtil pageUtil) {
        this.pageUtil = pageUtil;
    }

    /**
     * 用户信息页面，包含文章
     *
     * @param uid  用户uid
     * @param page 页码
     * @param size 每页信息的条数 default 20
     * @return 用户信息页面
     */
    @GetMapping({"/{uid}/article/{page}/{size}", "/{uid}/article"})
    public String userInfoPage(@PathVariable String uid,
                               @PathVariable(required = false) Integer page,
                               @PathVariable(required = false) Integer size,
                               Model model) {
        User user = userService.getUserByUid(uid);
        user.setPassword("");   //清除密码
        Page<Article> pageArticle = articleService.getArticleListByPageAndUidOrderByTime(uid, page, size);
        Long totalPage = pageUtil.getTotalPage(pageArticle.getTotal(), pageArticle.getSize());

        model.addAttribute("user", user);   //用户对象
        model.addAttribute("pageArticle", pageArticle); //页面对象
        model.addAttribute("totalPage", totalPage);  //页面总数用于做分页
        return "user/info-article";
    }

    /**
     * 更新用户信息页面，包含头像，简介之类的信息
     *
     * @param uid 用户uid
     * @return 更新用户信息页面
     */
    @GetMapping("/profile/{uid}")
    public String userUpdatePage(@PathVariable String uid, Model model) {
        User user = userService.getUserByUid(uid);
        user.setPassword("");   //清除密码
        UserInfo userInfo = userInfoService.getUserInfoByUid(uid);

        model.addAttribute("user", user);
        model.addAttribute("userInfo", userInfo);
        return "user/profile";
    }

    @PostMapping("/profile")
    public String updateUserInfo(UserInfoFrom userInfoFrom) {
        userInfoService.updateUserInfo(userInfoFrom);
        return "redirect:/user/profile/" + userInfoFrom.getUid();
    }
}
