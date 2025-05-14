package com.projet.equipement.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;

@Service
public class TenantInitializer {

    private final JdbcTemplate jdbcTemplate;

    public TenantInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void initializeTenant(String tenantId) throws IOException {
        // Lire le fichier SQL template
        ClassPathResource resource = new ClassPathResource("sql/init_tenant_template.sql");
        String sql = Files.readString(resource.getFile().toPath());

        // Remplacer le placeholder {{tenant_id}} par la vraie valeur
        sql = sql.replace("{{tenant_id}}", tenantId);

        // Séparer les statements si nécessaire
        String[] statements = sql.split(";");
        for (String statement : statements) {
            String trimmed = statement.trim();
            if (!trimmed.isEmpty()) {
                jdbcTemplate.execute(trimmed);
            }
        }
    }
}
