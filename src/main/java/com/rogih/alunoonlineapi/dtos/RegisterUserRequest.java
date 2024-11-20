package com.rogih.alunoonlineapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {

    private String email;
    private String senha;
    private String nomeCompleto;
    private String cpf;
}
