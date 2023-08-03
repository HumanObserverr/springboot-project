package com.miao.springdemo.controller;


//import com.miao.springdemo.domain.Comment;
import com.miao.springdemo.domain.Doctor;
import com.miao.springdemo.domain.User;
//import com.miao.springdemo.service.BlogService;
//import com.miao.springdemo.service.CommentService;
import com.miao.springdemo.service.DoctorsService;
import com.miao.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
public class PathController {
    private String username;
    @Autowired
    private DoctorsService doctorsService;
    @Autowired
    private UserService userService;
//    @Autowired

//    private BlogService blogService;
//    @Autowired
//    private CommentService commentService;

    @GetMapping("cat.do")
    public String Cat(HttpServletRequest servletRequest, @RequestParam("key") String key,@RequestParam(defaultValue = "1") int page, Model model,HttpSession httpSession){
        List<Doctor> doctors = doctorsService.findId(key);
        System.out.println("doctors:" + doctors);
        //计算页数
        int pageSize = 10;
        int totalDoctors = doctors.size();
        int totalPages = (int) Math.ceil((double) totalDoctors / pageSize);

        List<Doctor> doctorList = doctorsService.getDoctorsByPage(page,pageSize,key);
        System.out.println("doctorList" + doctorList);
        model.addAttribute("doctors", doctorList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        servletRequest.setAttribute("doctors",doctors);
        servletRequest.setAttribute("username",username);

        httpSession.setAttribute("user",username);
        return "list.html";
    }


    @RequestMapping("home.do")
    public String home(HttpServletRequest servletRequest,HttpSession httpSession){
        servletRequest.setAttribute("username",username);
        httpSession.setAttribute("user",username);
        return "index1.html";
//        return "index.html";
    }


    @RequestMapping("to{path}.do")
    public String path(@PathVariable("path") String path){
        System.out.println(path);

        return path + ".html";
//        return "index.html";
    }
    @RequestMapping("login.do")
    public String login(HttpServletRequest servletRequest, HttpSession httpSession){

//        this.username =(String) servletRequest.getSession().getAttribute("user");
        this.username = (String)httpSession.getAttribute("user");
        System.out.println("登录名字" + username);
//        System.out.println("登录密码" + password);
        System.out.println();
        servletRequest.getSession().setAttribute("username",username);
        return "index1.html";
    }
    @GetMapping("/findAll.do")
    public String findAllDoctors(@RequestParam(defaultValue = "1") int page, Model model,HttpSession httpSession) {


        List<Doctor> allDoctors = doctorsService.findAll();
        int pageSize = 10;
        int totalDoctors = allDoctors.size();
        int totalPages = (int) Math.ceil((double) totalDoctors / pageSize);

        List<Doctor> doctors = doctorsService.getDoctorsByPage(page,pageSize,"科");
        System.out.println(doctors.size());
        model.addAttribute("doctors", doctors);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        httpSession.setAttribute("user",username);
        return "list.html";
    }
    @RequestMapping("findId.do")
    public String limitList(HttpServletRequest request,String key,HttpSession httpSession){
        List<Doctor> doctors = doctorsService.findId(key);
        request.setAttribute("doctors",doctors);
        httpSession.setAttribute("user",username);
        return "list.html";
    }

    @RequestMapping("person.do")
    public String PersonList(HttpServletRequest request,String id,HttpSession httpSession){
        System.out.println(id);
        Doctor doctor = doctorsService.selectId(id);
        request.setAttribute("doctor",doctor);
        httpSession.setAttribute("user",username);
        return "person.html";
    }

    //AI聊天
    @RequestMapping("ai.do")
    public String chat(HttpSession httpSession){
        httpSession.setAttribute("user",username);
        return "chat.html";
    }


//在线聊天
    @RequestMapping("chat2.do")
    public String chat01(HttpServletRequest servletRequest,HttpSession httpSession){

        httpSession.setAttribute("user",username);
        servletRequest.getSession().setAttribute("username",username);
        return "/main.html";
    }
//退出
    @RequestMapping("out.do")
    public String out(HttpSession session){
        //清除会话
        session.invalidate();
        CertificationController.setLogoutFlag(false);
        System.out.println(username + "已退出");
        return "login_1.html";
    }
    //首页
    @RequestMapping("/")
    public String home(){
        return "login_1.html";

    }

//用户中心
    @RequestMapping("user.do")
    public String user(HttpServletRequest servletRequest){
        System.out.println("用户中心" + username);

        User user = userService.userDesc(username);

        servletRequest.getSession().setAttribute("user",user);
//        System.out.println(user);

//        servletRequest.getSession().setAttribute("username",username);
        return "user.html";
    }

    @RequestMapping("add.do")
    public String add(String id, String username, String password, String id_number, Model model,HttpServletRequest request, HttpServletResponse response,HttpSession httpSession){
//        int flag = userService.add(id, username, password, id_number);

        System.out.println("idcard" + id_number);
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
        //随机生成一个token用来当cookies的value
        String token = UUID.randomUUID().toString();
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setToken(token);
        //设置一个默认的头像
        user.setHeadpic("https://gitee.com/lyucoding/java-starter/raw/main/images/GitHub.png");
        int flag = userService.add(id, username, password,id_number,token);
        String msg = "";
        if (flag == 1){
            msg = "已成功添加" + flag + "用户";
            System.out.println(msg);

        }
        else {
            msg = "添加用户失败！账号不唯一";
            System.out.println(msg);
        }
        //如果用户注册成功，则把用户信息写进session，直接跳转到主页
        if (userService.selectId(id,password) != null) {
            response.addCookie(new Cookie("token", token));


        } else {
            //注册失败，处理方法先省略

        }
        httpSession.setAttribute("user",username);
        return "login_1.html";


    }


    @RequestMapping("update.do")
    public String updata(HttpServletRequest servletRequest,String username,String gender,String email, String location,String phone,String birth,HttpSession httpSession){
        String id = this.username;
        if (birth == null){
            System.out.println("生日不允许为空！");
            return "user.html";
        }
        userService.upInfo(id, username, gender, email, location, phone, birth);
        System.out.println("已成功更新" + this.username + "用户");
        User user = userService.userDesc(id);
        servletRequest.getSession().setAttribute("user",user);
        servletRequest.getSession().setAttribute("username",this.username);
        httpSession.setAttribute("user",username);
        return "user.html";
    }


}
