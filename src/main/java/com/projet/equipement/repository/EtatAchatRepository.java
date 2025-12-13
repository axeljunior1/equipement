package com.projet.equipement.repository;

import com.projet.equipement.entity.EtatAchat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtatAchatRepository extends JpaRepository<EtatAchat, Long> {
    Optional<EtatAchat> findByLibelle(String libelle);
}