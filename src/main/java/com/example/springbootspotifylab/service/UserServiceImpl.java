package com.example.springbootspotifylab.service;

import com.example.springbootspotifylab.model.Song;
import com.example.springbootspotifylab.model.User;
import com.example.springbootspotifylab.model.UserRole;
import com.example.springbootspotifylab.repositories.SongRepository;
import com.example.springbootspotifylab.repositories.UserRepository;
import com.example.springbootspotifylab.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private SongRepository songRepository;
    @Override
    public User login(User user) {
        return userRepository.login(user.getUsername(), user.getPassword());
    }
    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User createUser(User user) {
        UserRole userRole = userRoleRepository.findByName(user.getUserRole().getName());
        user.setUserRole(userRole);
        return userRepository.save(user);
    }

    @Override
    public User addSong(String username, Long songId) {
        User user = userRepository.findByUsername(username);
        Song song = songRepository.findById(songId).get();
        user.addSong(song);
        userRepository.save(user);
        return user;
    }
}
