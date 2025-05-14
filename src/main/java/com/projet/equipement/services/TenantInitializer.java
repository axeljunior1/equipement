package com.projet.equipement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class TenantInitializer {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TenantInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void initializeTenant(String tenantId) throws IOException {
        // Charger la ressource depuis le classpath
        Resource resource = new ClassPathResource("sql/init_tenant_template.sql");

        // Lire le fichier via InputStream (fonctionne même dans un JAR)
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }

            // Remplacer le placeholder {{tenant_id}} par la vraie valeur
            String finalSql = sql.toString().replace("{{tenant_id}}", tenantId);

            // Séparer les statements si nécessaire
            String[] statements = finalSql.split(";");
            for (String statement : statements) {
                String trimmed = statement.trim();
                if (!trimmed.isEmpty()) {
                    jdbcTemplate.execute(trimmed);
                }
            }
        }
    }
}
