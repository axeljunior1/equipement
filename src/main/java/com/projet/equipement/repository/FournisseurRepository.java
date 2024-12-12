package com.projet.equipement.repository;

import com.projet.equipement.entity.Entree;
import com.projet.equipement.entity.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer> {

}
