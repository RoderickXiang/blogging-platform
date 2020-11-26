package com.roderick.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roderick.pojo.Article;
import com.roderick.pojo.ArticleCategory;
import com.roderick.service.ArticleCategoryService;
import com.roderick.service.ArticleService;
import com.roderick.vo.ArticleFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    ArticleService articleService;
    ArticleCategoryService articleCategoryService;

    @Autowired
    public void setArticleCategoryService(ArticleCategoryService articleCategoryService) {
        this.articleCategoryService = articleCategoryService;
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
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
     * 按时间顺序获取文章列表页面
     *
     * @param page  页面
     * @param count 每页的条数
     */
    @GetMapping({"/{page}/{count}", ""})
    public String getArticleList(@PathVariable(value = "page", required = false) Integer page,
                                 @PathVariable(value = "count", required = false) Integer count,
                                 Model model) {
        Page<Article> articlePage = null;
        if (page == null || count == null) {
            articlePage = articleService.getArticleListByPageOrderByTime();
        } else {
            articlePage = articleService.getArticleListByPageOrderByTime(page, count);
        }

        model.addAttribute("articlePage", articlePage);
        return "blog/list";
    }
}
