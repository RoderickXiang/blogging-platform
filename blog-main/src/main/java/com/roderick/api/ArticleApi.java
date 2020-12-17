package com.roderick.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roderick.pojo.Article;
import com.roderick.pojo.User;
import com.roderick.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/article")
public class ArticleApi {

    ArticleService articleService;
    HttpSession httpSession;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Autowired
    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    /**
     * 通过用户uid分页获取用户发布的文章
     *
     * @param uid  用户uid
     * @param page 页面
     * @param size 每页数据的条数 default 20
     * @return 页面对象序列化为json
     */
    @GetMapping({"/{uid}/{page}/{size}", "/{uid}"})
    public ResponseEntity<Page<Article>> getArticleListByUid(@PathVariable String uid,
                                                             @PathVariable(required = false) Integer page,
                                                             @PathVariable(required = false) Integer size) {
        Page<Article> articleList = articleService.getArticleListByPageAndUidOrderByTime(uid, page, size);
        return articleList != null ? ResponseEntity.ok(articleList) : ResponseEntity.notFound().build();
    }

    /**
     * 删除文章--逻辑删除
     *
     * @param id  文章id
     * @param uid 文章作者uid
     */
    @DeleteMapping("/{id}/{uid}")
    public ResponseEntity<Map<String, Object>> deleteArticleById(@PathVariable String id, @PathVariable String uid) {
        User loginUser = (User) httpSession.getAttribute("loginUser");  //登入的用户
        //校验已登录的用户是否有权限删除文章
        if (loginUser == null || !loginUser.getUid().equals(uid)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        //删除操作
        boolean flag = articleService.deleteArticleById(id);
        if (flag) {
            Map<String, Object> map = new HashMap<>();
            map.put("msg", "文章删除成功");
            return ResponseEntity.ok(map);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
