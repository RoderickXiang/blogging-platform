package com.roderick.service.impl;

import com.roderick.mapper.QuestionCategoryMapper;
import com.roderick.pojo.QuestionCategory;
import com.roderick.service.QuestionCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionCategoryServiceImpl implements QuestionCategoryService {

    QuestionCategoryMapper questionCategoryMapper;

    @Autowired
    public void setQuestionCategoryMapper(QuestionCategoryMapper questionCategoryMapper) {
        this.questionCategoryMapper = questionCategoryMapper;
    }

    @Override
    public List<QuestionCategory> getQuestionCategoryList() {
        return questionCategoryMapper.selectList(null);
    }
}
