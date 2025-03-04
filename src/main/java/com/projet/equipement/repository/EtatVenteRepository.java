package com.projet.equipement.repository;

import com.projet.equipement.entity.EtatVente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtatVenteRepository extends JpaRepository<EtatVente, Long> {
    Optional<EtatVente> findByLibelle(String libelle);
}