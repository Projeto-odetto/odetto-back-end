package com.odetto.dto.Teacher;

import lombok.Value;

@Value
public class TeacherResponseDTO {
    String cpf;
    String name;
    String username;
    String hireDate;
    String subject;
}
