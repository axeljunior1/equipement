package com.projet.equipement.dto.facture;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FactureGetDTO {

    private Long idFacture;
    private Long venteId;
    private String numeroFacture;
    private LocalDateTime dateFacture;
    private BigDecimal montantTotal;
    private String statut;
    private String modePaiement;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
