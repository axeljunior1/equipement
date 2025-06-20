package com.projet.equipement.repository;

import com.projet.equipement.entity.LigneRetour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigneRetourRepository extends JpaRepository<LigneRetour, Long> {

}
