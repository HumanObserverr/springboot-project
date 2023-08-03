package com.miao.springdemo.blog.service;

import com.miao.springdemo.blog.dto.CommentDto;
import com.miao.springdemo.blog.entity.Comment;
import com.miao.springdemo.blog.mapper.CommentMapper;
import com.miao.springdemo.blog.mapper.UserBlogMapper;
import com.miao.springdemo.domain.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 三方参考后代码
 */
@Service
public class CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private UserBlogMapper userBlogMapper;

    public List<CommentDto> getByid(int id) {
        //通过文章id找到所有回复
        List<Comment> comments=commentMapper.getByid(id);
        //创建要给CommentDto的list
        List<CommentDto> lists=new ArrayList<>();
        //遍历每个Comment
        for(Comment comment:comments){
            //找到回复人
            User user=userBlogMapper.findById(comment.getCommentor());
            CommentDto commentDto=new CommentDto();
            //将第一个元素复制到第二个元素中
            BeanUtils.copyProperties(comment,commentDto);
            commentDto.setUser(user);
            lists.add(commentDto);
        }
        return lists;
    }
}
