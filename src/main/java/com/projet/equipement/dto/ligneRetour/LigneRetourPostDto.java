package com.projet.equipement.dto.ligneRetour;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class LigneRetourPostDto {

    @NotNull
    private Long retourId;

    @NotNull
    private Long ligneVenteId;

    @NotNull
    private Integer quantite;

    @Builder.Default
    private LocalDateTime dateCreation = LocalDateTime.now();

}
