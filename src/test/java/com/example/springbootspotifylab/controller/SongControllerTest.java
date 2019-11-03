package com.example.springbootspotifylab.controller;

import com.example.springbootspotifylab.model.UserProfile;
import com.example.springbootspotifylab.model.UserRole;
import com.example.springbootspotifylab.service.UserProfileService;
import com.example.springbootspotifylab.service.UserRoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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
@WebMvcTest(SongController.class)
public class SongControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    SongService songService;

    @MockBean
    JwtUtil jwtUtil;

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
    public void listOfSongs_Song_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/song")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

    }

    @Test
    public void createSong_Song_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/song")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "12345")
                .content(createSongInJson("Testing the Test of the Tester", 3.27));

        when(songService.createSong(any())).thenReturn(song);

        ObjectMapper mapper = new ObjectMapper();
        String songMapper = mapper.writeValueAsString(song);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(songMapper));
    }

    private static String createSongInJson (String title, Double length) {
        return "{ \"title\": \"" + title + "\", " +
                "\"length\":\"" + length +"\" }";
    }


}
