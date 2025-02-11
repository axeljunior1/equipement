package com.projet.equipement.repository;

import com.projet.equipement.entity.Vente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {

    @Query("select v from Vente v where v.actif = true")
    Page<Vente> findAllActif(Pageable pageable);
}
