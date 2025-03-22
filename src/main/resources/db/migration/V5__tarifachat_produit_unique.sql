alter table TARIF_ACHAT
    add constraint TARIF_ACHAT_uk
        unique (ID_PRODUIT);