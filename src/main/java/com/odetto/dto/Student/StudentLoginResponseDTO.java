package com.odetto.dto.Student;

import lombok.Value;

@Value
public class StudentLoginResponseDTO {
    Long enrollment;
    String name;
    String email;
    String password;
    Long cpf;
    boolean firstLogin;
}