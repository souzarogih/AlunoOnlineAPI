package com.rogih.alunoonlineapi.service;

import com.rogih.alunoonlineapi.dtos.CreateStudentRequest;
import com.rogih.alunoonlineapi.enums.FinanceStatusEnum;
import com.rogih.alunoonlineapi.model.Course;
import com.rogih.alunoonlineapi.model.Student;
import com.rogih.alunoonlineapi.model.StudentFinance;
import com.rogih.alunoonlineapi.repository.CourseRepository;
import com.rogih.alunoonlineapi.repository.StudentFinanceRepository;
import com.rogih.alunoonlineapi.repository.StudentRepository;
import com.rogih.alunoonlineapi.utils.EnrollmentGenerate;
import com.rogih.alunoonlineapi.utils.WorkDate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentFinanceRepository studentFinanceRepository;

    @Autowired
    CourseRepository courseRepository;

    public Student create(CreateStudentRequest createStudentRequest) {
        log.info("Service create in use");

        String enrollmentIdGenerated = EnrollmentGenerate.generateEnrollmentNumberNow();

        Course course = courseRepository.findById(createStudentRequest.getCourseId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        Student studentSaved = studentRepository.save(
                new Student(
                        null,
                        createStudentRequest.getName(),
                        createStudentRequest.getEmail(),
                        enrollmentIdGenerated,
                        course
                )
        );

        //Salvando informações financeiras do estudante
        createFinanceiroInformation(studentSaved, createStudentRequest);

        return studentSaved;
    }

    public List<Student> findAll() {
        log.info("Service findAll in use");
        return studentRepository.findAll();
    }

    public Optional<Student> findById(Long id) {
        log.info("Service findById in use");

        return studentRepository.findById(id);
    }

    public void update(Long id, Student aluno) {
        log.info("Service update in use where id={}", id);

        Optional<Student> studentFromDb = findById(id);

        if (studentFromDb.isEmpty()) {
            log.info("Student {} not found in database", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found in database");
        }

        Student studentUpdated = studentFromDb.get();

        studentUpdated.setName(aluno.getName());
        studentUpdated.setEmail(aluno.getEmail());

        Student studentSaved = studentRepository.save(studentUpdated);
        log.info("Student updated saved: {}", studentSaved);
    }

    public void deleteById(Long id) {
        log.info("Service deleteById in use");

        studentRepository.deleteById(id);
    }

    private void createFinanceiroInformation(Student student, CreateStudentRequest createStudentRequest) {
        log.info("Service createFinanceiroInformation in use");

        StudentFinance studentFinance = new StudentFinance(
                null,
                student,
                createStudentRequest.getDiscount(),
                createStudentRequest.getDueDate(),
                FinanceStatusEnum.EM_DIA
        );

        StudentFinance studentFinanceDB = studentFinanceRepository.save(studentFinance);
        log.info("Dados financeiros criados com sucesso. id= {}", studentFinanceDB.getId());
    }
}
