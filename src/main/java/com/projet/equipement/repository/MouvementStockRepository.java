package com.projet.equipement.repository;

import com.projet.equipement.entity.MouvementStock;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.TypeMouvementStock;
import com.projet.equipement.entity.enumeration.TypeMouvement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MouvementStockRepository extends JpaRepository<MouvementStock,  Long> {

    List<MouvementStock> findAll(Specification<MouvementStock> spec);

    @Query("select mv from MouvementStock mv where mv.produit.id = :id")
    Page<MouvementStock> findAllMouvementStockByProductId(@Param("id") Long id, Pageable pageable);

    List<MouvementStock> findByProduitAndTypeMouvement(Produit produit, TypeMouvementStock typeMouvementStock);
}

