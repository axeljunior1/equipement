package com.projet.equipement.repository;

import com.projet.equipement.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, String> {

    // Rechercher un tenant par son ID
    Optional<Tenant> findById(String tenantId);

    // Vérifier si un tenant est actif
    boolean existsByIdAndIsActiveTrue(String tenantId);
}