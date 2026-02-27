package com.odetto.dto.Grades;

import lombok.Value;

@Value
public class StudentGradeResponseDTO {
    String subjectName;
    String studentName;
    Double[] grades;
    Double average;
}
