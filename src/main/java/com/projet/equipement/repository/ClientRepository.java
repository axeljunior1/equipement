package com.projet.equipement.repository;

import com.projet.equipement.entity.Client;
import com.projet.equipement.entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional <Employe> findByNom(String nom);
    Optional <Employe> findByPrenom(String prenom);
    Optional <Employe> findByEmail(String prenom);
}
