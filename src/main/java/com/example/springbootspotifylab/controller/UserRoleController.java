package com.example.springbootspotifylab.controller;

import com.example.springbootspotifylab.model.UserRole;
import com.example.springbootspotifylab.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/{name}")
    public UserRole getRole(@PathVariable String name) {
        return userRoleService.getRole(name);
    }


    @PostMapping
    public UserRole createUserRole(@RequestBody UserRole userRole) {
        return userRoleService.createRrole(userRole);
    }
}
