package com.projet.equipement.repository;

import com.projet.equipement.entity.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProduitRepository extends JpaRepository<Produit,  Long>, JpaSpecificationExecutor<Produit> {
    @Query("SELECT p FROM Produit p WHERE p.actif = true and " +
            "(LOWER(p.nom) LIKE LOWER(CONCAT('%', :motCle, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :motCle, '%')))")
    List<Produit> rechercherProduits(@Param("motCle") String motCle);

    Page<Produit> findByActif(boolean actif, Pageable pageable);

    Optional<Produit> findByEan13(String ean13);
}
