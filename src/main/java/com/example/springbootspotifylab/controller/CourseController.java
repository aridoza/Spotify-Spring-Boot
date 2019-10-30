package com.example.springbootspotifylab.controller;

import com.example.springbootspotifylab.model.Course;
import com.example.springbootspotifylab.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/list")
    public Iterable<Course> listOfCourses() {
        return courseService.listOfCourses();
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }
}
