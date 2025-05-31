package com.projet.equipement.dto.achat;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AchatUpdateDto {

    private Double montantTotal;


    private Long employeId;

    private Boolean actif;

}
