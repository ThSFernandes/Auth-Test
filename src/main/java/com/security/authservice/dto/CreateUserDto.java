package com.security.authservice.dto;

public record CreateUserDto(String username, String password, String email) {
}
