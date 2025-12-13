alter table ACHATS
    add ETAT_ID INTEGER;

alter table ACHATS
    add constraint ACHATS_ETAT_ACHAT_ID_fk
        foreign key (ETAT_ID) references ETAT_ACHAT;
