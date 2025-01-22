package com.projet.equipement.repository;

import com.projet.equipement.entity.Produit;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProduitRepository extends PagingAndSortingRepository<Produit,  Long>, JpaSpecificationExecutor<Produit> {
    @Query("SELECT p FROM Produit p WHERE " +
            "LOWER(p.nom) LIKE LOWER(CONCAT('%', :motCle, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :motCle, '%')) OR " +
            "LOWER(p.categorie) LIKE LOWER(CONCAT('%', :motCle, '%'))")
    List<Produit> rechercherProduits(@Param("motCle") String motCle);

    Optional<Produit> findById(Long id);

    Produit save(Produit produit);

    void deleteById(Long id);
}
