package com.projet.equipement.repository;

import com.projet.equipement.entity.FormatVente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormatVenteRepository extends JpaRepository<FormatVente, Long> {
    @Override
    Page<FormatVente> findAll(Pageable pageable);
}