-- we don't know how to generate root <with-no-name> (class Root) :(

create table AUTHORITY
(
    ID  INTEGER auto_increment
        primary key,
    NOM CHARACTER VARYING(100) not null
);

create table CATEGORIE
(
    CATEGORIE_ID INTEGER auto_increment
        primary key,
    NOM          CHARACTER VARYING(255) not null,
    DESCRIPTION  CHARACTER VARYING,
    CREATED_AT   TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT   TIMESTAMP default CURRENT_TIMESTAMP
);

create table CLIENTS
(
    ID_CLIENT  INTEGER auto_increment
        primary key,
    NOM        CHARACTER VARYING(50)  not null,
    PRENOM     CHARACTER VARYING(50)  not null,
    EMAIL      CHARACTER VARYING(100),
    TELEPHONE  CHARACTER VARYING(20)  not null,
    ADRESSE    CHARACTER VARYING,
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    ACTIF      BOOLEAN   default TRUE not null
);

create table EMPLOYES
(
    ID_EMPLOYE BIGINT auto_increment
        primary key,
    NOM        CHARACTER VARYING(255) not null,
    PRENOM     CHARACTER VARYING(255) not null,
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    PASSWORD   CHARACTER VARYING(255) not null,
    ACTIF      BOOLEAN   default TRUE not null
);

create table ACHATS
(
    ID_ACHAT      INTEGER auto_increment
        primary key,
    EMPLOYE_ID    INTEGER                not null,
    MONTANT_TOTAL NUMERIC(10, 2)         not null,
    CREATED_AT    TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT    TIMESTAMP default CURRENT_TIMESTAMP,
    ACTIF         BOOLEAN   default TRUE not null,
    constraint ACHATS_EMPLOYES_ID_EMPLOYE_FK
        foreign key (EMPLOYE_ID) references EMPLOYES,
    constraint ACHATS_EMPLOYE_ID_FKEY
        foreign key (EMPLOYE_ID) references EMPLOYES
            on delete set null,
    check ("MONTANT_TOTAL" >= CAST(0 AS NUMERIC(1)))
);

create table EMPLOYES_ROLES
(
    ID_UTILISATEUR BIGINT not null,
    ID_ROLE        BIGINT not null,
    constraint EMPLOYES_ROLES_PK
        primary key (ID_ROLE, ID_UTILISATEUR),
    constraint EMPLOYES_ROLES_EMPLOYES_ID_EMPLOYE_FK
        foreign key (ID_UTILISATEUR) references EMPLOYES,
    constraint UTILISATEURS_ROLES_ID_UTILISATEUR_FKEY
        foreign key (ID_UTILISATEUR) references EMPLOYES
            on delete cascade
);

create table ETAT
(
    ID          INTEGER auto_increment
        primary key,
    NOM         CHARACTER VARYING(255) not null,
    DESCRIPTION CHARACTER VARYING(255),
    TYPE        CHARACTER VARYING(255) not null
);

create table ETAT_FACTURE
(
    ID          INTEGER auto_increment
        primary key,
    LIBELLE     CHARACTER VARYING(50) not null,
    DESCRIPTION CHARACTER VARYING
);

create table ETAT_PAIEMENT
(
    ID          INTEGER auto_increment
        primary key,
    LIBELLE     CHARACTER VARYING(50) not null,
    DESCRIPTION CHARACTER VARYING
);

create table ETAT_PANIER
(
    ID          INTEGER auto_increment
        primary key,
    LIBELLE     CHARACTER VARYING(50) not null,
    DESCRIPTION CHARACTER VARYING
);

create table ETAT_VENTE
(
    ID          INTEGER auto_increment
        primary key,
    LIBELLE     CHARACTER VARYING(50) not null,
    DESCRIPTION CHARACTER VARYING
);

create table FOURNISSEURS
(
    ID         INTEGER auto_increment
        primary key,
    NOM        CHARACTER VARYING(50) not null,
    PRENOM     CHARACTER VARYING(50) not null,
    EMAIL      CHARACTER VARYING(100),
    TELEPHONE  CHARACTER VARYING(20),
    ADRESSE    CHARACTER VARYING,
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP default CURRENT_TIMESTAMP
);

