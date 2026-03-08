package com.odetto.dto.Grades;

public record GradeInsertResponseDTO(
        String studentName,
        Long reportCardId,
        String subjectName,
        Double addedGrade
) {}
