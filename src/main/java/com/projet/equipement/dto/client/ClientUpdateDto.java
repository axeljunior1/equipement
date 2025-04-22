package com.projet.equipement.dto.client;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ClientUpdateDto {
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    @Builder.Default
    private Boolean actif = true;
}
