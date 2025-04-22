package com.projet.equipement.enumeration;

// SalesState.java
public enum SalesState {
    CREATED,            // Vente créée
    VALIDATED,          // Vente validée et en attente de paiement
    PARTIALLY_PAID,     // Paiement partiel effectué
    FULLY_PAID,         // Paiement complet effectué
    PARTIALLY_REFUNDED, // Retour partiel sur une ou plusieurs lignes
    FULLY_REFUNDED      // Annulation totale de la vente
}
