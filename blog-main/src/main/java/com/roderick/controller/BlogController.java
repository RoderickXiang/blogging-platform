package com.roderick.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roderick.pojo.Article;
import com.roderick.pojo.ArticleCategory;
import com.roderick.pojo.User;
import com.roderick.service.ArticleCategoryService;
import com.roderick.service.ArticleService;
import com.roderick.service.UserService;
import com.roderick.util.PageUtil;
import com.roderick.vo.ArticleFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    ArticleService articleService;
    ArticleCategoryService articleCategoryService;
    UserService userService;
    PageUtil pageUtil;
    HttpSession httpSession;

    @Autowired
    public void setArticleCategoryService(ArticleCategoryService articleCategoryService) {
        this.articleCategoryService = articleCategoryService;
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPageUtil(PageUtil pageUtil) {
        this.pageUtil = pageUtil;
    }

    @Autowired
    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    /**
     * 写文章的页面
     */
    @GetMapping("/write")
    public String writeArticlePage(Model model) {
        //存放类别
        List<ArticleCategory> categoryList = articleCategoryService.getArticleCategoryList();
        model.addAttribute("categoryList", categoryList);
        return "blog/write";
    }

    /**
     * 文章内容页面
     *
     * @param id 文章id
     */
    @GetMapping("/article/{id}")
    public String articlePage(Model model, @PathVariable Long id) {
        //异步增加浏览量
        articleService.asyncIncreaseViews(id);
        Article article = articleService.getArticleById(id);
        //文章信息
        model.addAttribute("article", article);
        User author = userService.getUserByUid(article.getAuthorUid());

        if (author != null) {
            author.setPassword(""); //清除敏感信息
            author.setPosts(userService.getPostsByUid(article.getAuthorUid())); //发布的文章数
        }
        //作者信息
        model.addAttribute("author", author);
        return "blog/article";
    }

    /**
     * 提交新的文章
     *
     * @param articleFrom 前端提交的vo对象
     */
    @PostMapping("/article")
    public String saveArticle(ArticleFrom articleFrom) {
        articleService.saveArticle(articleFrom);
        return "redirect:/index";
    }

    /**
     * 更新文章的页面--写文章页面+回显数据
     *
     * @param id  文章id
     * @param uid 用户uid
     */
    @GetMapping("/article/update/{id}/{uid}")
    public String updateArticlePage(@PathVariable Long id, @PathVariable String uid, Model model) {
        User loginUser = (User) httpSession.getAttribute("loginUser");  //登入的用户
        //校验已登录的用户是否有权限删除文章
        if (loginUser == null || !loginUser.getUid().equals(uid)) {
            return null;
        }
        //获取文章
        Article article = articleService.getArticleById(id);
        if (article == null) {
            return null;
        }
        model.addAttribute("article", article);
        return "blog/write";
    }

    /**
     * 更新文章
     *
     * @param id 文章id
     */
    @PostMapping("/article/update/{id}")
    public String updateArticle(ArticleFrom articleFrom, @PathVariable Long id) {
        articleService.updateArticle(id, articleFrom);
        return "redirect:/blog/article" + '/' + id;
    }

    /**
     * 按时间顺序获取文章列表页面
     *
     * @param page 页面
     * @param size 每页数据的条数 default 20
     */
    @GetMapping({"/{page}/{size}", ""})
    public String getArticleListPage(@PathVariable(value = "page", required = false) Integer page,
                                     @PathVariable(value = "size", required = false) Integer size,
                                     Model model) {
        Page<Article> articlePage = articleService.getArticleListByPageOrderByTime(page, size);

        model.addAttribute("articlePage", articlePage);
        model.addAttribute("totalPage", pageUtil.getTotalPage(articlePage.getTotal(), articlePage.getSize()));
        return "blog/list";
    }

    /**
     * 搜通过文章名获取文章列表
     *
     * @param page      页面
     * @param size      每页数据的条数 default 20
     * @param condition 搜索文章条件--目前只能使用文章名进行搜索
     */
    @GetMapping({"/search/{page}/{size}", "/search"})
    public String getArticleListPageBySearch(@PathVariable(value = "page", required = false) Integer page,
                                             @PathVariable(value = "size", required = false) Integer size,
                                             @RequestParam(value = "condition") String condition,
                                             Model model) {
        Page<Article> articlePage = articleService.getArticleListByPageAndSearch(page, size, condition);

        model.addAttribute("articlePage", articlePage);
        model.addAttribute("totalPage", pageUtil.getTotalPage(articlePage.getTotal(), articlePage.getSize()));
        return "blog/list";
    }
}
