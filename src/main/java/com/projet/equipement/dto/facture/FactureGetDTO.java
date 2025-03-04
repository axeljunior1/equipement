package com.projet.equipement.dto.facture;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FactureGetDTO {

    private Long idFacture;
    private Long venteId;
    private Long etatId;
    private String etatNom;
    private String numeroFacture;
    private LocalDateTime dateFacture;
    private BigDecimal montantTotal;
    private BigDecimal montantRestant;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
