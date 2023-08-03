package com.miao.springdemo.domain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Doctor implements Serializable {
    private Integer id;
    private String desc;
    private String name;
    private String position;
    private String hospital;
    private String category;

    public Doctor() {
    }

    public Doctor(Integer id, String desc, String name, String position, String hospital, String category) {
        this.id = id;
        this.desc = desc;
        this.name = name;
        this.position = position;
        this.hospital = hospital;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", hospital='" + hospital + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
