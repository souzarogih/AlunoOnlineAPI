package com.rogih.alunoonlineapi.controller;

import com.rogih.alunoonlineapi.dtos.StudentEnrollmentRequest;
import com.rogih.alunoonlineapi.dtos.StudentEnrollmentResponse;
import com.rogih.alunoonlineapi.dtos.StudentTranscriptResponse;
import com.rogih.alunoonlineapi.dtos.UpdateGradeRequest;
import com.rogih.alunoonlineapi.service.StudentEnrollmentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/enrollment-aluno")
public class StudentEnrollmentController {

    @Autowired
    StudentEnrollmentService studentEnrollmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentEnrollmentResponse create(@RequestBody StudentEnrollmentRequest studentEnrollmentRequest) {
        log.info("Controller create in student {}", studentEnrollmentRequest);

        System.out.println(studentEnrollmentRequest.getStudent_id());
        System.out.println(studentEnrollmentRequest.getSubject_id());

        return studentEnrollmentService.create(studentEnrollmentRequest);
    }

    /*Atualizar notas do aluno*/
    @PatchMapping("/update-grades/{enrollmentAlunoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGrades(@RequestBody UpdateGradeRequest updateGradeRequest,
                             @PathVariable Long enrollmentAlunoId) {
        log.info("Controller updateGrades in user");
        studentEnrollmentService.updateGrades(enrollmentAlunoId, updateGradeRequest);
    }

    /*Trancar matricula*/
    @PatchMapping("/update-status-to-break/{enrollmentStudentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatusToBreak(@PathVariable Long enrollmentStudentId) {
        log.info("Controller updateStatusToBreak in user");
        studentEnrollmentService.updateStatusToBreak(enrollmentStudentId);
    }

    @GetMapping("/academic-transcript/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public StudentTranscriptResponse getAcademicTranscript(@PathVariable Long studentId) {
        log.info("Controller getAcademicTranscript in user");
        return studentEnrollmentService.getAcademicTranscript(studentId);
    }
}
