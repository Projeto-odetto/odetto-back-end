package com.odetto.dto.Grades;

import lombok.Value;

import java.util.List;

@Value
public class StudentGradeResponseDTO {
    String subjectName;
    String studentName;
    List<Double> grades;
    Double average;
}
