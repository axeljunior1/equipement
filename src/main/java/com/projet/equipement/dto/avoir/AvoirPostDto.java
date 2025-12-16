package com.projet.equipement.dto.avoir;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AvoirPostDto {
    private Long id;
    private Long clientId;
    private Long retourId;
    private Long venteOrigineId;

    private BigDecimal montantInitial;

    private String etat;
    private LocalDateTime createdAt;
}
