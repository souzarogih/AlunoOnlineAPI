package com.rogih.alunoonlineapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_financial_id")
    private StudentFinance studentFinancial;

    private LocalDateTime dueDate;

    private LocalDateTime paidOn;

    private LocalDateTime createdAt;

    private Double value;
}
