package com.example.springbootspotifylab.controller;

import com.example.springbootspotifylab.model.JwtResponse;
import com.example.springbootspotifylab.model.User;
import com.example.springbootspotifylab.service.SongService;
import com.example.springbootspotifylab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    SongService songService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user/list")
    public Iterable<User> listUsers() {
        return userService.listUsers();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return ResponseEntity.ok(new JwtResponse(userService.createUser(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        return ResponseEntity.ok(new JwtResponse(userService.login(user)));
    }

    @PutMapping("/user/{username}/{songId}")
    public User addSong(@PathVariable String username, @PathVariable Long songId) {
        return userService.addSong(username, songId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/user/{userId}")
    public HttpStatus deleteUserById(@PathVariable Long userId) {
        return userService.deleteById(userId);
    }

    @DeleteMapping("/user/{username}/{songId}")
    public User deleteSongFromUser(@PathVariable String username, @PathVariable Long songId) {
        return userService.deleteSongFromUser(username, songId);
    }
}
