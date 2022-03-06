package com.roderick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roderick.mapper.QuestionCategoryMapper;
import com.roderick.mapper.QuestionMapper;
import com.roderick.mapper.UserMapper;
import com.roderick.pojo.Question;
import com.roderick.pojo.QuestionCategory;
import com.roderick.pojo.User;
import com.roderick.service.QuestionService;
import com.roderick.util.ThreadPoolUtil;
import com.roderick.vo.QuestionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;

@Service
public class QuestionServiceImpl implements QuestionService {

    QuestionMapper questionMapper;
    QuestionCategoryMapper questionCategoryMapper;
    UserMapper userMapper;
    ThreadPoolUtil threadPoolUtil;

    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Autowired
    public void setQuestionCategoryMapper(QuestionCategoryMapper questionCategoryMapper) {
        this.questionCategoryMapper = questionCategoryMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setThreadPoolUtil(ThreadPoolUtil threadPoolUtil) {
        this.threadPoolUtil = threadPoolUtil;
    }

    @Override
    public void saveQuestion(QuestionForm questionForm) {
        Question question = new Question();
        question.setTitle(questionForm.getTitle());
        question.setContent(questionForm.getContent());

        // 设置提问人
        if (questionForm.getUid() != null) {
            question.setAuthorUid(questionForm.getUid());
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("uid", questionForm.getUid()));
            question.setAuthorName(user.getUsername());
        }

        String json = null;
        QuestionCategory category = questionCategoryMapper.selectById(questionForm.getCategoryId());
        if (category != null) {
            HashMap<Integer, String> map = new HashMap<>();
            map.put(category.getId(), category.getCategoryName());
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                json = objectMapper.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        question.setCategory(json);

        questionMapper.insert(question);
    }

    @Override
    public Question getQuestionById(Long id) {
        return questionMapper.selectById(id);
    }

    @Override
    public Page<Question> getQuestionListByPageOrderByTime(Integer page, Integer size) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = 20;
        }
        return questionMapper.selectPage(new Page<>(page, size), new QueryWrapper<Question>()
                .orderByDesc(true, "create_time"));
    }

    @Override
    public void asyncIncreaseViews(Long id) {
        ExecutorService threadPool = threadPoolUtil.getThreadPool();
        threadPool.execute(() -> questionMapper.increaseViewsById(id));
    }
}
