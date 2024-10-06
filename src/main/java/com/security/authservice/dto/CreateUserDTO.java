package com.security.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
        @NotBlank(message = "Nome de usuário obrigatório") String username,
        @Size(min = 6, message = "Senha teve ter pelo menos 6 caracteres") String password,
        @NotBlank(message = "Email obrigatório")
        @Email(message = "Email deve ter um formato válido, exemplo: usuario@dominio.com") String email
) {
}