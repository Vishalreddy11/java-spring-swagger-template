package com.example.java_template.controller;

import com.example.java_template.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users", description = "User management APIs")
@RestController
@RequestMapping("/users")
public class UserController {

    // In-memory user store (thread-safe)
    private final Map<String, User> userStore = new ConcurrentHashMap<>();

    @Operation(summary = "Get all users", description = "Returns a list of all users in memory")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userStore.values().stream().collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Create a new user", description = "Creates a new user and stores it in memory")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (user.getId() == null || user.getId().isEmpty()) {
            user.setId(UUID.randomUUID().toString());
        }
        userStore.put(user.getId(), user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Operation(summary = "Get user by ID", description = "Fetches a user from memory by their unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userStore.get(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }
}
