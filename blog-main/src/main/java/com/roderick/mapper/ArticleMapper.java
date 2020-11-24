package com.roderick.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roderick.pojo.Article;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleMapper extends BaseMapper<Article> {
}
