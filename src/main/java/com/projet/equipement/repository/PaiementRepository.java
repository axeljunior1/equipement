package com.projet.equipement.repository;

import com.projet.equipement.entity.Paiement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    Page<Paiement> findByModePaiement(String modePaiement, Pageable pageable);
//
//    @Query("select a from Achat a where a.actif=true")
//    Page<Achat> findAllPage(Pageable pageable);
}
