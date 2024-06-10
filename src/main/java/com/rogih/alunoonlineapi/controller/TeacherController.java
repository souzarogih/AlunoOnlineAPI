package com.rogih.alunoonlineapi.controller;

import com.rogih.alunoonlineapi.model.Teacher;
import com.rogih.alunoonlineapi.service.TeacherService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Teacher create(@RequestBody Teacher teacher) {
        log.info("Controller create in user");
        return teacherService.create(teacher);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Teacher> findAll() {
        log.info("Controller findAll in teacher");
        return teacherService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Teacher> findById(@PathVariable Long id) {
        log.info("Controller findById in teacher");
        return teacherService.findById(id);
    }
}
