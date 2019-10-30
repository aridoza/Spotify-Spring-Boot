package com.example.springbootspotifylab.repositories;

import com.example.springbootspotifylab.model.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
