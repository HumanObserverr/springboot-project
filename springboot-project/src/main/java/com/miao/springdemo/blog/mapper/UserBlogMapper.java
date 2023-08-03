package com.miao.springdemo.blog.mapper;

import com.miao.springdemo.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserBlogMapper {
    @Insert("insert into user(name,password,token) values (#{name},#{password},#{token})")
    void insert(User user);

    @Select("select * from user where name=#{name} and password=#{password}")
    User select(User user);

    @Select("select  * from user where token=#{token}")
    User findBytoken(String token);

    @Select("select * from user where id=#{createid}")
    User findById(int createid);
}
