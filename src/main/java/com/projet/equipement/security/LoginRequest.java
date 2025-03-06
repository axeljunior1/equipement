package com.projet.equipement.security;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    String username;
    String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters et Setters
}