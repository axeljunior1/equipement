package com.projet.equipement.dto.achat;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AchatUpdateDto {

    private Integer montantTotal;

//    private LocalDateTime dateDerniereMiseAjour;

//    private Integer clientId;

    private Integer employeId;

}
