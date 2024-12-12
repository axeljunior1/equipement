package com.projet.equipement.repository;

import com.projet.equipement.entity.Entree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntreeRepository extends JpaRepository<Entree, Integer> {

}
