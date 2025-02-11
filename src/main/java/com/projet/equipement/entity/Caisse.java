package com.projet.equipement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = " Is Blank")
    public String clientNom;

    @NotBlank(message = " Is Blank")
    public String clientPrenom;


    public String clientEmail;

    @NotBlank(message = " Is Blank")
    public String clientTelephone;

    @NotNull
    public Double venteMontantTotal;

    public Long venteClientId;

    @NotNull
    public Long venteEmployeId;

    @NotNull
    @Valid
    public List<LigneCaisse> lignesCaisses = new ArrayList<LigneCaisse>();


}

