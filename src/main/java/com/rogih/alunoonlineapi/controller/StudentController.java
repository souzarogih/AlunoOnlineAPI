package com.rogih.alunoonlineapi.controller;

import com.rogih.alunoonlineapi.dtos.CreateStudentRequest;
import com.rogih.alunoonlineapi.model.Student;
import com.rogih.alunoonlineapi.service.StudentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@RequestBody CreateStudentRequest createStudentRequest) {
        log.info("Controller create in student");
        return studentService.create(createStudentRequest);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Student> findAll() {
        log.info("Controller findAll in user");
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Student> findById(@PathVariable Long id) {
        log.info("Controller findById in user");
        return studentService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Student student,
                       @PathVariable Long id) {
        log.info("Controller update in user");
        studentService.update(id, student);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        log.info("Controller deleteById in user");
        studentService.deleteById(id);
    }

}
