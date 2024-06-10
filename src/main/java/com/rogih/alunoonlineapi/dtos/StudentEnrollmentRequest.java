package com.rogih.alunoonlineapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentEnrollmentRequest {

    private Long student_id;
    private Long subject_id;

}
