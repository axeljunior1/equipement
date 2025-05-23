package com.projet.equipement.config;

import com.projet.equipement.security.TenantFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Autoriser tous les chemins
                        .allowedOrigins("*") // Origines autorisées
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // Méthodes HTTP autorisées
                        .allowedHeaders("*"); // Autoriser tous les en-têtes
            }
        };
    }

    @Bean
    public FilterRegistrationBean<TenantFilter> tenantFilterBean() {
        FilterRegistrationBean<TenantFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TenantFilter());
        registrationBean.addUrlPatterns("/clients/*"); // Appliquer à tous les endpoints /api/*
        return registrationBean;
    }


}
