package com.example.springbootspotifylab.service;

import com.example.springbootspotifylab.model.UserRole;

public interface UserRoleService {
    public UserRole getRole(String name);

    public UserRole createRrole(UserRole userRole);
}
