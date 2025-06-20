package com.projet.equipement.repository;

import com.projet.equipement.entity.TypeRetour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRetourRepository extends JpaRepository<TypeRetour, Long> {

}
