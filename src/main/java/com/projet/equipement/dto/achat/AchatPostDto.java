package com.projet.equipement.dto.achat;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AchatPostDto {

    private Double montantTotal;

    private Long employeId;

}
