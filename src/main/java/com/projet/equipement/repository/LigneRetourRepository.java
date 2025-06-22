package com.projet.equipement.repository;

import com.projet.equipement.entity.LigneRetour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LigneRetourRepository extends JpaRepository<LigneRetour, Long> {

    Page<LigneRetour> findByRetour_Id(Long id, Pageable pageable);

    Optional<LigneRetour> findByRetour_IdAndLigneVente_Id(Long retourId, Long ligneVenteId);
}
