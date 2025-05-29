package com.projet.equipement.repository;

import com.projet.equipement.entity.UniteVente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UniteVenteRepository extends JpaRepository<UniteVente, Long> {
    Optional<UniteVente> findByCode(String code);
}