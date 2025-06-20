package com.projet.equipement.repository;

import com.projet.equipement.entity.EtatRetour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatRetourRepository extends JpaRepository<EtatRetour, Long> {

}
