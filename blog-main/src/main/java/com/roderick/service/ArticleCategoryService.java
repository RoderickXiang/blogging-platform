package com.roderick.service;

import com.roderick.pojo.ArticleCategory;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ArticleCategoryService {

    List<ArticleCategory> getArticleCategoryList();
}
