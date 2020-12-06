package com.roderick.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roderick.pojo.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 增加文章阅读量
     * @param id 文章id
     */
    @Update("UPDATE blog_article SET views = views + 1 WHERE id = #{id} AND is_deleted = 0")
    void increaseViewsById(@Param("id") Long id);
}
