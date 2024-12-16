package com.projet.equipement.dto.mouvementStock;

import com.projet.equipement.entity.TypeMouvement;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MouvementStockUpdateDto {
    private Long produitId ;

    private Long utilisateurId ;

    @Enumerated(EnumType.STRING)
    private TypeMouvement typeMouvement ;

    private Integer quantity ;

    private Long entreeId;

    private Long sortieId;

}
