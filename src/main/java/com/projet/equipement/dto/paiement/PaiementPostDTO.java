package com.projet.equipement.dto.paiement;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaiementPostDTO {

    private Long factureId;
    private BigDecimal montantPaye;
    private String modePaiement;
    private String reference;
}