create table PANIER
(
    ID         INTEGER auto_increment
        primary key,
    ID_EMPLOYE INTEGER             not null,
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    ID_ETAT    INTEGER   default 1 not null,
    constraint PANIER_ETAT_ID_FK
        foreign key (ID_ETAT) references ETAT_PANIER,
    constraint PANIER_ID_EMPLOYE_FKEY
        foreign key (ID_EMPLOYE) references EMPLOYES
            on delete cascade
);

create table PRODUITS
(
    ID_PRODUIT     INTEGER auto_increment
        primary key,
    REFERENCE      CHARACTER VARYING(50),
    NOM            CHARACTER VARYING(100)  not null,
    DESCRIPTION    CHARACTER VARYING,
    PRIX_ACHAT     NUMERIC(10, 2),
    PRIX_VENTE     NUMERIC(10, 2),
    SOUS_CATEGORIE CHARACTER VARYING(50),
    MARQUE         CHARACTER VARYING(50),
    UNITE_MESURE   CHARACTER VARYING(20),
    SEUIL_LOT      INTEGER   default 0,
    SEUIL_PRODUIT  INTEGER   default 10,
    CREATED_AT     TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT     TIMESTAMP default CURRENT_TIMESTAMP,
    IMAGE          CHARACTER VARYING,
    STOCK_INITIAL  INTEGER   default 0,
    QR_CODE        BINARY LARGE OBJECT,
    EAN13          CHARACTER VARYING(13),
    CATEGORIE_ID   INTEGER,
    ACTIF          BOOLEAN   default FALSE not null,
    constraint PRODUITS_CATEGORIE_CATEGORIE_ID_FK
        foreign key (CATEGORIE_ID) references CATEGORIE,
    check ("PRIX_ACHAT" >= CAST(0 AS NUMERIC(1))),
    check ("PRIX_VENTE" >= "PRIX_ACHAT"),
    check ("SEUIL_LOT" >= 0)
);

create table LIGNES_ACHATS
(
    ID_LIGNES_ACHAT INTEGER auto_increment
        primary key,
    LOT_ID          INTEGER,
    PRIX_ACHAT      NUMERIC(10, 2)         not null,
    QUANTITE        INTEGER                not null,
    CREATED_AT      TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT      TIMESTAMP default CURRENT_TIMESTAMP,
    PRODUIT_ID      INTEGER,
    ACHAT_ID        INTEGER,
    ACTIF           BOOLEAN   default TRUE not null,
    constraint LIGNES_ACHATS_ACHATS_ID_ACHAT_FK
        foreign key (ACHAT_ID) references ACHATS,
    constraint LIGNES_ACHATS_PRODUITS_ID_FK
        foreign key (PRODUIT_ID) references PRODUITS,
    constraint CHECK_LOT_PRODUIT_NOT_NULL
        check (("PRODUIT_ID" IS NOT NULL)
            OR (("LOT_ID" IS NOT NULL)
                AND ("PRODUIT_ID" <> "LOT_ID"))),
    check ("PRIX_ACHAT" >= CAST(0 AS NUMERIC(1))),
    check ("QUANTITE" > 0)
);

create table MOUVEMENT_STOCK
(
    ID                   INTEGER auto_increment
        primary key,
    REFERENCE            CHARACTER VARYING(50),
    PRODUIT_ID           INTEGER not null,
    QUANTITE             INTEGER not null,
    TYPE_MOUVEMENT_ID    INTEGER not null,
    DATE_MOUVEMENT       TIMESTAMP default CURRENT_TIMESTAMP,
    COMMENTAIRE          CHARACTER VARYING,
    CREATED_AT           TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT           TIMESTAMP default CURRENT_TIMESTAMP,
    ID_EVENEMENT_ORIGINE INTEGER not null,
    ID_LIGNE_ORIGINE     INTEGER not null,
    constraint MOUVEMENT_STOCK_PRODUITS_ID_PRODUIT_FK
        foreign key (PRODUIT_ID) references PRODUITS,
    check ("QUANTITE" > 0)
);

