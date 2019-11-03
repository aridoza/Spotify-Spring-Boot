package com.example.springbootspotifylab.controller;

import com.example.springbootspotifylab.config.JwtUtil;
import com.example.springbootspotifylab.model.Song;
import com.example.springbootspotifylab.model.User;
import com.example.springbootspotifylab.service.SongService;
import com.example.springbootspotifylab.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    SongService songService;

    @InjectMocks
    User user;

    @InjectMocks
    Song song;

    @Before
    public void initUser() {
        user.setId(1L);
        song.setId(1L);
        user.addSong(song);
    }

    @Test
    public void helloWorld_ReturnsString_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hello")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World!"));
    }

    @Test
    @WithMockUser(username = "batman", password = "bat", roles = {"ADMIN"})
    public void listUsers_UserController_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/list")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void signup_Token_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson("joe","abc", "ADMIN"));

        when(userService.createUser(any())).thenReturn("123456");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"123456\"}"))
                .andReturn();
    }

    @Test
    public void login_Token_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson("joe","abc", "ADMIN"));

        when(userService.login(any())).thenReturn("123456");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"123456\"}"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "batman", password = "bat", roles = {"ADMIN"})
    public void addSong_Song_Success() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/user/"+user.getId()+"/"+song.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "12345");

        when(userService.addSong(any(), any())).thenReturn(user);

        ObjectMapper mapper = new ObjectMapper();
        String userMapper = mapper.writeValueAsString(user);
        System.out.println(userMapper);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(userMapper))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "batman", password = "bat", roles = {"DBA"})
    public void deleteUserById_UserController_Success() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/user/"+user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "12345");

        when(userService.deleteById(any())).thenReturn(HttpStatus.OK);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "batman", password = "bat", roles = {"DBA"})
    public void deleteSongFromUser_UserController_Success() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/user/"+user.getUsername()+"/"+song.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "12345");

        when(userService.deleteSongFromUser(any(), any())).thenReturn(user);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }


    private static String createUserInJson (String name, String password, String roleName) {
        return "{ \"username\": \"" + name + "\", " +
                "\"password\":\"" + password + "\", " +
                "\"userRole\": { \"name\": \"" + roleName +"\" }}";
    }
}
