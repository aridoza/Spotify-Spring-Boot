package com.example.springbootspotifylab.service;


import com.example.springbootspotifylab.model.UserRole;
import com.example.springbootspotifylab.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserRole getRole(String name) {
        return userRoleRepository.findByName(name);
    }

    @Override
    public UserRole createRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }
}
