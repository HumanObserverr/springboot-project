package com.miao.springdemo.service.impl;

import com.miao.springdemo.dao.DoctorMapper;
import com.miao.springdemo.domain.Doctor;
import com.miao.springdemo.service.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorsImpl implements DoctorsService {
    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public List<Doctor> findAll() {
        return doctorMapper.findAll();
    }

    @Override
    public List<Doctor> findId(String key) {
        return doctorMapper.findId(key);
    }

    @Override
    public Doctor selectId(String id) {
        return doctorMapper.selectId(id);
    }

    @Override
    public List<Doctor> getDoctorsByPage(int page, int pageSize , String key) {
        int start = (page-1)*10;
        return doctorMapper.getDoctorsByPage(start,pageSize,key);
    }


}
