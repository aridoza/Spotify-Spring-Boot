package com.example.springbootspotifylab.service;

import com.example.springbootspotifylab.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public String login(User user);

    public User getUser(String username);

    public String createUser(User user);

    public User addSong(String username, Long songId);
}
