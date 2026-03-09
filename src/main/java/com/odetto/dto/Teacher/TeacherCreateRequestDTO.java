package com.odetto.dto.Teacher;

import lombok.Data;

import java.util.List;

@Data
public class TeacherCreateRequestDTO {
    private Long cpf;
    private String name;
    private String username;
    private String password;
    private List<String> subjectNames;
}