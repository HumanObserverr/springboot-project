package com.miao.springdemo.dao;

import com.miao.springdemo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    public int add(String id, String username, String password, String id_number, String token);

//    @Select("select * from user where name=#{id} and password=#{password}")
    public User selectId(String id, String password);
    public User userDesc(String id);
    public int upInfo(String id, String username, String gender, String email, String location, String phone, String birth);
//    加分事件
    public int addSource(String id);
}
