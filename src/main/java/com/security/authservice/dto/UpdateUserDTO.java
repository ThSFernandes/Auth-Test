package com.security.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserDTO(
        @NotBlank(message = "Nome de usuário obrigatório") String name,
        @Size(min = 6, message = "Senha teve ter pelo menos 6 caracteres") String password) {
}
