package com.odetto.dto.Student;

import lombok.Data;

@Data
public class StudentEditRequestDTO {
    private Long enrollment;
    private String name;
    private String email;
    private String password;
}