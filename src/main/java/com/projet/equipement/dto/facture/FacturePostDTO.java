package com.projet.equipement.dto.facture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacturePostDTO {

    @Builder.Default
    private Double montantRestant = 0.0;
    @Builder.Default
    private Double montantTotal = 0.0;
    private String numeroFacture;
    private Long venteId;
    private Long etatId ;
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();


}
