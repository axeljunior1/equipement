package com.projet.equipement.repository;

import com.projet.equipement.entity.TarifAchat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifAchatRepository extends JpaRepository<TarifAchat, Long> {


}
