package com.projet.equipement.dto.mvt_stk;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MouvementStockPostDto {

    private String reference; // Référence unique du mouvement

    private Long produitId  ; // Référence vers le produit (relation Many-to-One)

    private Integer quantite; // Quantité du mouvement (doit être positive)

    private String typeMouvementCode; // Relation Many-to-One vers types_mouvement_stock

    private Long typeMouvementId; // Relation Many-to-One vers types_mouvement_stock

    private LocalDateTime dateMouvement; // Date du mouvement (par défaut : maintenant)

    private String commentaire; // Commentaire facultatif

    private LocalDateTime createdAt; // Date de création

    private Long idLigneOrigine;

    private Long idEvenementOrigine;


}
