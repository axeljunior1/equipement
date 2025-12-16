package com.projet.equipement.dto.avoir;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AvoirGetDto {
    private Integer id;
    private Integer clientId;
    private Integer retourId;
    private Integer venteOrigineId;

    private BigDecimal montantInitial;
    private BigDecimal montantRestant;

    private String etat;
    private LocalDateTime createdAt;
}
