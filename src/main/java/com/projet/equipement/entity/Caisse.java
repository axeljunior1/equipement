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

    public String clientNom;
    public String clientPrenom;
    public String clientEmail;
    public String clientTelephone;

    public Integer venteMontantTotal;
    public Long venteClientId;
    public Long venteEmployeId;

    public List<LigneCaisse> lignesCaisses = new ArrayList<LigneCaisse>();


}

