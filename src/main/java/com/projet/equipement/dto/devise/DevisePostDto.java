package com.projet.equipement.dto.devise;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DevisePostDto {

    @NotNull
    private String code;     // Ex: USD, EUR

    @NotNull
    private String nom;      // Ex: Dollar américain

    @NotNull
    private String symbole;  // Ex: $

}
