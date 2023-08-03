package com.miao.springdemo.controller;
/*
import com.miao.springdemo.Repository.UserRepository;
import com.miao.springdemo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(@RequestParam("id") String id,
                        @RequestParam("password") String password,
                        Model model) {
        User user = userRepository.findByUsername(id); // 假设根据用户名查找用户
        if (user != null && user.getPassword().equals(password)) {
            // 登录成功
            model.addAttribute("message", "登录成功");
            return "success";
        } else {
            // 登录失败
            model.addAttribute("error", "登录失败，请检查账号和密码");
            return "login";
        }
    }
}

*/