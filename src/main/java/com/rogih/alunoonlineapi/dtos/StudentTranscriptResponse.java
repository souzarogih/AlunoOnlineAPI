package com.rogih.alunoonlineapi.dtos;

import lombok.Data;

import java.util.List;

@Data
public class StudentTranscriptResponse {
    private String studentName;
    private String studentEmail;
    private List<CourseStudentResponse> studentSubjectsResponseList;
}
