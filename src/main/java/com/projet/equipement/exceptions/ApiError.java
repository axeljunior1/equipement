package com.projet.equipement.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ApiError {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ApiError(int status, String error, String message, String path) {
        this.timestamp = String.valueOf(LocalDateTime.now());
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // Getters et Setters
}



