package com.projet.equipement.repository;

import com.projet.equipement.entity.EtatFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtatFactureRepository extends JpaRepository<EtatFacture, Long> {
    Optional<EtatFacture> findByLibelle(String libelle);
}