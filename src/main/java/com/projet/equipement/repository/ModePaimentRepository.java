package com.projet.equipement.repository;

import com.projet.equipement.entity.ModePaiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModePaimentRepository extends JpaRepository<ModePaiement, Long> {
    Optional<ModePaiement> findByCode(String code);
}
