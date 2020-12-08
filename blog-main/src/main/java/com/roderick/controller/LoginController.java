package com.roderick.controller;

import com.roderick.service.UserService;
import com.roderick.vo.RegisterFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 返回登入页面，登入请求由SpringSecurity接收POST请求
     */
    @GetMapping("/login")
    public String longin() {
        return "user/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "user/register";
    }

    @PostMapping("/register")
    public String register(RegisterFrom registerFrom, Model model) {
        userService.createUser(registerFrom);
        //返回登入页面
        model.addAttribute("username", registerFrom.getUsername());
        return "user/login";
    }
}
