package com.example.springbootspotifylab.controller;

import com.example.springbootspotifylab.model.UserProfile;
import com.example.springbootspotifylab.model.UserRole;
import com.example.springbootspotifylab.service.UserProfileService;
import com.example.springbootspotifylab.service.UserRoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springbootspotifylab.config.JwtUtil;
import com.example.springbootspotifylab.model.Song;
import com.example.springbootspotifylab.model.User;
import com.example.springbootspotifylab.service.SongService;
import com.example.springbootspotifylab.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserProfileController.class)
public class UserProfileControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserProfileService userProfileService;

    @MockBean
    UserService userService;

    @MockBean
    JwtUtil jwtUtil;

    @InjectMocks
    User user;

    @InjectMocks
    UserProfile userProfile;

    @InjectMocks
    UserRole userRole;

    @Before
    public void initUser() {
        user.setId(1L);
        user.setUsername("batman");
        user.setUserRole(userRole);
    }

    @Test
    @WithMockUser(username = "batman", password = "bat", roles = {"DBA"})
    public void getProfile_UserProfile_Success() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/profile/"+user.getUsername())
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "batman", password = "bat", roles = {"DBA"})
    public void createUserProfile_UserProfile_Success() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/profile/"+user.getUsername())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserProfileInJson("batman@superhero.com","555-123-4567", "123 Main St"));

        when(userProfileService.createUserProfile(any(), any())).thenReturn(userProfile);

        ObjectMapper mapper = new ObjectMapper();
        String userProfileMapper = mapper.writeValueAsString(userProfile);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(userProfileMapper))
                .andReturn();

        System.out.println(result.getResponse());
    }

    private static String createUserProfileInJson (String email, String mobile, String address) {
        return "{ \"email\": \"" + email + "\", " +
                "\"mobile\":\"" + mobile + "\", " +
                "\"address\":\"" + address + "\" }";
    }
}
