package com.example.template.controller;

import com.example.template.dto.CreateUserRequest;
import com.example.template.model.User;
import com.example.template.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Basic user CRUD endpoints (demo)")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "List users")
    public ResponseEntity<List<User>> all() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    public ResponseEntity<User> byId(@PathVariable("id") UUID id) {
        Optional<User> u = userService.findById(id);
        return u.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create user")
    public ResponseEntity<User> create(@RequestBody CreateUserRequest req) {
        if (req.getName() == null || req.getName().isBlank() || req.getEmail() == null || req.getEmail().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        User created = userService.create(req.getName(), req.getEmail());
        return ResponseEntity.created(URI.create("/api/v1/users/" + created.getId())).body(created);
    }
}
