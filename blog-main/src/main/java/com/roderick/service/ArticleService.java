package com.roderick.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roderick.pojo.Article;
import com.roderick.vo.ArticleFrom;

public interface ArticleService {

    /**
     * 保存文章
     * @param articleFrom 前端传来的表单对象
     */
    void saveArticle(ArticleFrom articleFrom);

    /**
     * 按时间顺序分页获取文章
     * @param page 页面
     * @param size 每页的条数
     */
    Page<Article> getArticleListByPageOrderByTime(Integer page, Integer size);

    /**
     * 默认获取第一页
     */
    Page<Article> getArticleListByPageOrderByTime();
}
