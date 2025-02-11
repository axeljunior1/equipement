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

//    private LocalDateTime dateDerniereMiseAjour;

//    private Integer clientId;

    private Integer employeId;

    private Boolean actif;

}
