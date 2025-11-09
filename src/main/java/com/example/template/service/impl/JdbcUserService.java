package com.example.template.service.impl;

import com.example.template.model.User;
import com.example.template.service.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcUserService implements UserService {
    private final JdbcTemplate jdbc;

    public JdbcUserService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        autoMigrate();
    }

    private void autoMigrate() {
        // Simple automigration: create 'users' table if not exists
        jdbc.execute("""
            CREATE TABLE IF NOT EXISTS users (
                id UUID PRIMARY KEY,
                name TEXT NOT NULL,
                email TEXT NOT NULL
            )
        """);
    }

    private static final RowMapper<User> USER_MAPPER = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                UUID.fromString(rs.getString("id")),
                rs.getString("name"),
                rs.getString("email")
            );
        }
    };

    @Override
    public List<User> findAll() {
        System.out.println("from db");
        return jdbc.query("SELECT id, name, email FROM users ORDER BY name", USER_MAPPER);
    }

    @Override
    public Optional<User> findById(UUID id) {
        System.out.println("from db");
        List<User> list = jdbc.query("SELECT id, name, email FROM users WHERE id = ?", USER_MAPPER, id);
        return list.stream().findFirst();
    }

    @Override
    public User create(String name, String email) {
        System.out.println("from db");
        UUID id = UUID.randomUUID();
        jdbc.update("INSERT INTO users (id, name, email) VALUES (?,?,?)", id, name, email);
        return new User(id, name, email);
    }
}
