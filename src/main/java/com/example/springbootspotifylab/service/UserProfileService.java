package com.example.springbootspotifylab.service;

import com.example.springbootspotifylab.model.UserProfile;

public interface UserProfileService {
    public UserProfile createUserProfile(String username, UserProfile newProfile);

    public UserProfile getUserProfile(String username);
}
