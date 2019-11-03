package com.example.springbootspotifylab.controller;

import com.example.springbootspotifylab.model.UserRole;
import com.example.springbootspotifylab.service.UserRoleService;
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
@WebMvcTest(UserRoleController.class)
public class UserRoleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserRoleService userRoleService;

    @MockBean
    UserService userService;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    User user;

    @InjectMocks
    UserRole userRole;


    @Before
    public void initUser() {
        user.setId(1L);
        userRole.setName("ADMIN");
        user.setUserRole(userRole);
    }

    @Test
    @WithMockUser(username = "batman", password = "bat", roles = {"DBA"})
    public void getRole_Role_Success() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/role/"+user.getUserRole())
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "batman", password = "bat", roles = {"DBA"})
    public void createUserRole_UserRole_Success() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/role")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "12345")
                .content(createUserRoleInJson("ROLE_BASIC"));

        when(userRoleService.createRole(any())).thenReturn(userRole);

        ObjectMapper mapper = new ObjectMapper();
        String userRoleMapper = mapper.writeValueAsString(userRole);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(userRoleMapper))
                .andReturn();

        System.out.println(result.getResponse());
    }

    private static String createUserRoleInJson (String roleName) {
        return "{ \"name\": \""+roleName+"\" }";
    }
}
