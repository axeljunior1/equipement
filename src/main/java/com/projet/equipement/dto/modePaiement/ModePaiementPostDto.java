package com.projet.equipement.dto.modePaiement;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ModePaiementPostDto {


    private String  code;

    private String  description;

    @Builder.Default
    private boolean  actif = true;

}
