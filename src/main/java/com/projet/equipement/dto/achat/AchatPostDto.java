package com.projet.equipement.dto.achat;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class AchatPostDto {

    @NotNull
    private Double montantTotal;

    @NotNull
    private Long employeId;
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

}
