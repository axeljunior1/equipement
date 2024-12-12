package com.projet.equipement.repository;

import com.projet.equipement.entity.Sortie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SortieRepository extends JpaRepository<Sortie, Integer> {

}
