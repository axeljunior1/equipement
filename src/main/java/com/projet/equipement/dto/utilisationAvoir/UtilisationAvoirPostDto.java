package com.projet.equipement.dto.utilisationAvoir;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtilisationAvoirPostDto {

    private Long avoirId;

    private Long venteId;

    private BigDecimal montantUtilise;

    private LocalDateTime createdAt;
}