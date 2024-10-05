package com.security.authservice.service;

import com.security.authservice.controller.CreateUserDto;
import com.security.authservice.entity.User;
import com.security.authservice.repository.UserRepository;

import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.Instant;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long createUser(CreateUserDto createUserDto) {

        var entity = new User();
        entity.setUsername(createUserDto.username());
        entity.setEmail(createUserDto.email());
        entity.setPassword(createUserDto.password());

        var userSaved = userRepository.save(entity);

        return userSaved.getId();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);

    }
}