package com.projet.equipement.security;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    String username;
    String password;
    String tenantId;

    public LoginRequest(String username, String password, String tenantId) {
        this.username = username;
        this.tenantId = tenantId;
        this.password = password;
    }

    // Getters et Setters
}