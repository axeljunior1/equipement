package com.projet.equipement.dto.validerPanier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValiderPanierDTO {

    private Long idClient;
    private Long idPanier;
    private Long idEmploye;

}
