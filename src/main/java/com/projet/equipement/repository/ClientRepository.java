package com.projet.equipement.repository;

import com.projet.equipement.entity.Client;
import com.projet.equipement.entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional <Client> findByNom(String nom);
    Optional <Client> findByPrenom(String prenom);
    Optional <Client> findByEmail(String email);
    Optional <Client> findByTelephone(String tel);

}
