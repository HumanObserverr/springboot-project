package com.miao.springdemo.service;

import com.miao.springdemo.domain.User;


public interface UserService {

    public int add(String id, String username, String password, String id_number, String token);

    public User selectId(String id, String password);
    public User userDesc(String id);
    public int upInfo(String id, String username, String gender, String email, String location, String phone, String birth);

}
