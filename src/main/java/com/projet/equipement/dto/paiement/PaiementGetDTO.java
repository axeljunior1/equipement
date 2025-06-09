package com.projet.equipement.dto.paiement;

import com.projet.equipement.dto.etatPaiement.EtatPaiementGetDTO;
import com.projet.equipement.entity.EtatPaiement;
import com.projet.equipement.entity.ModePaiement;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaiementGetDTO {

    private Long idPaiement;
    private Long venteId;
    private Long etatId;
    private BigDecimal montantPaye;
    private ModePaiement modePaiement;
    private String reference;
    private LocalDateTime createdAt;
    private EtatPaiement etat;
}


