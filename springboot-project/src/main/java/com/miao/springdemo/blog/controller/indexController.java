package com.miao.springdemo.blog.controller;

import com.miao.springdemo.blog.dto.PageDto;
import com.miao.springdemo.blog.entity.Question;
import com.miao.springdemo.blog.mapper.NotificationMapper;
import com.miao.springdemo.blog.mapper.UserBlogMapper;
import com.miao.springdemo.blog.service.QuestionService;
import com.miao.springdemo.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


//首页
@Controller
public class indexController {

    @Resource
    private UserBlogMapper userBlogMapper;

    @Resource
    private QuestionService questionService;
    @Resource
    private NotificationMapper notificationMapper;

    @GetMapping(value = {"blog.do"})
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name = "size", defaultValue = "10") int size) {
        //查找cookies，观察是否有token存在
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {

            return "/dashboard.html";
        }
        User user = null;
        for (Cookie cookie : cookies) {
            System.out.println("cookie:还在吗" + cookie.getName());
            if (cookie.getName().equals("token")) {

                String token = cookie.getValue();
                user = userBlogMapper.findBytoken(token);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                    //获取未读的消息数量
                    int unreadnum=notificationMapper.getunreadcount(user.getId());
                    request.getSession().setAttribute("unreadnum",unreadnum);
                }
                break;
            }else{
                String username = request.getSession().getAttribute("username").toString();
                System.out.println("request:" + username);
                user = userBlogMapper.findById(Integer.valueOf(username));
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                    //获取未读的消息数量
                    int unreadnum=notificationMapper.getunreadcount(user.getId());
                    request.getSession().setAttribute("unreadnum",unreadnum);
                }
                break;
            }
        }
        PageDto pagination = questionService.list(page, size);
        model.addAttribute("pagination", pagination);

        //获取阅读量最高的十篇问题
        List<Question> questions= questionService.gettopten();
        model.addAttribute("topquestion",questions);
        return "index";
    }

}
