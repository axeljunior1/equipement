package com.projet.equipement.dto.utilisationAvoir;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtilisationAvoirGetDto {

    private Long id;

    private Long avoirId;

    private Long venteId;

    private Double montantUtilise;

    private LocalDateTime createdAt;
}