package com.projet.equipement.constants;

public class RefCodes {


    private RefCodes() {
    }


    public static final class EtatAchat {
        public static final String CREEE = "CREEE";
        public static final String VALIDEE = "VALIDEE";
        public static final String FERMEE = "FERMEE";
        public static final String ANNULEE = "ANNULEE";
    }

    public static final class EtatVente {
        public static final String CREEE = "CREEE";
        public static final String EN_ATTENTE_PAIEMENT = "EN_ATTENTE_PAIEMENT";
        public static final String PAIEMENT_PARTIEL = "PAIEMENT_PARTIEL";
        public static final String VENTE_A_CREDIT = "VENTE_A_CREDIT";
        public static final String PAYEE = "PAYEE";
        public static final String FERMEE = "FERMEE";
        public static final String REMBOURSEE = "REMBOURSEE";
        public static final String ANNULEE = "ANNULEE";
    }

    public static final class EtatPaiement {
        public static final String SUCCES = "SUCCES";
        public static final String ERREUR = "ERREUR";
        public static final String REFUS = "REFUS";
        public static final String PENDING = "PENDING";

    }

    public static final class ModePaiement {

        /*
        * Espèces
Carte bancaire
Mobile Money
Virement bancaire
Avoir

        * */
        public static final String ESPECES = "Espèces";
        public static final String CARD = "Carte bancaire";
        public static final String MOBILE_MONEY = "Mobile Money";
        public static final String VIREMENT = "Virement bancaire";
        public static final String AVOIR = "Avoir";

    }


}
