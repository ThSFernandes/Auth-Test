package com.security.authservice.service;

import com.security.authservice.dto.CreateUserDto;
import com.security.authservice.dto.UpdateUserDto;
import com.security.authservice.entity.User;
import com.security.authservice.repository.UserRepository;

import jakarta.validation.*;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private final Validator validator;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    private void validateUser(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ValidationException("Falha na validação do usuário: " + violations);
        }
    }

    public Long createUser(CreateUserDto createUserDto) {
        var entity = new User();
        entity.setUsername(createUserDto.username());
        entity.setEmail(createUserDto.email());
        entity.setPassword(createUserDto.password());

        validateUser(entity);

        var userSaved = userRepository.save(entity);
        return userSaved.getId();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);

    }

    public void deleteUserById(Long id) {
        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }
    }

    public void updateUserById(Long id, UpdateUserDto updateUserDto) {
        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

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
}