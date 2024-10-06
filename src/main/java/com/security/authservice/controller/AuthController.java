package com.security.authservice.controller;

import com.security.authservice.dto.LoginRequestDTO;
import com.security.authservice.dto.RegisterRequestDTO;
import com.security.authservice.dto.ResponseDTO;
import com.security.authservice.entity.User;
import com.security.authservice.exception.UserExceptions;
import com.security.authservice.infra.security.TokenService;
import com.security.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body) {
        // Verifica se o usuário existe pelo e-mail
        User user = this.repository.findByEmail(body.email())
                .orElseThrow(() -> new UserExceptions.EmailNotFoundException(body.email()));

        // Verifica se a senha está correta
        if (!passwordEncoder.matches(body.password(), user.getPassword())) {
            throw new UserExceptions.InvalidPasswordException();
        }

        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO body) {
        Optional<User> user = this.repository.findByEmail(body.email());

        if (user.isPresent()) {
            throw new UserExceptions.EmailAlreadyExistsException(body.email());
        }

        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setEmail(body.email());
        newUser.setName(body.name());
        this.repository.save(newUser);

        String token = this.tokenService.generateToken(newUser);
        return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
    }

}
