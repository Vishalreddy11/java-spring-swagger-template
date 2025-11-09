package com.example.template.service;

import com.example.template.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(UUID id);
    User create(String name, String email);
}
