package com.roderick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlogController {

    @RequestMapping({"/", "/index"})
    public String i() {
        return "blog/index";
    }
}