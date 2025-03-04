package com.projet.equipement.dto.paiement;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaiementGetDTO {

    private Long idPaiement;
    private Long factureId;
    private Long etatId;
    private BigDecimal montantPaye;
    private String modePaiement;
    private String reference;
    private LocalDateTime createdAt;
}


