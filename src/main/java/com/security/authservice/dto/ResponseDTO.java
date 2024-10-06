package com.security.authservice.dto;

import jakarta.validation.constraints.NotBlank;

public record ResponseDTO(
        @NotBlank(message = "Nome não pode ser vazio") String name,
        @NotBlank(message = "Token não pode ser vazio") String token
) {
}