create table PANIER_PRODUIT
(
    ID         INTEGER auto_increment
        primary key,
    ID_PANIER  INTEGER not null,
    ID_PRODUIT INTEGER not null,
    QUANTITE   INTEGER not null,
    PRIX_VENTE NUMERIC(10, 2),
    constraint PANIER_PRODUIT_ID_PANIER_FKEY
        foreign key (ID_PANIER) references PANIER
            on delete cascade,
    constraint PANIER_PRODUIT_ID_PRODUIT_FKEY
        foreign key (ID_PRODUIT) references PRODUITS
            on delete cascade,
    constraint PANIER_PRODUIT_PANIER_ID_FK
        foreign key (ID_PANIER) references PANIER,
    constraint PANIER_PRODUIT_PRODUITS_ID_PRODUIT_FK
        foreign key (ID_PRODUIT) references PRODUITS,
    check ("QUANTITE" > 0)
);

create table ROLES
(
    ID_ROLE     INTEGER auto_increment
        primary key,
    NOM         CHARACTER VARYING not null,
    DESCRIPTION CHARACTER VARYING
);

create table ROLE_AUTHORITY
(
    ID_ROLE      INTEGER not null,
    ID_AUTHORITY INTEGER not null,
    constraint ROLE_AUTHORITY_AUTHORITY_ID_FK
        foreign key (ID_AUTHORITY) references AUTHORITY,
    constraint ROLE_AUTHORITY_ROLES_ID_ROLE_FK
        foreign key (ID_ROLE) references ROLES
);

create table ROLE_EMPLOYE
(
    ID_ROLE    INTEGER not null,
    ID_EMPLOYE INTEGER not null,
    constraint ROLE_EMPLOYE_ID_EMPLOYE_FK
        foreign key (ID_EMPLOYE) references EMPLOYES,
    constraint ROLE_EMPLOYE_ROLES_ID_ROLE_FK
        foreign key (ID_ROLE) references ROLES
);

create table TARIF_ACHAT
(
    ID         INTEGER auto_increment,
    ID_PRODUIT INTEGER        not null,
    PRIX_ACHAT NUMERIC(10, 2) not null,
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP,
    constraint TARIF_ACHAT_PK
        primary key (ID),
    constraint TARIF_ACHAT_PRODUITS_ID_PRODUIT_FK
        foreign key (ID_PRODUIT) references PRODUITS
);

create table TYPES_MOUVEMENT_STOCK
(
    ID             INTEGER auto_increment,
    CODE           CHARACTER VARYING(50) not null
        constraint TYPES_MOUVEMENT_STOCK_PK
            unique,
    NOM            CHARACTER VARYING(50) not null,
    DESCRIPTION    CHARACTER VARYING,
    DATE_CREATION  TIMESTAMP default CURRENT_TIMESTAMP,
    TYPE_MOUVEMENT CHARACTER VARYING(50)
);

create table VENTES
(
    ID_VENTES     INTEGER auto_increment
        primary key,
    CLIENT_ID     INTEGER                not null,
    DATE_VENTE    DATE,
    MONTANT_TOTAL NUMERIC(10, 2)         not null,
    CREATED_AT    TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT    TIMESTAMP default CURRENT_TIMESTAMP,
    EMPLOYE_ID    INTEGER,
    ACTIF         BOOLEAN   default TRUE not null,
    ETAT_ID       INTEGER   default 1    not null,
    constraint VENTES_CLIENT_ID_FKEY
        foreign key (CLIENT_ID) references CLIENTS
            on delete set null,
    constraint VENTES_EMPLOYES_ID_FK
        foreign key (EMPLOYE_ID) references EMPLOYES,
    constraint VENTES_ETAT_VENTE_ID_FK
        foreign key (ETAT_ID) references ETAT_VENTE
);

create table FACTURES
(
    ID_FACTURE      INTEGER auto_increment
        primary key,
    VENTE_ID        INTEGER                             not null,
    NUMERO_FACTURE  CHARACTER VARYING(50)               not null,
    DATE_FACTURE    TIMESTAMP default CURRENT_TIMESTAMP not null,
    MONTANT_TOTAL   NUMERIC(10, 2)                      not null,
    CREATED_AT      TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT      TIMESTAMP default CURRENT_TIMESTAMP,
    ETAT_ID         INTEGER   default 1                 not null,
    MONTANT_RESTANT NUMERIC(10, 2),
    constraint FACTURES_ETAT_FACTURE_ID_FK
        foreign key (ETAT_ID) references ETAT_FACTURE,
    constraint FACTURES_VENTE_ID_FKEY
        foreign key (VENTE_ID) references VENTES
            on delete cascade,
    check ("MONTANT_TOTAL" >= CAST(0 AS NUMERIC(1)))
);

