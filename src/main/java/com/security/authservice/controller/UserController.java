package com.security.authservice.controller;


import com.security.authservice.dto.CreateUserDto;
import com.security.authservice.dto.UpdateUserDto;
import com.security.authservice.entity.User;
import com.security.authservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createuser(@RequestBody CreateUserDto createUserDto) {
        var userID = userService.createUser(createUserDto);
        return ResponseEntity.created(URI.create("/users/" + userID.toString())).build();

    }

    @GetMapping("{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        var user = userService.findUserById(userId);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping({"/{userId}"})
    public ResponseEntity<Void> deleteUserById(@PathVariable ("userId") Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping({"/{userId}"})
    public ResponseEntity<Void> updateByUserId(@PathVariable("userId") Long userId,
                                               @RequestBody UpdateUserDto updateUserDto) {
        userService.updateUserById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }
}
