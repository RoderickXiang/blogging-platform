package com.roderick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roderick.mapper.QuestionMapper;
import com.roderick.pojo.Article;
import com.roderick.pojo.Question;
import com.roderick.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    QuestionMapper questionMapper;

    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Override
    public Page<Question> getQuestionListByPageOrderByTime(Integer page, Integer size) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = 20;
        }
        return questionMapper.selectPage(new Page<Question>(page,size),new QueryWrapper<Question>()
                .orderByDesc(true,"create_time"));
    }
}
