package com.roderick.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roderick.pojo.Article;
import com.roderick.pojo.ArticleCategory;
import com.roderick.service.ArticleCategoryService;
import com.roderick.service.ArticleService;
import com.roderick.utils.PageUtil;
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
    PageUtil pageUtil;

    @Autowired
    public void setArticleCategoryService(ArticleCategoryService articleCategoryService) {
        this.articleCategoryService = articleCategoryService;
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Autowired
    public void setTotalPage(PageUtil pageUtil) {
        this.pageUtil = pageUtil;
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
     * @param page 页面
     * @param size 每页数据的条数 default 5
     */
    @GetMapping({"/{page}/{size}", ""})
    public String getArticleList(@PathVariable(value = "page", required = false) Integer page,
                                 @PathVariable(value = "size", required = false) Integer size,
                                 Model model) {
        Page<Article> articlePage = articleService.getArticleListByPageOrderByTime(page, size);

        model.addAttribute("articlePage", articlePage);
        model.addAttribute("totalPage", pageUtil.getTotalPage(articlePage.getTotal(), articlePage.getSize()));
        return "blog/list";
    }
}
