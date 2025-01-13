package com.projet.equipement.dto.ligneVente;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LigneVenteUpdateDto {

    private Integer prixVenteUnitaire;

    private Integer quantite;

    private Integer venteId;

    private Integer produitId;

}
