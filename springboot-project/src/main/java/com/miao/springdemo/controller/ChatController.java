package com.miao.springdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@RestController
public class ChatController {



    @GetMapping(value = "/chatRoobot")
    public String chat(@RequestParam("message") String message) throws IOException {
        // 启动Python聊天脚本的进程
        ProcessBuilder pb = new ProcessBuilder("python", "path/chatWithRoobot/chatbot_graph.py", message);
        Process process = pb.start();

        System.out.println("python");
        // 读取Python脚本的输出
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
            output.append(line).append("\n");
        }
        // 读取Python脚本的错误流
        BufferedReader stderrReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuilder errors = new StringBuilder();
        String errorLine;
        while ((errorLine = stderrReader.readLine()) != null) {
            errors.append(errorLine).append("\n");
        }

// 打印输出和错误消息
        System.out.println("Output: " + output.toString());
        System.out.println("Errors: " + errors.toString());

        return output.toString();
    }

}
