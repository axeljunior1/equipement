package com.projet.equipement.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.equipement.exceptions.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // Création d'une réponse API structurée
        ApiError apiError = new ApiError(
                403,
                "Forbidden",
                "Vous n'avez pas les permissions nécessaires pour accéder à cette ressource.",
                request.getRequestURI()
        );

        // Configuration de la réponse HTTP
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(apiError));

        System.out.println("Access denied: " + request.getRequestURI()); // Log console
    }
}
