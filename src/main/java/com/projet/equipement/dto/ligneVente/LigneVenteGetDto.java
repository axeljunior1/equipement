package com.projet.equipement.dto.ligneVente;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LigneVenteGetDto {

    private Long id;

    private Double prixVente;

    private Integer quantite;

    private Long venteId;

    private Boolean actif;

    private Long produitId;

    private String  produitNom;

    private String  produitDeviseVenteCode;

    private String  produitDeviseVenteSymbole;

    private Long formatVenteId;

    private String formatVenteLibelle;



}
