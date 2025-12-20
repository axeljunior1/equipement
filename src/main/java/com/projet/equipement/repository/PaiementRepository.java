package com.projet.equipement.repository;

import com.projet.equipement.entity.Paiement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    Page<Paiement> findByModePaiement(String modePaiement, Pageable pageable);

    @Query("""
    SELECT COALESCE(SUM(p.montantPaye), 0)
    FROM Paiement p
    WHERE p.vente.id = :venteId
      AND p.etat.libelle = :etatPaiement
""") // todo corrigeer les different etats de paiement possible dans les ventes egalement
    BigDecimal sumPaiementsSuccesByVente(@Param("venteId") Long venteId
    , @Param("etatPaiement") String etatPaiement);

    @Query("""
    SELECT p
    FROM Paiement p
    WHERE p.vente.id = :venteId
      AND p.etat.id = :etatSucces
""")
    List<Paiement> findPaiementsSuccesByVente(
            @Param("venteId") Long venteId,
            @Param("etatSucces") Integer etatSucces
    );

    Long id(Long id);
}
