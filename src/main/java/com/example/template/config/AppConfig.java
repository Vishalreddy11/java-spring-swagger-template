package com.example.template.config;

import com.example.template.service.UserService;
import com.example.template.service.impl.InMemoryUserService;
import com.example.template.service.impl.JdbcUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.time.Duration;
import java.util.Optional;

@Configuration
public class AppConfig {
    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    private static String env(String key) {
        return Optional.ofNullable(System.getenv(key)).orElse("");
    }

    @Bean
    public UserService userService() {
        String host = env("db_host");
        String user = env("db_user");
        String pass = env("db_pass");
        String name = env("db_name");
        String port = Optional.ofNullable(System.getenv("db_port")).orElse("5432");

        if (host.isBlank() || user.isBlank() || name.isBlank()) {
            log.warn("UserService -> Using IN-MEMORY backend (missing one of db_host/db_user/db_name).");
            return new InMemoryUserService();
        }

        String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s", host, port, name);

        try {
            HikariConfig cfg = new HikariConfig();
            cfg.setJdbcUrl(jdbcUrl);
            cfg.setUsername(user);
            cfg.setPassword(pass);
            cfg.setMaximumPoolSize(5);
            cfg.setConnectionTimeout(Duration.ofSeconds(5).toMillis());
            cfg.setDriverClassName("org.postgresql.Driver");

            HikariDataSource ds = new HikariDataSource(cfg);

            // Prove it works before choosing JDBC:
            try (Connection c = ds.getConnection()) {
                log.info("Connected to Postgres at {}:{} / {}", host, port, name);
            }

            log.info("UserService -> Using JDBC backend");
            return new JdbcUserService(new JdbcTemplate(ds));

        } catch (Exception e) {
            log.warn("UserService -> Failed to connect to Postgres ({}). Falling back to IN-MEMORY. Error: {}",
                    jdbcUrl, e.toString());
            return new InMemoryUserService();
        }
    }
}
