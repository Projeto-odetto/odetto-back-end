package com.odetto.dto.Teacher;

import lombok.Data;

@Data
public class TeacherRequestDTO {
    private Long cpf;
    private String name;
    private String username;
    private String password;
    private String hireDate;
}
