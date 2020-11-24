package com.roderick.service.impl;

import com.roderick.mapper.ArticleCategoryMapper;
import com.roderick.pojo.ArticleCategory;
import com.roderick.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    ArticleCategoryMapper articleCategoryMapper;

    @Autowired
    public void setArticleCategoryMapper(ArticleCategoryMapper articleCategoryMapper) {
        this.articleCategoryMapper = articleCategoryMapper;
    }

    @Override
    public List<ArticleCategory> getArticleCategoryList() {
        return articleCategoryMapper.selectList(null);
    }
}
