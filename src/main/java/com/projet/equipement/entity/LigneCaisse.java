package com.projet.equipement.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class LigneCaisse extends MultiTenantEntity {

    @NotNull
    public Double lVentePrixVenteUnitaire;
    @NotNull
    public Integer lVenteQuantite;
    @NotNull
    public Integer lVenteProduitId;

}
