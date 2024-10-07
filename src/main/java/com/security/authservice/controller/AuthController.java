package com.security.authservice.controller;

import com.security.authservice.dto.LoginDTO;
import com.security.authservice.dto.RegisterDTO;
import com.security.authservice.dto.ResponseDTO;
import com.security.authservice.entity.User;
import com.security.authservice.exception.UserExceptions;
import com.security.authservice.infra.security.SecurityConfig;
import com.security.authservice.infra.security.TokenService;
import com.security.authservice.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "usuario", description = "Autenticação")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    @Operation(summary = "Realizar Login", description = "Método para login usuário")
    @ApiResponse(responseCode = "200", description = "usuário logado com sucesso")
    @ApiResponse(responseCode = "400", description = "Email não cadastrado" )
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<?> login(@RequestBody LoginDTO body) {
        // Validações de entrada
        if (body.email() == null || body.email().isEmpty()) {
            throw new UserExceptions.EmptyFieldException("email");
        }

        if (body.password() == null || body.password().isEmpty()) {
            throw new UserExceptions.EmptyFieldException("password");
        }

        if (body.password().length() < 6) {
            throw new UserExceptions.PasswordTooShortException();
        }

        if (body.password().length() > 16) {
            throw new UserExceptions.PasswordTooLongException();
        }

        // Tenta encontrar o usuário pelo email
        User user = this.repository.findByEmail(body.email()).orElseThrow(() ->
                new UserExceptions.EmailNotFoundException(body.email())
        );

        // Verifica se a senha está correta
        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Registra usuário", description = "Método para salvar usuário")
    @ApiResponse(responseCode = "201", description = "usuário registrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Email já cadastrado" )
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO body) {
        Optional<User> user = this.repository.findByEmail(body.email());

        if (user.isEmpty()) {

            if (body.email() == null || body.email().isEmpty()) {
                throw new UserExceptions.EmptyFieldException("email");
            }

            if (body.password() == null || body.password().isEmpty()) {
                throw new UserExceptions.EmptyFieldException("password");
            }

            if (body.name() == null || body.name().isEmpty()) {
                throw new UserExceptions.EmptyFieldException("name");
            }

            if (body.password().length() < 6) {
                throw new UserExceptions.PasswordTooShortException();
            }

            if (body.password().length() > 16) {
                throw new UserExceptions.PasswordTooLongException();
            }

            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }


}
