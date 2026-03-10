package com.odetto.dto.Teacher;

import lombok.Data;
import java.util.List;

@Data
public class TeacherSubjectEditRequestDTO {
    private Long cpf;
    private List<String> addedSubjects;
    private List<String> removedSubjects;
}