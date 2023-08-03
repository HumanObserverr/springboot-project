package com.miao.springdemo.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name ="user")
public class User implements Serializable {


    @Id
    private int id;
      private String name;
      private String password;
      private String id_number;
      private String username;
      private String email;
      private String location;
      private String phone;
      private String birth;
      private String gender;
    private String token;
   private String headpic;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getId_number() {
    return id_number;
  }

  public void setId_number(String id_number) {
    this.id_number = id_number;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getBirth() {
    return birth;
  }

  public void setBirth(String birth) {
    this.birth = birth;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getHeadpic() {
    return headpic;
  }

  public void setHeadpic(String headpic) {
    this.headpic = headpic;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", password='" + password + '\'' +
            ", id_number='" + id_number + '\'' +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", location='" + location + '\'' +
            ", phone='" + phone + '\'' +
            ", birth='" + birth + '\'' +
            ", gender='" + gender + '\'' +
            ", token='" + token + '\'' +
            ", headpic='" + headpic + '\'' +
            '}';
  }
}
