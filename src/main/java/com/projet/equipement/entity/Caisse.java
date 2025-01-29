package com.projet.equipement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // Ignore les champs JSON inconnus
public class Caisse {

    private String clientNom;
    private String clientPrenom;
    private String clientEmail;
    private String clientTelephone;

    private Integer venteMontantTotal;
    private Long venteClientId;
    private Long venteEmployeId;

    private List<LigneCaisse> lignesCaisses = new ArrayList<LigneCaisse>();


}

