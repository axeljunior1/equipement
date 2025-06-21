package com.projet.equipement.dto.validerPanier;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValiderPanierDTO {


    @NotNull
    private Long idClient;
    @NotNull
    private Long idPanier;
    @NotNull
    private Long idEmploye;

}
