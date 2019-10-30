package com.example.springbootspotifylab.controller;

import com.example.springbootspotifylab.model.UserProfile;
import com.example.springbootspotifylab.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class UserProfileController {
    @Autowired
    UserProfileService userProfileService;

    @GetMapping("/{username}")
    public UserProfile getUserProfile(@PathVariable String username) {
        return userProfileService.getUserProfile(username);
    }

    @PostMapping("/{username}")
    public UserProfile createUserProfile(@PathVariable String username, @RequestBody UserProfile userProfile) {
        System.out.println(username);
        return userProfileService.createUserProfile(username, userProfile);
    }
}
