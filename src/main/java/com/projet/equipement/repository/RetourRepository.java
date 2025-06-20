package com.projet.equipement.repository;

import com.projet.equipement.entity.Retour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetourRepository extends JpaRepository<Retour, Long> {

}
