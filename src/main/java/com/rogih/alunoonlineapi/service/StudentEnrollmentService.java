package com.rogih.alunoonlineapi.service;

import com.rogih.alunoonlineapi.dtos.*;
import com.rogih.alunoonlineapi.enums.EnrollmentStudentStatusEnum;
import com.rogih.alunoonlineapi.model.Course;
import com.rogih.alunoonlineapi.model.Student;
import com.rogih.alunoonlineapi.model.StudentEnrollment;
import com.rogih.alunoonlineapi.repository.CourseRepository;
import com.rogih.alunoonlineapi.repository.StudentEnrollmentRepository;
import com.rogih.alunoonlineapi.repository.StudentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class StudentEnrollmentService {

    public static final double GRADE_AVG_TO_APPROVE = 7.0;

    @Autowired
    StudentEnrollmentRepository studentEnrollmentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    final String MESSAGE = "Matrícula não encontrada";

    public StudentEnrollmentResponse create(StudentEnrollmentRequest studentEnrollmentRequest) {
        log.info("Service create in use");

        Student studentData = studentRepository.findById(studentEnrollmentRequest.getStudent_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        Course courseData = courseRepository.findById(studentEnrollmentRequest.getSubject_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        if (studentData != null && courseData != null){
            log.info("Student: {} | Course: {}", studentData, courseData);

            StudentEnrollment studentCreated =  studentEnrollmentRepository.save(
                    new StudentEnrollment(
                            null,
                            null,
                            null,
                            studentData,
                            courseData,
                            EnrollmentStudentStatusEnum.MATRICULADO));
            log.info("Student created: {}", studentCreated);

            return new StudentEnrollmentResponse(
                    studentCreated.getId(),
                    studentCreated.getStudent().getId(),
                    studentCreated.getSubject().getId(),
                    studentCreated.getStatus()
            );
        }
        return null;
    }

    public void updateGrades(Long enrollmentStudentId, UpdateGradeRequest updateGradeRequest) {
        log.info("Service updateGrades in use for enrollment student id={}", enrollmentStudentId);

        StudentEnrollment studentEnrollment =
                studentEnrollmentRepository.findById(enrollmentStudentId)
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));
        updateStudentGrades(studentEnrollment, updateGradeRequest);
        updateStudentStatus(studentEnrollment);

        StudentEnrollment studentEnrollmentSaved = studentEnrollmentRepository.save(studentEnrollment);
        log.info("Notas do aluno salvas. StudentEnrollment={}",studentEnrollmentSaved);
    }

    private void updateStudentGrades(StudentEnrollment studentEnrollment, UpdateGradeRequest updateGradeRequest) {
        log.info("Service updateStudentGrades in use");

        if (updateGradeRequest.getGradeOne() != null) {
            studentEnrollment.setGrade1(updateGradeRequest.getGradeOne());
            System.out.println("updateStudentGrades" + studentEnrollment);
        }

        if (updateGradeRequest.getGradeTwo() != null) {
            studentEnrollment.setGrade2(updateGradeRequest.getGradeTwo());
            System.out.println("updateStudentGrades" + studentEnrollment);
        }
    }

    private void updateStudentStatus(StudentEnrollment studentEnrollment) {
        log.info("Service updateStudentStatus in use");

        Double nota1 = studentEnrollment.getGrade1();
        Double nota2 = studentEnrollment.getGrade2();

        if (nota1 != null && nota2 != null) {
            double average = (nota1 + nota2) / 2;
            studentEnrollment.setStatus(average >= GRADE_AVG_TO_APPROVE ? EnrollmentStudentStatusEnum.APROVADO : EnrollmentStudentStatusEnum.REPROVADO);
        }

    }

    public void updateStatusToBreak(Long matriculaAlunoId) {
        log.info("Service updateStatusToBreak in use");

        StudentEnrollment studentEnrollment =
                studentEnrollmentRepository.findById(matriculaAlunoId)
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));

        if (!EnrollmentStudentStatusEnum.MATRICULADO.equals(studentEnrollment.getStatus())) {
            log.info("O aluno não está matriculado.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Só é possível trancar uma matrícula com o status MATRICULADO");
        }

        changeStatus(studentEnrollment);
    }

    private void changeStatus(StudentEnrollment studentEnrollment) {
        log.info("Service changeStatus in use");

        studentEnrollment.setStatus(EnrollmentStudentStatusEnum.TRANCADO);
        studentEnrollmentRepository.save(studentEnrollment);
    }

    public StudentTranscriptResponse getAcademicTranscript(Long alunoId) {
        log.info("Service enrollmentList in use");

        List<StudentEnrollment> enrollmentsOfStudent = studentEnrollmentRepository.findByStudentId(alunoId);

        if(enrollmentsOfStudent.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This student has no enrollments");
        }

        StudentTranscriptResponse transcript = new StudentTranscriptResponse();
        transcript.setStudentName(enrollmentsOfStudent.get(0).getStudent().getName());
        transcript.setStudentEmail(enrollmentsOfStudent.get(0).getStudent().getEmail());

        List<CourseStudentResponse> enrollmentList = new ArrayList<>();

        for (StudentEnrollment enrollment : enrollmentsOfStudent) {
            CourseStudentResponse courseStudentResponse = new CourseStudentResponse();
            courseStudentResponse.setSubjectName(enrollment.getSubject().getName());
            courseStudentResponse.setTeacherName(enrollment.getSubject().getTeacher().getName());
            courseStudentResponse.setGradeOne(enrollment.getGrade1());
            courseStudentResponse.setGradeTwo(enrollment.getGrade2());

            if(enrollment.getGrade1() != null && enrollment.getGrade2() != null) {
                courseStudentResponse.setAverage((enrollment.getGrade1() + enrollment.getGrade2()) / 2.0);
            } else {
                courseStudentResponse.setAverage(null);
            }

            courseStudentResponse.setStatus(enrollment.getStatus());
            enrollmentList.add(courseStudentResponse);
        }

        transcript.setStudentSubjectsResponseList(enrollmentList);

        return transcript;
    }

}
