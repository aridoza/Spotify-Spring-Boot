package com.example.springbootspotifylab.controller;

import com.example.springbootspotifylab.model.User;
import com.example.springbootspotifylab.model.UserProfile;
import com.example.springbootspotifylab.service.UserProfileServiceStub;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserProfileControllerTest {
    private UserProfileController userProfileController;


    @Before
    public void initializeUserProfileController(){
        userProfileController = new UserProfileController();
        userProfileController.setUserProfileService(new UserProfileServiceStub());
    }

    @Test
    public void createUserProfile_SaveUserProfile_Success(){
        UserProfile userProfile = new UserProfile();
        userProfile.setEmail("batman@superhero.com");

        UserProfile newProfile = userProfileController.createUserProfile("batman", userProfile);

        Assert.assertNotNull(newProfile);
        Assert.assertEquals(newProfile.getEmail(), userProfile.getEmail());
    }

    @Test
    public void getUserProfile_UserProfile_Success() {
        UserProfile userProfile = new UserProfile();
        userProfile.setEmail("batman@superhero.com");
        User user = new User();
        user.setUsername("Robin");
        user.setUserProfile(userProfile);

        userProfile.setUser(user);

        UserProfile newProfile = userProfileController.getUserProfile(userProfile.getUser().getUsername());
        Assert.assertNotNull(newProfile);
        Assert.assertEquals(newProfile.getEmail(), userProfile.getEmail());
    }
}
