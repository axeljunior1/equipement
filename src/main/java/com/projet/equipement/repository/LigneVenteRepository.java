package com.projet.equipement.repository;

import com.projet.equipement.dto.rapport.vente.RapportVenteView;
import com.projet.equipement.entity.LigneVente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LigneVenteRepository extends JpaRepository<LigneVente, Long> {
    @Query("select lv from LigneVente lv where lv.vente.id = :id and lv.actif=true")
    Page<LigneVente> findByVenteId(@Param("id") Long id, Pageable pageable);

    @Query("select lv from LigneVente lv where lv.vente.id = :id and lv.actif=true ")
    List<LigneVente> findByVenteId(@Param("id") Long id);

    @Query("select l from LigneVente l where l.actif = true")
    Page<LigneVente> findAllLine(Pageable pageable);

    @Query("SELECT SUM(l.prixVente * l.quantite) FROM LigneVente l WHERE l.vente.id = :venteId and l.actif=true ")
    Double sumTotalByVenteId(@Param("venteId") Long venteId);

    @Query(
            """
      SELECT
          p.id  as ID_PRODUIT,
          p.nom AS NOM_PRODUIT,
          SUM(l.quantite) AS QUANTITE_TOTALE,
          t.prixAchat AS PRIX_ACHAT,
          p.prixVente AS PRIX_VENTE,
          SUM(l.quantite * l.prixVente) AS MONTANT_VENTE_TOTAL,
          SUM((l.quantite * l.prixVente) - (l.quantite * t.prixAchat)) AS MARGE_TOTALE,
          COUNT(DISTINCT v.id) AS NOMBRE_VENTES,
          MIN(v.createdAt) AS PREMIERE_VENTE,
          MAX(v.createdAt) AS DERNIERE_VENTE
      FROM LigneVente l 
           JOIN Vente v ON v.id = l.vente.id
           JOIN Produit p ON l.produit.id = p.id
           JOIN TarifAchat t ON p.id = t.produit.id
           JOIN Client c ON v.client.id = c.id
           JOIN EtatVente e ON v.etat.id = e.id AND e.libelle = 'CONFIRME'
      GROUP BY p.id, p.nom, t.prixAchat, p.prixVente
      """
    )
    List<RapportVenteView> rapportVente(@Param("start")LocalDateTime start, @Param("end")LocalDateTime end);

}
