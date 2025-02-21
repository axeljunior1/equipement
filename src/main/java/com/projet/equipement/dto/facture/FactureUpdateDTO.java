package com.projet.equipement.dto.facture;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FactureUpdateDTO {

    private String numeroFacture;
    private BigDecimal montantTotal;
    private String statut;
    private String modePaiement;


}


