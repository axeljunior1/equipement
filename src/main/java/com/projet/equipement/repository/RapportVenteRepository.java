package com.projet.equipement.repository;


import com.projet.equipement.dto.rapport.RapportVenteView;
import com.projet.equipement.entity.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RapportVenteRepository extends JpaRepository<LigneVente, Integer> {

    @Query(
            """
    
      SELECT
            p.id  idProduit,
          p.nom nomProduit, fv.quantiteParFormat quantiteParFormat,
          SUM(l.quantite * fv.quantiteParFormat) quantiteTotale,
          t.prixAchat prixAchat,
          fv.prixVente prixVente,
          SUM(l.quantite * l.prixVente) montantVenteTotal,
          SUM((l.quantite * l.prixVente) - (l.quantite * t.prixAchat)) margeTotale,
          COUNT(DISTINCT v.id) nombreVentes,
          MIN(v.createdAt) premiereVente,
          MAX(v.createdAt) derniereVente,
                fv.id, fv.libelleFormat libelleFormat, fv.uniteVente.code uniteVenteCode

      FROM LigneVente l
           JOIN Vente v ON v.id = l.vente.id and v.createdAt between :start and :end
           JOIN Produit p ON l.produit.id = p.id
           JOIN TarifAchat t ON p.id = t.produit.id
           JOIN Client c ON v.client.id = c.id
           JOIN EtatVente e ON v.etat.id = e.id AND (e.libelle = 'FERMEE' or e.libelle = "PAYEE")
                 join FormatVente fv on fv.id = l.formatVente.id
      GROUP BY p.id, p.nom, t.prixAchat, p.prixVente,l.prixVente, l.formatVente.id
      """
    )
    List<RapportVenteView> rapportVente(@Param("start") LocalDateTime start, @Param("end")LocalDateTime end);


}
