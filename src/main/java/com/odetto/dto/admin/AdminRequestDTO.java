package com.odetto.dto.admin;

import lombok.Data;

@Data
public class AdminRequestDTO {
    private Long id;
    private String email;
    private String password;
}
