package com.projet.equipement.repository;

import com.projet.equipement.entity.EtatPaiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtatPaiementRepository extends JpaRepository<EtatPaiement, Long> {
    Optional<EtatPaiement> findByLibelle(String libelle);
}