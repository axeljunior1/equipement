package com.projet.equipement.repository;

import com.projet.equipement.entity.StockCourant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(readOnly = true)  // Bloque les modifications
public interface StockCourantRepository extends JpaRepository<StockCourant, Long> {

    // repository en lecture seule , ne pas faire de update, post, delet car StockCourant est une vue et pas une table
    
}
