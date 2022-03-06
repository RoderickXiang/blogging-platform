package com.roderick.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roderick.pojo.Question;
import com.roderick.vo.QuestionForm;

public interface QuestionService {

    /**
     * 保存提问
     *
     * @param questionForm 前端传来的表单对象
     */
    void saveQuestion(QuestionForm questionForm);

    /**
     * 通过id获取提问
     */
    Question getQuestionById(Long id);

    /**
     * 按时间顺序分页获取问题列表
     *
     * @param page 页面
     * @param size 每页数据的条数 default 20
     */
    Page<Question> getQuestionListByPageOrderByTime(Integer page, Integer size);

    /**
     * 异步增加提问浏览量
     *
     * @param id 提问id
     */
    void asyncIncreaseViews(Long id);
}
