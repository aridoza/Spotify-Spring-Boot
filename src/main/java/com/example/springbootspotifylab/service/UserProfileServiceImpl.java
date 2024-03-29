package com.example.springbootspotifylab.service;

import com.example.springbootspotifylab.model.User;
import com.example.springbootspotifylab.model.UserProfile;
import com.example.springbootspotifylab.repositories.UserProfileRepository;
import com.example.springbootspotifylab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public UserProfile createUserProfile(String username, UserProfile newProfile) {

        User user = userService.getUser(username);

        user.setUserProfile(newProfile);
        return userRepository.save(user).getUserProfile();
    }

    @Override
    public UserProfile getUserProfile(String username) {
        return userProfileRepository.findProfileByUsername(username);
    }
}