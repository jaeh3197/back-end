package com.example.backend.user;

public enum Role {
    USER("user"), ADMIN("admin");

    private String role;

    private Role(String role) {
        this.role = role;
    }
}
