package com.example.springbootspotifylab.service;

import com.example.springbootspotifylab.model.User;

public interface UserService {

    public User login(User user);

    public User getUser(String username);

    public User createUser(User user);

    public User addSong(String username, Long songId);
}
