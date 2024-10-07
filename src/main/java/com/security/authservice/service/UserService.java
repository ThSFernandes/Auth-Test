package com.security.authservice.service;

import com.security.authservice.dto.UpdateUserDTO;
import com.security.authservice.dto.RegisterDTO;
import com.security.authservice.entity.User;
import com.security.authservice.exception.UserExceptions;
import com.security.authservice.repository.UserRepository;

import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;  // Injetando PasswordEncoder

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.passwordEncoder = passwordEncoder;  // Inicializando PasswordEncoder
    }

    private void validateUser(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ValidationException("Falha na validação do usuário: " + violations);
        }
    }

    private void validateFields(RegisterDTO createUserDto) {
        if (createUserDto != null) {
            String username = Optional.ofNullable(createUserDto.name()).orElse("").trim();
            String password = Optional.ofNullable(createUserDto.password()).orElse("").trim();

            if (username.isEmpty() && password.isEmpty()) {
                throw new UserExceptions.EmptyFieldException();
            }
            if (username.isEmpty()) {
                throw new UserExceptions.EmptyFieldException("username");
            }
            if (password.isEmpty()) {
                throw new UserExceptions.EmptyFieldException("password");
            }

        }
    }


    private void validatePassword(String password) {
        if (password.length() < 6) {
            throw new UserExceptions.PasswordTooShortException();
        }
        if (password.length() > 16) {
            throw new UserExceptions.PasswordTooLongException();
        }
    }


    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserExceptions.UserNotFoundException(id));
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserExceptions.UserNotFoundException(id);
        }

        userRepository.deleteById(id);
    }

    public void updateUserById(Long id, UpdateUserDTO updateUserDto) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserExceptions.UserNotFoundException(id));


        Optional.ofNullable(updateUserDto.name())
                .ifPresent(user::setName);


        Optional.ofNullable(updateUserDto.password())
                .ifPresent(password -> {
                    validatePassword(password);  // Valida a nova senha
                    user.setPassword(passwordEncoder.encode(password));  // Criptografa e atualiza a senha
                });


        validateFields(new RegisterDTO(user.getName(), user.getEmail(), updateUserDto.password()));
        validateUser(user);
        userRepository.save(user);
    }
}
