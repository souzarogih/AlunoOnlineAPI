package com.rogih.alunoonlineapi.repository;

import com.rogih.alunoonlineapi.model.Course;
import com.rogih.alunoonlineapi.model.StudentFinance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentFinanceRepository extends JpaRepository<StudentFinance, Long> {

    Optional<StudentFinance> findByStudentId(Long studentId);
}
