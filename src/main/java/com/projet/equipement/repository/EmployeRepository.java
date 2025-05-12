package com.projet.equipement.repository;

import com.projet.equipement.entity.Employe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {
    Optional <Employe> findByNom(String nom);
    Optional <Employe> findByPrenom(String prenom);

    @Query("select e from Employe e where e.actif = true and e.tenantId = :tenantId")
    Page<Employe> findAll(Pageable pageable, String tenantId);

}
