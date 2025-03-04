package com.projet.equipement.dto.facture;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FactureUpdateDTO {

    private String numeroFacture;
    private BigDecimal montantTotal;
    private Double montantRestant;
    private Long etatId;

    private String statut;
    private String modePaiement;


}


