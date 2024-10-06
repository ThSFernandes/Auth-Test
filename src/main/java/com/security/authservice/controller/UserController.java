package com.security.authservice.controller;


import com.security.authservice.dto.CreateUserDTO;
import com.security.authservice.dto.UpdateUserDTO;
import com.security.authservice.entity.User;
import com.security.authservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        var user = userService.findUserById(userId);
        return ResponseEntity.ok(user);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }


    @PutMapping({"/{userId}"})
    public ResponseEntity<Void> updateByUserId(@PathVariable("userId") Long userId,
                                               @RequestBody UpdateUserDTO updateUserDto) {
        userService.updateUserById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }

}
