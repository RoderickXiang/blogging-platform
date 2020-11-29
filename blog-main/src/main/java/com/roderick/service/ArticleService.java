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
     * @param size 每页数据的条数 default 20
     */
    Page<Article> getArticleListByPageOrderByTime(Integer page, Integer size);

    /**
     * 通过id获取文章
     */
    Article getArticleById(Long id);
}