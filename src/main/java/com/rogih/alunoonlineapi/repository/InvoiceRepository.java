package com.rogih.alunoonlineapi.repository;

import com.rogih.alunoonlineapi.model.Invoice;
import com.rogih.alunoonlineapi.model.StudentFinance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    boolean existsByStudentFinancialAndDueDate(StudentFinance studentFinancial, LocalDateTime dueDate);
}
