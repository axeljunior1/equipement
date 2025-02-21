package com.projet.equipement.repository;

import com.projet.equipement.entity.Facture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {

    Page<Facture> findByStatut(String statut, Pageable pageable);
//
//    @Query("select a from Achat a where a.actif=true")
//    Page<Achat> findAllPage(Pageable pageable);
}
