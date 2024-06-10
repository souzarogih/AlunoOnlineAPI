package com.rogih.alunoonlineapi.controller;

import com.rogih.alunoonlineapi.model.Course;
import com.rogih.alunoonlineapi.service.CourseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course create(@RequestBody Course course) {
        log.info("Controller create in user {}", course);
        return courseService.create(course);
    }

    @GetMapping("/teacher/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Course> findByTeacherId(@PathVariable Long id) {
        log.info("Controller findByTeacherId in user");
        return courseService.findByTeacherId(id);
    }
}
