package com.miao.springdemo.blog.controller;

import com.miao.springdemo.blog.enums.notificationEnum;
import com.miao.springdemo.blog.mapper.CommentMapper;
import com.miao.springdemo.blog.mapper.NotificationMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 三方代码
 */
//将通知设置为已读，并且跳转到问题页面
@Controller
public class NotificationController {

    @Resource
    private NotificationMapper notificationMapper;
    @Resource
    private CommentMapper commentMapper;

    @GetMapping("/notification/{action}")
    public String notification(@PathVariable("action")int id,
                               HttpServletRequest request){
        //将通知设置为已读
        notificationMapper.updatestatus(id);
        //获取type，检验是回复评论还是回复问题
        int type=notificationMapper.gettypebyid(id);
        int outerid=notificationMapper.getouteridbyid(id);
        int questionid;
        if(type== notificationEnum.NOTIFICATION_QUESTION.getType()){
            questionid=outerid;
        }else {
            questionid=commentMapper.getparentidbyid(id);
        }
        return "redirect:/question/"+questionid;
    }
}
