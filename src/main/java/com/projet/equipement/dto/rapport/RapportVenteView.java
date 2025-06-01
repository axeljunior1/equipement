package com.projet.equipement.dto.rapport;

public interface RapportVenteView {

    Long getIdProduit();

    String getNomProduit();

    String getLibelleFormat();

    String getUniteVenteCode();

    Long getQuantiteTotale();

    Long getQuantiteParFormat();

    java.math.BigDecimal getPrixAchat();

    java.math.BigDecimal getPrixVente();

    java.math.BigDecimal getMontantVenteTotal();

    java.math.BigDecimal getMargeTotale();

    Long getNombreVentes();

    java.time.LocalDateTime getPremiereVente();

    java.time.LocalDateTime getDerniereVente();

}

