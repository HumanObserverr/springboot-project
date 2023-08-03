package com.tjetc.service;

/*
import com.tjetc.dto.CommentDto;
import com.tjetc.entity.Comment;
import com.tjetc.entity.User;
import com.tjetc.mapper.CommentMapper;
import com.tjetc.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private UserMapper userMapper;

    public List<CommentDto> getByid(int id) {
        //通过文章id找到所有回复
        List<Comment> comments=commentMapper.getByid(id);
        //创建要给CommentDto的list
        List<CommentDto> lists=new ArrayList<>();
        //遍历每个Comment
        for(Comment comment:comments){
            //找到回复人
            User user=userMapper.findById(comment.getCommentor());
            CommentDto commentDto=new CommentDto();
            //将第一个元素复制到第二个元素中
            BeanUtils.copyProperties(comment,commentDto);
            commentDto.setUser(user);
            lists.add(commentDto);
        }
        return lists;
    }
}


 */