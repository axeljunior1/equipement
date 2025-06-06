package com.projet.equipement.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                        .requestMatchers("/static/**", "/index.html", "/favicon.ico", "/build/**", "/css/**", "/js/**", "/assets/**")  // Permet l'accès aux ressources statiques
                        .permitAll()
                        .requestMatchers(
                                "/api/auth/**",                    // pour login/register
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()                        .requestMatchers(HttpMethod.POST, "login").permitAll()  // Permet l'accès à /login sans authentification
                        .requestMatchers( "test").permitAll()  // Permet l'accès à /login sans authentification
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("tenant/**").hasRole("ADMIN")  // Permet l'accès à /login sans authentification
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
