package com.example.java_template.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class User {
    @Schema(description = "Unique identifier for the user", example = "9f21749e-40ed-4ecb-a5a8-27b862c10445")
    private String id;

    @Schema(description = "Full name of the user", example = "Vishal Yellati")
    private String name;

    @Schema(description = "Email address of the user", example = "vishal.yelati@bofa.com")
    private String email;

    @Schema(description = "Role of the user", example = "Admin")
    private String role;

    // Getters & setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
