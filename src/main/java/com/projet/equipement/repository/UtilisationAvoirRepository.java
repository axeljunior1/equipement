package com.projet.equipement.repository;

import com.projet.equipement.entity.Retour;
import com.projet.equipement.entity.UtilisationAvoir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UtilisationAvoirRepository extends JpaRepository<UtilisationAvoir, Long> {

    List<UtilisationAvoir> findByAvoir_Id(Long idAvoir);

    @Query("""
               select coalesce(sum(u.montantUtilise), 0)
               from UtilisationAvoir u
               where u.avoir.id = :avoirId
            """)
    BigDecimal sumMontantUtiliseByAvoirId(Long avoirId);

}
