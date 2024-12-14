package com.projet.equipement.repository;

import com.projet.equipement.entity.Utilisateur;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByNom(String nom);
    Utilisateur findByEmail(String Email) ;
}
