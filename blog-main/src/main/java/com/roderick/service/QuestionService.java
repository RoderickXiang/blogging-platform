package com.roderick.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roderick.pojo.Question;

public interface QuestionService {

    /**
     * 按时间顺序分页获取问题列表
     *
     * @param page 页面
     * @param size 每页数据的条数 default 20
     */
    Page<Question> getQuestionListByPageOrderByTime(Integer page, Integer size);
}
