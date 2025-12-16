package com.projet.equipement.repository;

import com.projet.equipement.entity.EtatAvoir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtatAvoirRepository extends JpaRepository<EtatAvoir, Long> {

    Optional<EtatAvoir> findByLibelle(String libelle);
}
