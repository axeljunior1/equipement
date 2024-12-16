package com.projet.equipement.dto.mouvementStock;

import com.projet.equipement.entity.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MouvementStockPostDto {

    @NotNull
    private Long produitId ;

    @NotNull
    private Long utilisateurId ;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeMouvement typeMouvement ;

    @NotNull
    private Integer quantity ;

    @NotNull
    private Long entreeId;

    @NotNull
    private Long sortieId;



}
