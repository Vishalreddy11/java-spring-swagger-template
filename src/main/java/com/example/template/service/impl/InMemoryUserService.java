package com.example.template.service.impl;

import com.example.template.model.User;
import com.example.template.service.UserService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class InMemoryUserService implements UserService {
    private final ConcurrentMap<UUID, User> store = new ConcurrentHashMap<>();

    @Override
    public List<User> findAll() {
        System.out.println("from in memory");
        return store.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(UUID id) {
        System.out.println("from in memory");
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public User create(String name, String email) {
        System.out.println("from in memory");
        UUID id = UUID.randomUUID();
        User u = new User(id, name, email);
        store.put(id, u);
        return u;
    }
}
