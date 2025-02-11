package com.projet.equipement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @NotNull(message = "test")
    @NotBlank(message = "test")
    public String clientNom;

    @NotNull(message = "test")
    @NotBlank(message = "test")
    public String clientPrenom;

    @NotNull(message = "test")
    @NotBlank(message = "test")
    public String clientEmail;

    @NotNull(message = "test")
    @NotBlank(message = "test")
    public String clientTelephone;

    @NotNull(message = "test")
    @NotBlank(message = "test")
    public Double venteMontantTotal;

    public Long venteClientId;

    @NotNull
    @NotBlank
    public Long venteEmployeId;

    public List<LigneCaisse> lignesCaisses = new ArrayList<LigneCaisse>();


}

