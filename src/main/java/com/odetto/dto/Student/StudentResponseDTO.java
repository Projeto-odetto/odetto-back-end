package com.odetto.dto.Student;

import lombok.Value;

@Value
public class StudentResponseDTO {
    String enrollment;
    String name;
    String email;
    String cpf;
}
