package com.projet.equipement.dto.vente;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class VenteFilterDto {

    private Long venteId;
    private Boolean venteIdPartiel;

    private BigDecimal montantMin;
    private BigDecimal montantMax;

    private Long clientId;
    private Long employeId;

    // ⭐ clé métier
    private Long etatVenteId;

    private Boolean actif;

    private LocalDateTime createdAtFrom;
    private LocalDateTime createdAtTo;

    private List<Long> produitIds;
    private Long produitId;
}
