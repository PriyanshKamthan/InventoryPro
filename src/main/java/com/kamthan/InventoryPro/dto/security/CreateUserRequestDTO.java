package com.kamthan.InventoryPro.dto.security;

import com.kamthan.InventoryPro.model.enums.Role;

public class CreateUserRequestDTO {

    private String name;
    private String username;
    private String email;
    private String password;
    private Role role;

    public CreateUserRequestDTO() {
    }

    public CreateUserRequestDTO(
            String name,
            String username,
            String email,
            String password,
            Role role) {

        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // getters setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "CreateUserRequestDTO{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}