package com.projet.equipement.repository;

import com.projet.equipement.entity.FormatVente;
import com.projet.equipement.entity.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormatVenteRepository extends JpaRepository<FormatVente, Long> {
    @Override
    Page<FormatVente> findAll(Pageable pageable);

    @Query("select f from FormatVente f where f.produit.id=:produitId")
    Page<FormatVente> findByProduitId(Long produitId, Pageable pageable);
}