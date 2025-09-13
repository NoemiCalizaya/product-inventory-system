package com.example.oauth_server.models;

import lombok.Data;

import java.util.List;

@Data
public class User {

    private Long id;

    private String username;

    private String password;

    private Boolean enabled;

    private boolean admin;

    private List<Role> roles;

    private String email;

    public Boolean isEnabled() {
        return enabled;
    }
}
