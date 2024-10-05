package com.security.authservice.service;

import com.security.authservice.dto.CreateUserDto;
import com.security.authservice.dto.UpdateUserDto;
import com.security.authservice.entity.User;
import com.security.authservice.exception.UserExceptions;
import com.security.authservice.repository.UserRepository;

import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Validator validator;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    private void validateUser(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ValidationException("Falha na validação do usuário: " + violations);
        }
    }

    private void validateFields(CreateUserDto createUserDto) {
        String username = createUserDto.username();
        String password = createUserDto.password();

        boolean isUsernameEmpty = username == null || username.trim().isEmpty();
        boolean isPasswordEmpty = password == null || password.trim().isEmpty();

        if (isUsernameEmpty && isPasswordEmpty) {
            throw new UserExceptions.EmptyFieldException();
        }
        if (isUsernameEmpty) {
            throw new UserExceptions.EmptyFieldException("username");
        }
        if (isPasswordEmpty) {
            throw new UserExceptions.EmptyFieldException("password");
        }
        if (password.length() < 6) {
            throw new UserExceptions.PasswordTooShortException();
        }

        if (password.length() > 16) {
            throw new UserExceptions.PasswordTooLongException();
        }
    }

    public Long createUser(CreateUserDto createUserDto) {
        if (userRepository.existsByEmail(createUserDto.email())) {
            throw new UserExceptions.EmailAlreadyExistsException(createUserDto.email());
        }

        validateFields(createUserDto);

        var entity = new User();
        entity.setUsername(createUserDto.username());
        entity.setEmail(createUserDto.email());
        entity.setPassword(createUserDto.password());

        validateUser(entity);

        var userSaved = userRepository.save(entity);
        return userSaved.getId();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserExceptions.UserNotFoundException(id));
    }

    public void deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

    public void updateUserById(Long id, UpdateUserDto updateUserDto) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserExceptions.UserNotFoundException(id));

        if (updateUserDto.username() != null) {
            user.setUsername(updateUserDto.username());
        }
        if (updateUserDto.password() != null) {
            user.setPassword(updateUserDto.password());
        }

        validateUser(user);
        userRepository.save(user);
    }
}
