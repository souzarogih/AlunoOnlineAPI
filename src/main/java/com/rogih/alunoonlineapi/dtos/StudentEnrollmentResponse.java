package com.rogih.alunoonlineapi.dtos;

import com.rogih.alunoonlineapi.enums.EnrollmentStudentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentEnrollmentResponse {
    private Long id;

    private Long student_id;

    private Long subject_id;

    private EnrollmentStudentStatusEnum status;
}
