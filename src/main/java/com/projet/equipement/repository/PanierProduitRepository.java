package com.projet.equipement.repository;

import com.projet.equipement.entity.PanierProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PanierProduitRepository extends JpaRepository<PanierProduit, Long> {

    @Query("select p from PanierProduit p where p.panier.id = :id ")
    List<PanierProduit> findAllByPanierId(@Param("id")Long id);

    @Query("select p from PanierProduit p where p.panier.id = :idPanier and p.produit.id = :idProduit ")
    Optional<PanierProduit> findByIdPanierAndIdProduit(@Param("idPanier") Long idPanier, @Param("idProduit")Long idProduit);

}
