
package com.miao.springdemo.controller;


import com.miao.springdemo.domain.User;
import com.miao.springdemo.pojo.Result;
import com.miao.springdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
//模拟登录操作
@Slf4j
public class CertificationController {
    @Autowired
    private UserService userService;

    private static boolean logoutFlag = false;

    public static boolean isLogoutFlag() {
        return logoutFlag;
    }

    public static void setLogoutFlag(boolean logoutFlag) {
        CertificationController.logoutFlag = logoutFlag;
    }


    @RequestMapping("/toLogin")
    public Result toLogin(String id, String password, HttpSession httpSession, HttpServletRequest request, HttpServletResponse response){

        Result result = new Result();
        httpSession.setMaxInactiveInterval(30*60);
                                                System.out.println(id +"登录验证中..");
        if(httpSession.getAttribute("id") != null){
            result.setFlag(false);
            result.setMessage("不支持一个浏览器登录多个用户！");
            return result;
        }
        User user = userService.selectId(id, password);
//        System.out.println(user);
        if (user != null){
            result.setFlag(true);
            System.out.println(id+"登录验证成功");
            logoutFlag = true;
//            User newUser = userMapper.select(user);
            String token = user.getToken();
            response.addCookie(new Cookie("token", token));
//            httpSession.setAttribute("user",user.getName());
            System.out.println(user.getName());
            httpSession.setAttribute("user",user.getName());
//            request.setAttribute("user",user.getName());
        }else{
            result.setFlag(false);
            System.out.println(id+"验证失败");
            result.setMessage("登录失败");
        }
        return result;
    }
    @RequestMapping("/getUsername")
    public String getUsername(HttpSession httpSession,HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute("user");
//        Cookie cookie=new Cookie("token",null);
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
        String username = (String) httpSession.getAttribute("username");
        return username;
    }
}











































/*

package com.miao.springdemo.controller;

import com.miao.springdemo.pojo.Result;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
//模拟登录操作
@Slf4j
public class CertificationController {
    @RequestMapping("/toLogin")
    public Result toLogin(String user, String pwd, HttpSession httpSession){
        Result result = new Result();
        httpSession.setMaxInactiveInterval(30*60);
        log.info(id+"登录验证中..");
        if(httpSession.getAttribute("id") != null){
            result.setFlag(false);
            result.setMessage("不支持一个浏览器登录多个用户！");
            return result;
        }
        if ("张三".equals(id)&&"123".equals(password)){
            result.setFlag(true);
            log.info(user+"登录验证成功");
            httpSession.setAttribute("user",user);
        }else if ("李四".equals(user)&&"123".equals(pwd)){
            result.setFlag(true);
            System.out.println(user + "登录验证成功");
            httpSession.setAttribute("user",user);
        }else if ("田七".equals(user)&&"123".equals(pwd)){
            result.setFlag(true);
            System.out.println(user+"登录验证成功");
            httpSession.setAttribute("user",user);
        }
        else if ("王五".equals(user)&&"123".equals(pwd)){
            result.setFlag(true);
            log.info(user+"登录验证成功");
            httpSession.setAttribute("user",user);
        }else {
            result.setFlag(false);
            log.info(user+"验证失败");
            result.setMessage("登录失败");
        }
        return result;
    }
    @RequestMapping("/getUsername")
    public String getUsername(HttpSession httpSession){
        String username = (String) httpSession.getAttribute("user");
//        System.out.println(username);
        return username;
    }
}
*/