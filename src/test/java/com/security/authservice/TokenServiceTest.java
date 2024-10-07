package com.security.authservice;


import com.security.authservice.entity.User;
import com.security.authservice.infra.security.TokenService;
import com.security.authservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setEmail("th@gmail.com");
        String rawPassword = "Th1234@";
        user.setPassword(new BCryptPasswordEncoder().encode(rawPassword));
    }

    @Test
    public void testGenerateToken() {
        String token = tokenService.generateToken(user);
        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"));
    }

    @Test
    public void testValidateToken_ValidToken() {
        String token = tokenService.generateToken(user);
        String validatedEmail = tokenService.validateToken(token);
        assertEquals(user.getEmail(), validatedEmail);
    }

    @Test
    public void testValidateToken_InvalidToken() {
        String invalidToken = "Token invalido";
        String validatedEmail = tokenService.validateToken(invalidToken);
        assertNull(validatedEmail);
    }

    @Test
    public void testPasswordEncryption() {
        String rawPassword = "Th1234@";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        assertNotEquals(rawPassword, encryptedPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encryptedPassword));
    }

    @Test
    public void testUserRepositoryInteraction() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(java.util.Optional.of(user));

        User foundUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }
}
