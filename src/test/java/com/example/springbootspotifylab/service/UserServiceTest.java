package com.example.springbootspotifylab.service;


import com.example.springbootspotifylab.config.JwtUtil;
import com.example.springbootspotifylab.model.Song;
import com.example.springbootspotifylab.model.User;
import com.example.springbootspotifylab.model.UserRole;
import com.example.springbootspotifylab.repositories.SongRepository;
import com.example.springbootspotifylab.repositories.UserRepository;
import io.jsonwebtoken.lang.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    User user;

    @InjectMocks
    UserRole userRole;

    @Mock
    UserRoleService userRoleService;

    @Mock
    SongRepository songRepository;

    @Mock
    JwtUtil jwtUtil;

    @InjectMocks
    Song song;
    @Mock
    UserDetails userDetails;

    @Mock
    PasswordEncoder bCryptPasswordEncoder;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void dummyInit() {
        user.setUsername("Robin");
        user.setId(1L);
        user.setUserRole(userRole);
        userRole.setName("ROLE_DBA");
        song.setId(1L);
        user.addSong(song);
    }

    @Test
    public void logic_User_Success() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        when(bCryptPasswordEncoder.matches(any(), any())).thenReturn(true);
        when(bCryptPasswordEncoder.encode(any())).thenReturn("Robin");
        when(jwtUtil.generateToken(any())).thenReturn("123456");
        String actualToken = userService.login(user);
        Assert.notNull(actualToken);
    }

    @Test
    public void getUser_User_Success() {
        when(userRepository.findByUsername(any())).thenReturn(user);
        User actualUser = userService.getUser(user.getUsername());
        assertEquals(user, actualUser);
    }

    @Test
    public void createUser_User_Success() {
        when(userRoleService.getRole(any())).thenReturn(userRole);
        when(bCryptPasswordEncoder.encode(any())).thenReturn("bat");
        when(userRepository.findByUsername(any())).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);
        when(jwtUtil.generateToken(any())).thenReturn("123456");
        String actualToken = userService.createUser(user);
        Assert.notNull(actualToken);
    }

    @Test
    public void deleteById_User_Success() {
        HttpStatus status = userService.deleteById(user.getId());
        assertEquals(status, HttpStatus.OK);
    }

    @Test
    public void addSong_User_Success() {
        when(userRepository.findByUsername(any())).thenReturn(user);
        when(songRepository.findById(1L)).thenReturn(Optional.of(song));
        User actualUser = userService.addSong(user.getUsername(), 1L);
        assertEquals(actualUser, user);
    }

    @Test
    public void listUsers_User_Success() {
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findAll()).thenReturn(users);
        List<User> listOfUsers = (ArrayList) userService.listUsers();
        assertEquals(users, listOfUsers);
    }

    @Test
    public void deleteSongFromUser_User_Success() {
        when(userRepository.findByUsername(any())).thenReturn(user);
        when(songRepository.findById(1L)).thenReturn(Optional.of(song));
        when(userRepository.save(user)).thenReturn(user);

        User actualUser = userService.deleteSongFromUser("Robin", 1L);

        assertEquals(actualUser, user);

    }
}
