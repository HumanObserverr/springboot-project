package com.miao.springdemo.controller;
/**
 * 此处AI部分使用的是百度提供的数据库
 */

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class StartController {
    @RequestMapping("/chatRoobot")
    public void chat(HttpServletRequest req, HttpServletResponse resp, @RequestParam("message") String message) throws IOException, InterruptedException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        System.out.println(message);
        String pythonPath = "springboot-project/doctor/doctor.py";
        //此处路径为本地Python路径
        String[] arguments = new String[] {"D:\\Program\\Python\\Python311\\python.exe",pythonPath,message};//指定命令、路径、传递的参数
        StringBuilder sbrs = null;
        StringBuilder sberror = null;
        ProcessBuilder builder = new ProcessBuilder(arguments);
        Process process = builder.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "gb2312"));
        BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream(), "utf-8"));
        String line=null;
        sbrs=new StringBuilder();
        sberror=new StringBuilder();
        while ((line=in.readLine())!=null){

            sbrs.append(line);
        }
        while ((line=error.readLine())!=null){
            sberror.append(line);
        }
        in.close();
        process.waitFor();
        if (sbrs.toString().equals("我不知道该怎样答复您。")){
            jsonObject.put("response","小e不知道该怎样答复您。请联系在线医师");
        }else {
            jsonObject.put("response",sbrs.toString());
        }

        System.out.println("sbrs"+sbrs.toString());
        System.out.println("sberror"+sberror.toString());
//        jsonObject.put("message",message);

        resp.getWriter().write(jsonObject.toJSONString());

    }


}
