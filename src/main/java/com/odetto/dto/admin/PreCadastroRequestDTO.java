package com.odetto.dto.admin;

import lombok.Data;

@Data
public class PreCadastroRequestDTO {
    private Long cpf;
    private String email;
}