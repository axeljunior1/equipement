package com.projet.equipement.dto.mvt_stk;

import com.projet.equipement.entity.MouvementStock;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.TypeMouvementStock;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MouvementStockGetDto {

    private String reference; // Référence unique du mouvement

    private Long produitId  ; // Référence vers le produit (relation Many-to-One)

    private String produitNom  ; // Référence vers le produit (relation Many-to-One)

    private Integer quantite; // Quantité du mouvement (doit être positive)

    private String typeMouvementCode; // Relation Many-to-One vers types_mouvement_stock

    private LocalDateTime dateMouvement; // Date du mouvement (par défaut : maintenant)

    private String commentaire; // Commentaire facultatif

    private LocalDateTime createdAt; // Date de création


    public MouvementStockGetDto(MouvementStock mouvementStock) {
        this.produitId = mouvementStock.getProduit().getId();
        this.reference = mouvementStock.getReference();
        this.quantite = mouvementStock.getQuantite();
        this.typeMouvementCode = mouvementStock.getTypeMouvement().getCode();
        this.dateMouvement = mouvementStock.getDateMouvement();
        this.commentaire = mouvementStock.getCommentaire();
        this.createdAt = mouvementStock.getCreatedAt();
        this.produitNom = mouvementStock.getProduit().getNom();
    }


}
