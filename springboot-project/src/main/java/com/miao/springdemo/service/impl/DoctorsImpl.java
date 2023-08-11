package com.miao.springdemo.service.impl;

import com.miao.springdemo.blog.dto.PageDto;
import com.miao.springdemo.blog.dto.Questiondto;
import com.miao.springdemo.blog.entity.Question;
import com.miao.springdemo.dao.DoctorMapper;
import com.miao.springdemo.domain.Doctor;
import com.miao.springdemo.domain.User;
import com.miao.springdemo.service.DoctorsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
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
//        int start = (page-1)*10;
        return doctorMapper.getDoctorsByPage(page,pageSize,key);
    }

    public PageDto list(int page, int size , String key) {
        PageDto pageDto = new PageDto();
        int totalcount = 0;
        if (key.equals("科")){
           totalcount = doctorMapper.findAll().size();
        }else {
            totalcount = doctorMapper.findId(key).size();
//            System.out.println(totalcount);
        }

        pageDto.setPagination(totalcount,page,size);
        //size*{page-1}
        int offset = size * (page - 1);
        //每页只展示10条
        List<Doctor> doctors = doctorMapper.getDoctorsByPage(offset, size, key);
        List<Doctor> doctorList = new ArrayList<>();

        for (Doctor doctor :doctors) {
            doctorList.add(doctor);
        }
        pageDto.setData(doctorList);
        return pageDto;
    }


}
