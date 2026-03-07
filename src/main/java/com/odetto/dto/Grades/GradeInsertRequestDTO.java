package com.odetto.dto.Grades;

import lombok.Data;

@Data
public class GradeInsertRequestDTO {
    private String studentName;
    private String subjectName;
    private Double gradeValue;
}