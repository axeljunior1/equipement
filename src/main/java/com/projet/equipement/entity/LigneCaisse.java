package com.projet.equipement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
public class LigneCaisse {

    public Double lVentePrixVenteUnitaire;
    public Integer lVenteQuantite;
    public Integer lVenteProduitId;

}
