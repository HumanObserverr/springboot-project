package com.miao.springdemo.blog.controller;

import com.miao.springdemo.blog.dto.CommentCreateDto;
import com.miao.springdemo.blog.dto.CommentDto;
import com.miao.springdemo.blog.dto.PageDto;
import com.miao.springdemo.blog.dto.ResultDto;
import com.miao.springdemo.blog.entity.Comment;
import com.miao.springdemo.blog.entity.Notification;
import com.miao.springdemo.blog.entity.Question;
import com.miao.springdemo.blog.enums.NotificationStatusEnum;
import com.miao.springdemo.blog.enums.notificationEnum;
import com.miao.springdemo.blog.mapper.CommentMapper;
import com.miao.springdemo.blog.mapper.NotificationMapper;
import com.miao.springdemo.blog.mapper.QuestionMapper;
import com.miao.springdemo.blog.mapper.UserBlogMapper;
import com.miao.springdemo.blog.service.QuestionService;
import com.miao.springdemo.domain.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 三方代码
 */
//回复功能
@Controller
public class CommentController {
    @Autowired
    private UserBlogMapper userBlogMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private NotificationMapper notificationMapper;
    @Resource
    private QuestionService questionService;


    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDto commentCreateDto,
                       HttpServletRequest request){
        //把User写进session
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "login";
        }
        User user = null;
        for (Cookie cookie : cookies) {
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
            }
        }
        //把评论插入数据库
        Comment comment=new Comment();
        comment.setParent_id(commentCreateDto.getParent_id());
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setCreatetime(System.currentTimeMillis());
        comment.setCommentor(user.getId());
        commentMapper.insert(comment);

        if (commentCreateDto.getType()==2){
            //把回复评论的通知插入数据库
            Notification notification=new Notification();
            notification.setNotifier(comment.getCommentor());
            Comment comment2=commentMapper.getparentbyid(commentCreateDto.getParent_id());
            notification.setReceiver(comment2.getCommentor());
            notification.setOuterid(commentCreateDto.getParent_id());
            notification.setType(notificationEnum.NOTIFICATION_COMMENT.getType());
            notification.setCreatetime(System.currentTimeMillis());
            notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
            notificationMapper.insert(notification);

            //增加评论数
            commentMapper.updatecommentcount(commentCreateDto.getParent_id());
        }else {
            //把回复问题的通知插入数据库
            Question question=questionMapper.getbyId(commentCreateDto.getParent_id());
            Notification notification=new Notification();
            notification.setNotifier(user.getId());
            notification.setReceiver(question.getCreateid());
            notification.setOuterid(commentCreateDto.getParent_id());
            notification.setType(notificationEnum.NOTIFICATION_QUESTION.getType());
            notification.setCreatetime(System.currentTimeMillis());
            notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
            notificationMapper.insert(notification);
            //增加问题回复量
            questionMapper.updatecomment(commentCreateDto.getParent_id());
        }
        ResultDto resultDto=new ResultDto();
        return resultDto.success();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDto<List<CommentDto>> comments(@PathVariable(name = "id") int id,
                                                HttpServletRequest request){
        //查找type=2，即是回复评论的评论
        List<Comment> comments = commentMapper.getCommentByid(id,2);
        List<CommentDto> commentDto=new ArrayList<>();
        //找到User
        Cookie[] cookies = request.getCookies();
        User user = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                user = userBlogMapper.findBytoken(token);
                break;
            }
        }
        //把二级评论和对应的User写进每个CommentDto集合中
        for (Comment comment:comments){
            CommentDto dto=new CommentDto();
            BeanUtils.copyProperties(comment,dto);
            dto.setUser(user);
            commentDto.add(dto);
        }
        ResultDto resultDto=new ResultDto();
        //返回数据给前端
        return resultDto.success(commentDto);
    }

    @RequestMapping(value = "/search.do", method = RequestMethod.POST)
    public String handleSearchRequest(@RequestParam("searchTerm") String searchTerm ,Model model,
                                      @RequestParam(name = "page", defaultValue = "1") int page,
                                      @RequestParam(name = "size", defaultValue = "10") int size) {

        List<Question> search = questionMapper.search(searchTerm);
        PageDto pagination = questionService.list(search,page, size);
        model.addAttribute("pagination", pagination);

        // 返回结果页面
        return "index";
    }
}
