alter table PRODUITS
    add constraint PRODUITS_pk
        unique (EAN13);