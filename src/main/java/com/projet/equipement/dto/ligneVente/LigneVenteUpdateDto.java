package com.projet.equipement.dto.ligneVente;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LigneVenteUpdateDto {

    private Double prixVenteUnitaire;

    private Integer quantite;

    private Integer venteId;

    private Boolean actif;

    private Integer produitId;

}
