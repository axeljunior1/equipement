package com.projet.equipement.dto.modePaiement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModePaiementGetDto {

    private Long id ;

    private String  code;

    private String  description;

    private boolean  actif;

}
