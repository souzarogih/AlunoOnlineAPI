package com.rogih.alunoonlineapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String enrollmentId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}


//https://github.com/kelsonvictr/tec-backend-uniesp-2024-1/blob/main/src/main/java/br/com/alunoonline/api/model/Aluno.java