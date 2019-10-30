package com.example.springbootspotifylab.controller;

import com.example.springbootspotifylab.model.User;
import com.example.springbootspotifylab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @PostMapping("/signup")
    public User saveUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user);
    }

    @PutMapping("/user/{username}/{courseId}")
    public User addCourse(@PathVariable String username, @PathVariable Long courseId){
        return userService.addCourse(username, courseId);
    }
}
