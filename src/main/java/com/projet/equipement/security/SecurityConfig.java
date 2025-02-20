package com.projet.equipement.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults()).csrf(c -> c.disable())// Si tu veux désactiver CSRF (utilisé généralement pour les API)
                .authorizeHttpRequests(r -> r
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()  // Permet l'accès à /login sans authentification
                        .requestMatchers(HttpMethod.POST, "login").permitAll()  // Permet l'accès à /login sans authentification
                        .requestMatchers( "test").permitAll()  // Permet l'accès à /login sans authentification
                        .requestMatchers(HttpMethod.GET, "/").permitAll()  // Permet l'accès à /login sans authentification
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("roles/**").hasRole("ADMIN")  // Permet l'accès à /login sans authentification
                        .requestMatchers("employes/**").hasRole("ADMIN")  // Permet l'accès à /login sans authentification
                        .anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
                )
                .exceptionHandling(ex -> ex.accessDeniedHandler(new CustomAccessDeniedHandler())
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Enforce HTTPS (optional but recommended)

        return http.build();  // Renvoie la configuration de sécurité construite
    }



}
