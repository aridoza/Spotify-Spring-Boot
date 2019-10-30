package com.example.springbootspotifylab.service;

import com.example.springbootspotifylab.model.Course;

import java.util.List;

public interface CourseService {
    public Iterable<Course> listOfCourses();

    public Course createCourse(Course course);
}
