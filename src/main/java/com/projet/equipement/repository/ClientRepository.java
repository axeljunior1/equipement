package com.projet.equipement.repository;

import com.projet.equipement.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional <Client> findByNom(String nom);
    Optional <Client> findByPrenom(String prenom);
    Optional <Client> findByEmail(String email);
    Optional <Client> findByTelephone(String tel);

    @Query("SELECT p FROM Client p WHERE p.actif = true and " +
            "(LOWER(p.nom) LIKE LOWER(CONCAT('%', :motCle, '%')) OR " +
            "LOWER(p.telephone) LIKE LOWER(CONCAT('%', :motCle, '%')) OR " +
            "LOWER(p.prenom) LIKE LOWER(CONCAT('%', :motCle, '%')))"
    )
    List<Client> rechercherClients(@Param("motCle") String motCle);

    @Query("SELECT c FROM Client c WHERE c.id = :id")
    Optional<Client> findWithTenant(@Param("id") Long id);
}
