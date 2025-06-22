package com.projet.equipement.repository;

import com.projet.equipement.entity.EtatRetour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtatRetourRepository extends JpaRepository<EtatRetour, Long> {

    Optional<EtatRetour> findByLibelle(String s);
}
