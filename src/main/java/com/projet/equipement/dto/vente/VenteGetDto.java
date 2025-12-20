package com.projet.equipement.dto.vente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projet.equipement.constants.RefCodes;
import com.projet.equipement.dto.client.ClientGetDto;
import com.projet.equipement.dto.employe.EmployeGetDto;
import com.projet.equipement.dto.etatDto.EtatVenteGetDto;
import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import com.projet.equipement.dto.paiement.PaiementGetDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class VenteGetDto {

    private Long id ;

    private Double montantTotal;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private ClientGetDto client;

    private Boolean actif;

    private EtatVenteGetDto etat;

    private EmployeGetDto employe;


    private List<LigneVenteGetDto> ligneVentes;

    private List<PaiementGetDTO> paiements;

    @JsonProperty("resteAPayer")
    public BigDecimal getResteAPayer() {
        if (montantTotal == null || paiements == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalPaye = paiements.stream()
                .filter(p -> p.getMontantPaye() != null && (Objects.equals(p.getEtat().getLibelle(), RefCodes.EtatPaiement.SUCCES) || Objects.equals(p.getEtat().getLibelle(), "PAIEMENT_PARTIEL")))
                .map(PaiementGetDTO::getMontantPaye)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return BigDecimal.valueOf(montantTotal).subtract(totalPaye);
    }


}
