package com.projet.equipement.dto.paiement;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaiementUpdateDTO {

    private BigDecimal montantPaye;
    private String modePaiement;
    private String reference;
}
