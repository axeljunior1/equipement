package com.projet.equipement.dto.paiement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaiementPostDTO {

    private Long factureId;
    private BigDecimal montantPaye;
    private String modePaiement;
    private String reference;
    private Long etatId;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}


