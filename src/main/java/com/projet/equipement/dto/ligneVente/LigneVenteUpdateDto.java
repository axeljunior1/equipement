package com.projet.equipement.dto.ligneVente;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LigneVenteUpdateDto {

    private Double prixVente;

    private Integer quantite;

    private Long venteId;

    private Boolean actif;

    private Long produitId;

    private Long formatVenteId;

}
