package com.roderick.service;

import com.roderick.vo.ArticleFrom;

public interface ArticleService {

    /**
     * 保存文章
     * @param articleFrom 前端传来的表单对象
     */
    void saveArticle(ArticleFrom articleFrom);
}
