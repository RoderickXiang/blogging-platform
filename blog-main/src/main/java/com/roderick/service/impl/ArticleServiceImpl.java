package com.roderick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roderick.mapper.ArticleCategoryMapper;
import com.roderick.mapper.ArticleMapper;
import com.roderick.pojo.Article;
import com.roderick.pojo.ArticleCategory;
import com.roderick.service.ArticleService;
import com.roderick.vo.ArticleFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    ArticleMapper articleMapper;
    ArticleCategoryMapper articleCategoryMapper;

    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Autowired
    public void setArticleCategoryMapper(ArticleCategoryMapper articleCategoryMapper) {
        this.articleCategoryMapper = articleCategoryMapper;
    }

    @Override
    public void saveArticle(ArticleFrom articleFrom) {
        Article article = new Article();
        article.setTitle(articleFrom.getTitle());
        article.setContent(articleFrom.getContent());
        /*浏览量默认为0*/
        article.setAuthorUid(articleFrom.getUid());
        //json设置文章类别
        String json = null;
        ArticleCategory category = articleCategoryMapper.selectById(articleFrom.getCategoryId());
        if (category != null) {
            Map<Integer, String> map = new HashMap<>();
            map.put(category.getId(), category.getCategoryName());
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                json = objectMapper.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        article.setCategory(json);
        //存入数据库
        articleMapper.insert(article);
    }

    @Override
    public Page<Article> getArticleListByPageOrderByTime(Integer page, Integer count) {
        return articleMapper.selectPage(new Page<Article>(page, count), new QueryWrapper<Article>().orderByDesc(true, "create_time"));
    }

    @Override
    public Page<Article> getArticleListByPageOrderByTime() {
        return this.getArticleListByPageOrderByTime(1, 5);
    }
}
