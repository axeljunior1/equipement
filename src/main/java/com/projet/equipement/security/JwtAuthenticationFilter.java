package com.projet.equipement.security;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final EmployeeDetailsService employeeDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, EmployeeDetailsService employeeDetailsService) {
        this.jwtUtil = jwtUtil;
        this.employeeDetailsService = employeeDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Récupération du token depuis l'en-tête Authorization
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        try{
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7); // Supprime "Bearer "
                username = jwtUtil.getUsernameFromToken(token);
            }

            // Si le token est valide et que l'utilisateur n'est pas encore authentifié
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = employeeDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(token)) {
                    // Crée une authentification et configure le contexte de sécurité
                    var authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expiré. Veuillez vous reconnecter.");
            return;  // On arrête le filtre ici
        }

        // Continue le traitement de la requête
        filterChain.doFilter(request, response);
    }
}
