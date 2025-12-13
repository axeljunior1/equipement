package com.projet.equipement.dto.devise;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviseGetDto {


    private Long id ;

    private String code;     // Ex: USD, EUR

    private String nom;      // Ex: Dollar américain

    private String symbole;  // Ex: $

}
