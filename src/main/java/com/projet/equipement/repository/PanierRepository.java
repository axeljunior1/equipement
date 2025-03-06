package com.projet.equipement.repository;

import com.projet.equipement.entity.Achat;
import com.projet.equipement.entity.Panier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Long> {

    @Query("select p from Panier p where p.employe.id = :id and p.etat.id =1")
    List<Panier> findByEmployeId(Long id);
}
