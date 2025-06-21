package com.projet.equipement.dto.vente;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VenteLightDto {
    private Long id;
    private Double montantTotal;
    private LocalDateTime createdAt;

    private String clientNom;
    private String employeNom;
}
