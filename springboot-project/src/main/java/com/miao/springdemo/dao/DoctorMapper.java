package com.miao.springdemo.dao;

import com.miao.springdemo.domain.Doctor;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Mapper
public interface DoctorMapper {

    //查询全部
    public List<Doctor> findAll();
    //模糊查询
    public List<Doctor> findId(String key);
    //查询详情
    public Doctor selectId(String id);
    //分页列表
    List<Doctor> getDoctorsByPage(int start, int pageSize,String key);
}
