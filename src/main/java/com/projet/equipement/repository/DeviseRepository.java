package com.projet.equipement.repository;

import com.projet.equipement.entity.Devise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviseRepository extends JpaRepository<Devise, Long> {

    @Override
    Page<Devise> findAll(Pageable pageable);

    Page<Devise> findByTenantIdAndNom(String tenantId, String nom, Pageable pageable);

    Page<Devise> findByTenantIdAndCode(String tenantId, String code, Pageable attr1);

    Optional<Devise> findByCode(String code);
    Optional<Devise> findByNom(String nom);
}
