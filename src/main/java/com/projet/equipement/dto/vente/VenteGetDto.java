package com.projet.equipement.dto.vente;

import com.projet.equipement.entity.LigneVente;
import com.projet.equipement.entity.Vente;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class VenteGetDto {

    private Long id ;

    private Double montantTotal;

    private LocalDateTime dateDerniereMiseAjour;

    private Long clientId;

    private String clientNom;

    private Boolean actif;

    private Long employeId;

    private String employeNom;

    private List<Long> lignesVenteId;


    public VenteGetDto(Vente vente) {
        this.id = vente.getId();
        this.montantTotal = vente.getMontantTotal();
        this.clientId = vente.getClient().getId();
        this.employeId = vente.getEmploye().getId();
        this.clientNom = vente.getClient().getNom();
        this.employeNom = vente.getEmploye().getNom();
        this.actif = vente.getActif();
        this.lignesVenteId =  vente.getLigneVentes().stream().map(LigneVente::getId).collect(Collectors.toList()) ;
    }
}
