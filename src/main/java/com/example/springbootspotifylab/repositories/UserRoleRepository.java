package com.example.springbootspotifylab.repositories;

import com.example.springbootspotifylab.model.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    public UserRole findByName(String name);
}
