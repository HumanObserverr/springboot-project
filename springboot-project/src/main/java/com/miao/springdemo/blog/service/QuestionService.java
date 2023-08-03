package com.miao.springdemo.blog.service;

//import com.miao.springdemo.blog.dto.PageDto;
//import com.miao.springdemo.blog.dto.Questiondto;
//import com.miao.springdemo.blog.entity.Question;
//import com.miao.springdemo.blog.mapper.QuestionMapper;
//import com.miao.springdemo.blog.mapper.UserBlogMapper;
import com.miao.springdemo.blog.dto.PageDto;
import com.miao.springdemo.blog.dto.Questiondto;
import com.miao.springdemo.blog.entity.Question;
import com.miao.springdemo.blog.mapper.QuestionMapper;
import com.miao.springdemo.blog.mapper.UserBlogMapper;
import com.miao.springdemo.domain.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private UserBlogMapper userBlogMapper;
//首页展示
    public PageDto list(int page, int size) {
        PageDto pageDto = new PageDto();
        int totalcount = questionMapper.count();
        pageDto.setPagination(totalcount,page,size);
        //size*{page-1}
        int offset = size * (page - 1);
        //每页只展示5条
        List<Question> questions = questionMapper.list(offset, size);
        List<Questiondto> questiondtoList = new ArrayList<>();

        for (Question question : questions) {
            User user = userBlogMapper.findById(question.getCreateid());
            Questiondto questiondto = new Questiondto();
            //把第一个对象的所有属性拷贝到第二个对象中
            BeanUtils.copyProperties(question, questiondto);
            questiondto.setUser(user);
            questiondtoList.add(questiondto);
        }
        pageDto.setData(questiondtoList);
        return pageDto;
    }

    public PageDto list(int userid, int page, int size) {
        PageDto pageDto = new PageDto();
        int totalcount = questionMapper.countbyid(userid);
        pageDto.setPagination(totalcount,page,size);
        //size*{page-1}
        int offset = size * (page - 1);
        //每页只展示5条
        List<Question> questions = questionMapper.listbyid(userid,offset, size);
        List<Questiondto> questiondtoList = new ArrayList<>();

        for (Question question : questions) {
            User user = userBlogMapper.findById(question.getCreateid());
            Questiondto questiondto = new Questiondto();
            //把第一个对象的所有属性拷贝到第二个对象中
            BeanUtils.copyProperties(question, questiondto);
            questiondto.setUser(user);
            questiondtoList.add(questiondto);
        }
        pageDto.setData(questiondtoList);
        return pageDto;
    }
    public PageDto list(List<Question> questions, int page, int size){
        PageDto pageDto = new PageDto();

        int totalcount = questions.size();
        pageDto.setPagination(totalcount,page,size);
        //size*{page-1}
        int offset = size * (page - 1);
        //每页只展示5条
        List<Questiondto> questiondtoList = new ArrayList<>();

        for (Question question : questions) {
            User user = userBlogMapper.findById(question.getCreateid());
            Questiondto questiondto = new Questiondto();
            //把第一个对象的所有属性拷贝到第二个对象中
            BeanUtils.copyProperties(question, questiondto);
            questiondto.setUser(user);
            questiondtoList.add(questiondto);
        }
        pageDto.setData(questiondtoList);
        return pageDto;
    }
//根据查找用户发布的帖子
    public Questiondto getbyid(int id) {
        Questiondto questiondto=new Questiondto();
        Question question=questionMapper.getbyId(id);
        //把第一个对象的所有属性拷贝到第二个对象中
        BeanUtils.copyProperties(question, questiondto);
        User user = userBlogMapper.findById(question.getCreateid());
        questiondto.setUser(user);
        return questiondto;
    }

    public void increaseview(int id) {
        questionMapper.updateView(id);
    }

    public List<Question> getbytag(int id, String result) {
        return questionMapper.getbytag(id,result);
    }

    public List<Question> gettopten() {
        List<Question> questions=questionMapper.gettopten();
        return questions;
    }
    public List<Question> search(String searchTerm){
        return questionMapper.search(searchTerm);
    }
}
