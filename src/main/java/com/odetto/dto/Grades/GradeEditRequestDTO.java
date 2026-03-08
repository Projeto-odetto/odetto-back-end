package com.odetto.dto.Grades;

import lombok.Data;

@Data
public class GradeEditRequestDTO {
    private String studentName;
    private String subjectName;
    private Integer gradeIndex;
    private Double newGradeValue;
}