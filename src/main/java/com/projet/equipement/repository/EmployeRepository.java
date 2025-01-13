package com.projet.equipement.repository;

import com.projet.equipement.entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {
    Optional <Employe> findByNom(String nom);
    Optional <Employe> findByPrenom(String prenom);
}
