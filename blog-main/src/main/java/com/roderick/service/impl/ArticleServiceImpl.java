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
import com.roderick.pojo.User;
import com.roderick.service.ArticleService;
import com.roderick.util.ThreadPoolUtil;
import com.roderick.vo.ArticleFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Service
public class ArticleServiceImpl implements ArticleService {

    ArticleMapper articleMapper;
    ArticleCategoryMapper articleCategoryMapper;
    UserMapper userMapper;
    ThreadPoolUtil poolUtil;

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

    @Autowired
    public void setPoolUtil(ThreadPoolUtil poolUtil) {
        this.poolUtil = poolUtil;
    }

    @Override
    public void saveArticle(ArticleFrom articleFrom) {
        Article article = new Article();
        article.setTitle(articleFrom.getTitle());
        article.setContent(articleFrom.getContent());
        /*浏览量默认为0*/
        //设置作者
        if (articleFrom.getUid() != null) {
            article.setAuthorUid(articleFrom.getUid());
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("uid", articleFrom.getUid()));
            article.setAuthorName(user.getUsername());
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
    public Article getArticleById(Long id) {
        return articleMapper.selectById(id);
    }

    @Override
    public void asyncIncreaseViews(Long id) {
        ExecutorService threadPool = poolUtil.getThreadPool();
        threadPool.execute(() -> {
            //使用重写的方法
            articleMapper.increaseViewsById(id);
        });
    }

    @Override
    public boolean deleteArticleById(String id) {
        return articleMapper.deleteById(id) >= 1;
    }

    @Override
    public void updateArticle(Long id, ArticleFrom articleFrom) {
        Article article = new Article();
        article.setId(id);
        article.setTitle(articleFrom.getTitle());
        // TODO 设置文章分类
        article.setContent(articleFrom.getContent());
        articleMapper.updateById(article);
    }

    @Override
    public Page<Article> getArticleListByPageOrderByTime(Integer page, Integer size) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = 20;
        }
        return articleMapper.selectPage(new Page<Article>(page, size), new QueryWrapper<Article>()
                .orderByDesc(true, "create_time"));
    }

    @Override
    public Page<Article> getArticleListByPageAndUidOrderByTime(String uid, Integer page, Integer size) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = 20;
        }
        return articleMapper.selectPage(new Page<>(page, size), new QueryWrapper<Article>()
                .eq("author_uid", uid)
                .orderByDesc(true, "create_time"));
    }

    @Override
    public Page<Article> getArticleListByPageAndSearch(Integer page, Integer size, String condition) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = 20;
        }
        return articleMapper.selectPage(new Page<>(page, size), new QueryWrapper<Article>()
                .like("title", condition));
    }
}
