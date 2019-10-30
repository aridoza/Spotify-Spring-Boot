package com.example.springbootspotifylab.service;

import com.example.springbootspotifylab.model.Course;
import com.example.springbootspotifylab.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Iterable<Course> listOfCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }
}
