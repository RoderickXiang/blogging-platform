package com.roderick.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roderick.pojo.Question;
import com.roderick.service.QuestionService;
import com.roderick.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ask")
public class QuestionController {

    QuestionService questionService;
    PageUtil pageUtil;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setPageUtil(PageUtil pageUtil) {
        this.pageUtil = pageUtil;
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
