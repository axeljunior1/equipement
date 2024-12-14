package com.projet.equipement.repository;

import com.projet.equipement.entity.Client;
import com.projet.equipement.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,  Long> {

}
