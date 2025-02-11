package com.projet.equipement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class LigneCaisse {

    @NotNull
    public Double lVentePrixVenteUnitaire;
    @NotNull
    public Integer lVenteQuantite;
    @NotNull
    public Integer lVenteProduitId;

}
