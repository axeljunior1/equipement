package com.projet.equipement.dto.retour;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RetourPostDto {


    @NotNull
    private Long venteId;

    @NotNull
    private Long typeId;

    @NotNull
    private Long etatId;

}
