package com.security.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "Email obrigatório")
        @Email(message = "Email deve ter um formato válido, exemplo: usuario@dominio.com")
        String email,

        @NotBlank(message = "Senha obrigatória")
        String password
) {
}
