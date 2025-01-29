package com.projet.equipement.repository;

import com.projet.equipement.entity.TypeMouvementStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeMouvementStockRepository extends JpaRepository<TypeMouvementStock,  Long> {
    TypeMouvementStock findByCode(String code);
}

