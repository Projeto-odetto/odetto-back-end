package com.odetto.dto.Teacher;

import lombok.Value;

import java.util.List;

@Value
public class TeacherResponseDTO {
    String cpf;
    String name;
    String username;
    String hireDate;
    List<String> subjects;
}
