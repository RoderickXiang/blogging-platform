package com.roderick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roderick.mapper.ArticleCategoryMapper;
import com.roderick.mapper.ArticleMapper;
import com.roderick.mapper.UserMapper;
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
    UserMapper userMapper;

    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Autowired
    public void setArticleCategoryMapper(ArticleCategoryMapper articleCategoryMapper) {
        this.articleCategoryMapper = articleCategoryMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void saveArticle(ArticleFrom articleFrom) {
        Article article = new Article();
        article.setTitle(articleFrom.getTitle());
        article.setContent(articleFrom.getContent());
        /*浏览量默认为0*/
        //设置作者
        if (articleFrom.getUid()!=null){
            article.setAuthorUid(articleFrom.getUid());
            article.setAuthorName(userMapper.selectById(articleFrom.getUid()).getUsername());
        }
        //json设置文章类别
        String json = null;
        ArticleCategory category = articleCategoryMapper.selectById(articleFrom.getCategoryId());   //查询数据库获取类别
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
    public Page<Article> getArticleListByPageOrderByTime(Integer page, Integer size) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = 5;
        }
        return articleMapper.selectPage(new Page<Article>(page, size), new QueryWrapper<Article>().orderByDesc(true, "create_time"));
    }
}
