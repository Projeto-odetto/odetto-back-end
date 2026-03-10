package com.odetto.dto.admin;

import lombok.Data;

@Data
public class AdminLoginRequestDTO {
    private String email;
    private String password;
}
