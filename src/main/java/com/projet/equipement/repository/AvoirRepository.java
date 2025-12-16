package com.projet.equipement.repository;

import com.projet.equipement.entity.Avoir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvoirRepository extends JpaRepository<Avoir, Long> {

}
