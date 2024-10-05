package com.security.authservice.exception;

public class UserExceptions {

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(Long userId) {
            super("Usuário com ID " + userId + " não foi encontrado.");
        }
    }

    public static class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException(String email) {
            super("O e-mail " + email + " já está em uso.");
        }
    }
}
