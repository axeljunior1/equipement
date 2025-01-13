package com.projet.equipement.repository;

import com.projet.equipement.entity.Achat;
import com.projet.equipement.entity.LigneAchat;
import com.projet.equipement.entity.LigneVente;
import com.projet.equipement.entity.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneAchatRepository extends JpaRepository<LigneAchat, Long> {
    List<LigneAchat> findByAchat(Achat achat);
}
