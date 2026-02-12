package com.odetto.dto.Student;

import lombok.Data;

@Data
public class StudentRequestDTO {
    private Long enrollment;
    private String name;
    private String email;
    private String password;
}
