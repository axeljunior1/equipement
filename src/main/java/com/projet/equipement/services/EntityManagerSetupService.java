package com.projet.equipement.services;

import com.projet.equipement.entity.TenantContext;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

@Service
public class EntityManagerSetupService {

    private final EntityManager entityManager;

    public EntityManagerSetupService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setTenantFilter() {
        entityManager.unwrap(Session.class)
                .enableFilter("tenantFilter")
                .setParameter("tenantId", TenantContext.getTenantId());
    }
}
