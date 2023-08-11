package com.miao.springdemo.service;

import com.miao.springdemo.blog.dto.PageDto;
import com.miao.springdemo.domain.Doctor;

import java.util.List;

public interface DoctorsService {
    public List<Doctor> findAll();
    public List<Doctor> findId(String key);
    public Doctor selectId(String id);
    List<Doctor> getDoctorsByPage(int page, int pageSize, String key);
    public PageDto list(int page, int size , String key);

}
