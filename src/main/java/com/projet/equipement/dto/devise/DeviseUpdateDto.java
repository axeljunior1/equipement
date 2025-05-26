package com.projet.equipement.dto.devise;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class DeviseUpdateDto {

    @NonNull
    private String code;     // Ex: USD, EUR

    @NonNull
    private String nom;      // Ex: Dollar américain

    @NonNull
    private String symbole;  // Ex: $

}
