package com.projet.equipement.repository;

import com.projet.equipement.entity.TarifAchat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarifAchatRepository extends JpaRepository<TarifAchat, Long> {

    @Query("select t from TarifAchat t where t.produit.id=:id")
    Optional<TarifAchat> findByProduitId(@Param("id") Long id);

}
