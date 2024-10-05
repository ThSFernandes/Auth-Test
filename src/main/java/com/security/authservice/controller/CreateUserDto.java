package com.security.authservice.controller;

public record CreateUserDto(String username, String password, String email) {
}
