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

    public static class EmptyFieldException extends RuntimeException {
        public EmptyFieldException(String field) {
            super("O campo " + field + " não pode estar vazio.");
        }

        public EmptyFieldException() {
            super("Os campos username e password não podem estar vazios.");
        }
    }

    public static class PasswordTooShortException extends RuntimeException {
        public PasswordTooShortException() {
            super("A senha deve ter pelo menos 6 caracteres.");
        }
    }


    public static class PasswordTooLongException extends RuntimeException {
        public PasswordTooLongException() {
            super("A senha comporta apenas 16 caracteres.");
        }
    }
}
