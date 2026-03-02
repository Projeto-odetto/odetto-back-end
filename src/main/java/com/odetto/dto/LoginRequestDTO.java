package com.odetto.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private Long cpf;
    private String password;
}
