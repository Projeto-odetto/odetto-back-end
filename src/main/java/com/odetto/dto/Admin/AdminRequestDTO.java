package com.odetto.dto.Admin;

import lombok.Data;

@Data
public class AdminRequestDTO {
    private Long id;
    private String email;
    private String password;
}
