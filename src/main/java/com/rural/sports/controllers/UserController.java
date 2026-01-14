package com.rural.sports.controllers;

import com.rural.sports.models.User;
import com.rural.sports.models.UserStatus;
import com.rural.sports.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/me")
    public User getMe(@AuthenticationPrincipal User user) {
        return user;
    }

    @PutMapping("/{userId}/status")
    public ResponseEntity<?> updateUserStatus(@PathVariable Long userId, @RequestBody Map<String, String> statusUpdate) {
        try {
            UserStatus status = UserStatus.valueOf(statusUpdate.get("status").toUpperCase());
            userService.updateUserStatus(userId, status);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status value");
        }
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<?> resetPassword(@PathVariable Long userId, @RequestBody Map<String, String> passwordUpdate) {
        String newPassword = passwordUpdate.get("password");
        if (newPassword == null || newPassword.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Password cannot be empty");
        }
        userService.resetPassword(userId, newPassword);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
