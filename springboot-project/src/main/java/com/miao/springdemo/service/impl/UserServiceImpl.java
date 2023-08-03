package com.miao.springdemo.service.impl;

import com.miao.springdemo.dao.UserMapper;
import com.miao.springdemo.domain.User;
import com.miao.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;



    @Override
    public int add(String id, String username, String password, String id_number,String token) {
        int flag = 0;
        try {
            int add = userMapper.add(id, username, password, id_number,token);
            if (add == 1){
                flag = add;
            }
        }catch (Exception e){
//            e.getMessage();
            System.out.println("sql添加失败");
        }

        return flag;
    }

    @Override
    public User selectId(String id , String password) {
        return userMapper.selectId(id,password);
    }

    @Override
    public User userDesc(String id) {
        return userMapper.userDesc(id);
    }

    @Override
    public int upInfo(String id, String username, String gender, String email, String location, String phone, String birth) {
//        return userMapper.upInfo(id,username,gender,id_number,email,location,phone,birth);
        int flag = userMapper.upInfo(id, username, gender, email, location, phone, birth);
        return flag;

    }
}
