package com.projet.equipement.enumeration;

// SalesEvent.java
public enum SalesEvent {
    VALIDATE,      // Validation de la vente
    PAY_PARTIAL,   // Paiement partiel effectué
    PAY_FULL,      // Paiement complet effectué
    REFUND_PARTIAL,// Retour partiel sur un ou des produit(s)
    REFUND_FULL    // Retour total / annulation complète de la vente
}

