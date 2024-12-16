package com.projet.equipement.dto.entree;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntreeUpdateDto {

    private Integer quantite;

    private Double prixAchat;

    private Long produitId;

    private Long fournisseurId;

    private Long utilisateurId;

    private Long mouvementId;
}
