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

    @PostMapping
    public ResponseEntity<User> createuser(@RequestBody CreateUserDTO createUserDto) {
        var userID = userService.createUser(createUserDto);
        return ResponseEntity.created(URI.create("/users/" + userID.toString())).build();

    }

    @GetMapping("{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        var user = userService.findUserById(userId);
        return ResponseEntity.ok(user);
    }


    @DeleteMapping({"/{userId}"})
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping({"/{userId}"})
    public ResponseEntity<Void> updateByUserId(@PathVariable("userId") Long userId,
                                               @RequestBody UpdateUserDTO updateUserDto) {
        userService.updateUserById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("Sucesso");
    }
}
