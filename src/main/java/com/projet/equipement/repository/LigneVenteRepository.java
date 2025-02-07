package com.projet.equipement.repository;

import com.projet.equipement.entity.LigneVente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneVenteRepository extends JpaRepository<LigneVente, Long> {
    @Query("select lv from LigneVente lv where lv.vente.id = :id ")
    Page<LigneVente> findByVenteId(@Param("id") Long id, Pageable pageable);

    @Query("select lv from LigneVente lv where lv.vente.id = :id ")
    List<LigneVente> findByVenteId(@Param("id") Long id);
}
