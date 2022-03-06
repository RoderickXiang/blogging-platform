package com.roderick.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roderick.pojo.*;
import com.roderick.service.QuestionCategoryService;
import com.roderick.service.QuestionService;
import com.roderick.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ask")
public class QuestionController {

    QuestionService questionService;
    QuestionCategoryService questionCategoryService;
    PageUtil pageUtil;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setQuestionCategoryService(QuestionCategoryService questionCategoryService) {
        this.questionCategoryService = questionCategoryService;
    }

    @Autowired
    public void setPageUtil(PageUtil pageUtil) {
        this.pageUtil = pageUtil;
    }

    /**
     * 写提问的页面
     */
    @GetMapping("/write")
    public String writeQuestionPage(Model model) {
        // 问答类别
        List<QuestionCategory> categoryList = questionCategoryService.getQuestionCategoryList();
        model.addAttribute("categoryList", categoryList);
        return "ask/write";
    }

    /**
     * 文章内容页面
     *
     * @param id 文章id
     */
    @GetMapping("/question/{id}")
    public String questionPage(Model model, @PathVariable Long id) {
        //异步增加浏览量
        questionService.asyncIncreaseViews(id);
        Question question = questionService.getQuestionById(id);
        //文章信息
        model.addAttribute("question", question);
        User questioner = questionService.getUserByUid(question.getAuthorUid());

        if (questioner != null) {
            questioner.setPassword(""); //清除敏感信息
            questioner.setPosts(userService.getPostsByUid(article.getAuthorUid())); //发布的文章数
        }
        //作者信息
        model.addAttribute("author", questioner);
        return "blog/article";
    }

    @GetMapping({"/{page}/{size}", ""})
    public String getQuestionListPage(@PathVariable(value = "page", required = false) Integer page,
                                      @PathVariable(value = "size", required = false) Integer size,
                                      Model model) {
        Page<Question> questionPage = questionService.getQuestionListByPageOrderByTime(page, size);

        model.addAttribute("questionPage", questionPage);
        model.addAttribute("totalPage", pageUtil.getTotalPage(questionPage.getTotal(), questionPage.getSize()));
        return "ask/list";
    }
}
