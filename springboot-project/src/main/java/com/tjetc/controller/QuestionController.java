package com.tjetc.controller;

/*
import com.tjetc.dto.CommentDto;
import com.tjetc.dto.Questiondto;
import com.tjetc.entity.Question;
import com.tjetc.entity.User;
import com.tjetc.mapper.NotificationMapper;
import com.tjetc.mapper.UserMapper;
import com.tjetc.service.CommentService;
import com.tjetc.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

//问题详情
@Controller
public class QuestionController {

    @Resource
    private QuestionService questionService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CommentService commentService;
    @Resource
    private NotificationMapper notificationMapper;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")int id,
                           Model model,
                           HttpServletRequest request){
        System.out.println(id);
        //查找cookies，观察是否有token存在
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "login";
        }
        User user = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                user = userMapper.findBytoken(token);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                    //获取未读的消息数量
                    int unreadnum=notificationMapper.getunreadcount(user.getId());
                    request.getSession().setAttribute("unreadnum",unreadnum);
                }
                break;
            }
        }
        Questiondto questiondto=questionService.getbyid(id);
        //增加阅读数
        questionService.increaseview(id);
        model.addAttribute("questionDto",questiondto);
        //展示回复数据
        List<CommentDto> comments=commentService.getByid(id);
        model.addAttribute("comments",comments);
        //相关问题
        String[] tags=questiondto.getTag().split(",");
        StringBuilder msg=new StringBuilder();
        for (String tag:tags){
            msg.append(tag);
            msg.append("|");
        }
        String result=msg.substring(0,msg.length()-1);
        List<Question> relativeQuestion =questionService.getbytag(id,result);
        model.addAttribute("relativeQuestion",relativeQuestion);

        return "question";
    }
}


 */