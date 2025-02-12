package com.projet.equipement.dto.achat;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AchatPostDto {

    @NotNull
    private Double montantTotal;

    @NotNull
    private Long employeId;

}
