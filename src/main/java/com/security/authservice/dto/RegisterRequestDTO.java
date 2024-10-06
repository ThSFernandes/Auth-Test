package com.security.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank(message = "Nome é obrigatório") String name,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email deve ter um formato válido, exemplo: usuario@dominio.com") String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres") String password
) {
}
