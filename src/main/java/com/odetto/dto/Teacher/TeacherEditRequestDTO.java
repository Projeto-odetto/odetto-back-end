package com.odetto.dto.Teacher;

import lombok.Data;

@Data
public class TeacherEditRequestDTO {
    private Long cpf;
    private String name;
    private String username;
    private String password;
}