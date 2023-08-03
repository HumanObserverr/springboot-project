package com.tjetc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//页面跳转
public class PageController {
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/ai")
    public String aichat(){
        return "chat";
    }

    @RequestMapping("/loginerror")
    public String longinError(){
        return "loginerror";
    }
}
