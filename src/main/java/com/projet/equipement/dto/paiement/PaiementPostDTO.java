package com.projet.equipement.dto.paiement;

import com.projet.equipement.entity.ModePaiement;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Long venteId;
    @NotNull
    private BigDecimal montantPaye;
    @NotNull
    private String modePaiementCode;
    private Long modePaiementId;
    @NotNull
    private String reference;
    @NotNull
    private Long etatId;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updateddAt = LocalDateTime.now();
}


