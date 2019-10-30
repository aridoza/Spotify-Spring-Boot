package com.example.springbootspotifylab.service;

import com.example.springbootspotifylab.model.Course;
import com.example.springbootspotifylab.model.User;
import com.example.springbootspotifylab.model.UserRole;
import com.example.springbootspotifylab.repositories.CourseRepository;
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
    private CourseRepository courseRepository;

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
    public User addCourse(String username, Long courseId) {
        Course course = courseRepository.findById(courseId).get();
        User user = getUser(username);
        user.addCourse(course);

        return userRepository.save(user);
    }
}
