package com.projet.equipement.dto.vente;

import com.projet.equipement.dto.employe.EmployeGetDto;
import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import com.projet.equipement.dto.paiement.PaiementGetDTO;
import com.projet.equipement.entity.Client;
import com.projet.equipement.entity.EtatVente;
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

    private Long clientId;

    private String clientNom;

    private Client client;

    private Boolean actif;

    private Long employeId;

    private Long etatId;

    private EtatVente etat;

    private String employeNom;

    private EmployeGetDto employe;

    private List<Long> lignesVenteId;

    private List<LigneVenteGetDto> ligneVentes;

    private List<PaiementGetDTO> paiements;

    public BigDecimal getResteAPayer() {
        if (montantTotal == null || paiements == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalPaye = paiements.stream()
                .filter(p -> p.getMontantPaye() != null && (Objects.equals(p.getEtat().getLibelle(), "PAYEE") || Objects.equals(p.getEtat().getLibelle(), "PAIEMENT_PARTIEL")))
                .map(PaiementGetDTO::getMontantPaye)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return BigDecimal.valueOf(montantTotal).subtract(totalPaye);
    }


}
