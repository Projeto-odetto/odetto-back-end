package com.odetto.dto.Student;

import lombok.Data;

import java.util.List;

@Data
public class StudentEditSubjectsDTO {
    private Long enrollment;
    private List<String> addedSubjects;
    private List<String> removedSubjects;
}