package com.roderick.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roderick.pojo.Article;
import com.roderick.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article")
public class ArticleApi {

    ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 通过用户uid分页获取用户发布的文章
     *
     * @param uid  用户uid
     * @param page 页面
     * @param size 每页数据的条数 default 20
     * @return 页面对象序列化为json
     */
    @GetMapping("/{uid}/{page}/{size}")
    public ResponseEntity<Page<Article>> getArticleListByUid(@PathVariable String uid,
                                                             @PathVariable(required = false) Integer page,
                                                             @PathVariable(required = false) Integer size) {
        Page<Article> articleList = articleService.getArticleListByPageAndUidOrderByTime(uid, page, size);
        return articleList != null ? ResponseEntity.ok(articleList) : ResponseEntity.notFound().build();
    }
}
