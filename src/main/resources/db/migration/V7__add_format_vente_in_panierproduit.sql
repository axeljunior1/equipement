alter table PANIER_PRODUIT
    add format_vente_id INTEGER not null default 1;


alter table PANIER_PRODUIT
    add constraint PANIER_PRODUIT_FORMAT_VENTE_ID_fk
        foreign key (format_vente_id) references FORMAT_VENTE;

alter table LIGNES_VENTES
    add format_vente_id INTEGER default 1;

alter table LIGNES_VENTES
    add constraint LIGNES_VENTES_FORMAT_VENTE_ID_fk
        foreign key (format_vente_id) references FORMAT_VENTE;
