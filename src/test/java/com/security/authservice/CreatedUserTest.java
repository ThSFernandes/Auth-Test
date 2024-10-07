package com.security.authservice;

import com.security.authservice.dto.RegisterDTO;
import com.security.authservice.entity.User;
import com.security.authservice.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CreatedUserTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should create and find user successfully")
    void shouldCreateAndFindUserSuccessfully() {
        RegisterDTO data = new RegisterDTO("Thiago", "Thsalles@teste.com", "Th12345@");
        User createdUser = createFakerUser(data);

        Optional<User> result = this.userRepository.findById(createdUser.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getEmail()).isEqualTo(data.email());
    }


    private User createFakerUser(RegisterDTO data) {
        User newUser = new User();
        newUser.setName(data.name());
        newUser.setEmail(data.email());
        newUser.setPassword(data.password());

        this.entityManager.persist(newUser);
        return newUser;
    }
}
