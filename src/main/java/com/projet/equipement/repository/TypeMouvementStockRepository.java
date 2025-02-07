package com.projet.equipement.repository;

import com.projet.equipement.entity.TypeMouvementStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface TypeMouvementStockRepository extends JpaRepository<TypeMouvementStock,  Long> {
    Optional<TypeMouvementStock> findByCode(String code);
}

