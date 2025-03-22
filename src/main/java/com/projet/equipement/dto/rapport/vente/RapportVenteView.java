package com.projet.equipement.dto.rapport.vente;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface RapportVenteView {

    Long getIdProduit();

    String getNomProduit();

    Long getQuantiteTotale();

    java.math.BigDecimal getPrixAchat();

    java.math.BigDecimal getPrixVente();

    java.math.BigDecimal getMontantVenteTotal();

    java.math.BigDecimal getMargeTotale();

    Long getNombreVentes();

    java.time.LocalDateTime getPremiereVente();

    java.time.LocalDateTime getDerniereVente();
}

