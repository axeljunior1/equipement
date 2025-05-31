package com.projet.equipement.repository;

import com.projet.equipement.entity.EtatPanier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtatPanierRepository extends JpaRepository<EtatPanier, Long> {
    Optional<EtatPanier> findByLibelle(String libelle);
}