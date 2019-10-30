package com.example.springbootspotifylab.repositories;

import com.example.springbootspotifylab.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface  UserRepository extends CrudRepository<User, Long> {
    @Query("FROM User where username = ?1 and password = ?2")
    public User login(String username, String password);

    public User findByUsername(String username);
}