create table LIGNES_VENTES
(
    ID_LIGNES_VENTES    INTEGER auto_increment
        primary key,
    VENTE_ID            INTEGER              not null,
    PRODUIT_ID          INTEGER,
    QUANTITE            INTEGER              not null,
    PRIX_VENTE_UNITAIRE NUMERIC(10, 2)       not null,
    ACTIF               BOOLEAN default TRUE not null,
    constraint LIGNES_VENTES_PRODUIT_ID_FKEY
        foreign key (PRODUIT_ID) references PRODUITS
            on delete cascade,
    constraint LIGNES_VENTES_VENTE_ID_FKEY
        foreign key (VENTE_ID) references VENTES
            on delete cascade,
    check ("QUANTITE" > 0),
    check ("PRIX_VENTE_UNITAIRE" > CAST(0 AS NUMERIC(1)))
);

create table PAIEMENTS
(
    ID_PAIEMENT   INTEGER auto_increment
        primary key,
    FACTURE_ID    INTEGER             not null,
    MONTANT_PAYE  NUMERIC(10, 2)      not null,
    MODE_PAIEMENT CHARACTER VARYING(50),
    REFERENCE     CHARACTER VARYING(100),
    CREATED_AT    TIMESTAMP default CURRENT_TIMESTAMP,
    ETAT_ID       INTEGER   default 1 not null,
    constraint PAIEMENTS_ETAT_PAIEMENT_ID_FK
        foreign key (ETAT_ID) references ETAT_PAIEMENT,
    constraint PAIEMENTS_FACTURE_ID_FKEY
        foreign key (FACTURE_ID) references FACTURES
            on delete cascade
);

create table RETOURS
(
    ID_RETOUR  INTEGER auto_increment
        primary key,
    VENTE_ID   INTEGER           not null,
    PRODUIT_ID INTEGER           not null,
    QUANTITE   INTEGER           not null,
    RAISON     CHARACTER VARYING not null,
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    constraint RETOURS_PRODUIT_ID_FKEY
        foreign key (PRODUIT_ID) references PRODUITS
            on delete cascade,
    constraint RETOURS_VENTE_ID_FKEY
        foreign key (VENTE_ID) references VENTES
            on delete cascade,
    check ("QUANTITE" > 0)
);

CREATE FORCE VIEW "PUBLIC"."VUE_STOCK_COURANT"("ID_PRODUIT", "NOM", "STOCK_INITIAL", "STOCK_COURANT") AS
SELECT "ID_PRODUIT",
       "NOM",
       "STOCK_INITIAL",
       ("STOCK_INITIAL" + COALESCE((SELECT SUM("MS"."QUANTITE") AS "SUM"
                                    FROM "PUBLIC"."MOUVEMENT_STOCK" "MS"
                                             INNER JOIN "PUBLIC"."TYPES_MOUVEMENT_STOCK" "TMS"
                                                        ON 1 = 1
                                    WHERE ("MS"."PRODUIT_ID" = "P"."ID_PRODUIT")
                                      AND (CAST("TMS"."TYPE_MOUVEMENT" AS CHARACTER VARYING) =
                                           CAST('ENTREE' AS CHARACTER VARYING))
                                      AND ("MS"."TYPE_MOUVEMENT_ID" = "TMS"."ID")
                                    GROUP BY ()), CAST(0 AS BIGINT))) - COALESCE((SELECT SUM("MS"."QUANTITE") AS "SUM"
                                                                                  FROM "PUBLIC"."MOUVEMENT_STOCK" "MS"
                                                                                           INNER JOIN "PUBLIC"."TYPES_MOUVEMENT_STOCK" "TMS"
                                                                                                      ON 1 = 1
                                                                                  WHERE ("MS"."PRODUIT_ID" = "P"."ID_PRODUIT")
                                                                                    AND (
                                                                                      CAST("TMS"."TYPE_MOUVEMENT" AS CHARACTER VARYING) =
                                                                                      CAST('SORTIE' AS CHARACTER VARYING))
                                                                                    AND ("MS"."TYPE_MOUVEMENT_ID" = "TMS"."ID")
                                                                                  GROUP BY ()),
                                                                                 CAST(0 AS BIGINT)) AS "STOCK_COURANT"
FROM "PUBLIC"."PRODUITS" "P";



