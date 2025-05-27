package com.projet.equipement.repository;

import com.projet.equipement.entity.Devise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviseRepository extends JpaRepository<Devise, Long> {

    @Override
    Page<Devise> findAll(Pageable pageable);

    Optional<Devise> findByCode(String code);
    Optional<Devise> findByNom(String nom);
}
