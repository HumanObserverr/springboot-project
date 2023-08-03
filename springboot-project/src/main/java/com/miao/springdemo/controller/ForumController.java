package com.miao.springdemo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ForumController {
    private List<Post> posts = new ArrayList<>();

    @GetMapping("/posts")
    public List<Post> getPosts() {
        return posts;
    }

    @PostMapping("/posts")
    public void createPost(@RequestBody Post post) {
        posts.add(post);
    }

    public static class Post {
        private String username;
        private String content;
        private List<String> comments;

        // 省略构造函数、getter和setter

        public Post() {
            this.comments = new ArrayList<>();
        }
    }
}
