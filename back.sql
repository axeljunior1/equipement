create table PUBLIC.DEVISE
(
    ID      INTEGER auto_increment
        primary key,
    CODE    CHARACTER VARYING(3)  not null
        unique,
    NOM     CHARACTER VARYING(50) not null,
    SYMBOLE CHARACTER VARYING(5)
);

create table PUBLIC.ROLES
(
    ID_ROLE     INTEGER auto_increment
        primary key,
    NOM         CHARACTER VARYING      not null,
    DESCRIPTION CHARACTER VARYING,
    TENANT_ID   CHARACTER VARYING(100) not null
);

create table PUBLIC.ROLE_AUTHORITY
(
    ID_ROLE      INTEGER                not null,
    ID_AUTHORITY INTEGER                not null,
    TENANT_ID    CHARACTER VARYING(100) not null,
    constraint ROLE_AUTHORITY_PK
        primary key (ID_ROLE, ID_AUTHORITY, TENANT_ID),
    constraint FK_RA_ROLE
        foreign key (ID_ROLE) references PUBLIC.ROLES
            on delete cascade
);

create table PUBLIC.ROLE_EMPLOYE
(
    ID_ROLE    INTEGER                not null,
    ID_EMPLOYE INTEGER                not null,
    TENANT_ID  CHARACTER VARYING(100) not null,
    constraint ROLE_EMPLOYE_PK
        primary key (ID_ROLE, ID_EMPLOYE, TENANT_ID),
    constraint FK_RE_ROLE
        foreign key (ID_ROLE) references PUBLIC.ROLES
            on delete cascade
);

create table PUBLIC.TARIF_ACHAT
(
    ID         INTEGER auto_increment,
    ID_PRODUIT INTEGER                not null,
    PRIX_ACHAT NUMERIC(10, 2)         not null,
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP,
    TENANT_ID  CHARACTER VARYING(100) not null,
    constraint PK_TARIF_ACHAT
        primary key (ID),
    constraint UK_TARIF_ACHAT
        unique (TENANT_ID, ID_PRODUIT)
);

create table PUBLIC.TENANT
(
    ID         CHARACTER VARYING(100) not null
        primary key,
    NAME       CHARACTER VARYING(255) not null,
    IS_ACTIVE  BOOLEAN   default TRUE,
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP
);

create table PUBLIC.APP_USER
(
    ID        INTEGER auto_increment
        primary key,
    USERNAME  CHARACTER VARYING(50)  not null
        unique,
    PASSWORD  CHARACTER VARYING(255) not null,
    ROLE      CHARACTER VARYING(30)  not null,
    TENANT_ID CHARACTER VARYING(100) not null,
    constraint FK_APP_USER_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade
);

create table PUBLIC.AUTHORITY
(
    ID        INTEGER auto_increment
        primary key,
    NOM       CHARACTER VARYING(100) not null,
    TENANT_ID CHARACTER VARYING(100) not null,
    constraint FK_AUTHORITY_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade
);

create index PUBLIC.IDX_AUTHORITY_TENANT_ID
    on PUBLIC.AUTHORITY (TENANT_ID);

create table PUBLIC.CATEGORIE
(
    CATEGORIE_ID INTEGER auto_increment
        primary key,
    NOM          CHARACTER VARYING(255) not null,
    DESCRIPTION  CHARACTER VARYING,
    CREATED_AT   TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT   TIMESTAMP default CURRENT_TIMESTAMP,
    TENANT_ID    CHARACTER VARYING(100) not null,
    constraint FK_CATEGORIE_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade
);

create index PUBLIC.IDX_CATEGORIE_TENANT_ID
    on PUBLIC.CATEGORIE (TENANT_ID);

create table PUBLIC.CLIENTS
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
    ACTIF      BOOLEAN   default TRUE not null,
    TENANT_ID  CHARACTER VARYING(100) not null,
    constraint CLIENTS_UK
        unique (TENANT_ID, NOM),
    constraint FK_CLIENT_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade
);

create index PUBLIC.IDX_CLIENTS_TENANT_ID
    on PUBLIC.CLIENTS (TENANT_ID);

create table PUBLIC.EMPLOYES
(
    ID_EMPLOYE BIGINT auto_increment
        primary key,
    NOM        CHARACTER VARYING(255) not null,
    PRENOM     CHARACTER VARYING(255) not null,
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    PASSWORD   CHARACTER VARYING(255) not null,
    ACTIF      BOOLEAN   default TRUE not null,
    TENANT_ID  CHARACTER VARYING(100) not null,
    constraint EMPLOYES_UK
        unique (TENANT_ID, NOM),
    constraint FK_EMPLOYES_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade
);

create table PUBLIC.ACHATS
(
    ID_ACHAT      INTEGER auto_increment
        primary key,
    EMPLOYE_ID    BIGINT,
    MONTANT_TOTAL NUMERIC(10, 2)         not null,
    CREATED_AT    TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT    TIMESTAMP default CURRENT_TIMESTAMP,
    ACTIF         BOOLEAN   default TRUE not null,
    TENANT_ID     CHARACTER VARYING(100) not null,
    constraint FK_ACHATS_EMPLOYE
        foreign key (EMPLOYE_ID) references PUBLIC.EMPLOYES
            on delete set null,
    constraint FK_ACHAT_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade,
    check ("MONTANT_TOTAL" >= CAST(0 AS NUMERIC(1)))
);

create index PUBLIC.IDX_ACHATS_TENANT_ID
    on PUBLIC.ACHATS (TENANT_ID);

create index PUBLIC.IDX_EMPLOYES_TENANT_ID
    on PUBLIC.EMPLOYES (TENANT_ID);

create table PUBLIC.EMPLOYES_ROLES
(
    ID_UTILISATEUR BIGINT                 not null,
    ID_ROLE        BIGINT                 not null,
    TENANT_ID      CHARACTER VARYING(100) not null,
    constraint EMPLOYES_ROLES_PK
        primary key (ID_ROLE, ID_UTILISATEUR),
    constraint FK_EMPLOYES_ROLES_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade,
    constraint FK_ER_EMPLOYE
        foreign key (ID_UTILISATEUR) references PUBLIC.EMPLOYES
            on delete cascade
);

create index PUBLIC.IDX_EMPLOYES_ROLES_TENANT_ID
    on PUBLIC.EMPLOYES_ROLES (TENANT_ID);

create table PUBLIC.ETAT_ACHAT
(
    ID          INTEGER auto_increment
        primary key,
    LIBELLE     CHARACTER VARYING(50)  not null,
    DESCRIPTION CHARACTER VARYING,
    TENANT_ID   CHARACTER VARYING(100) not null,
    constraint FK_ETAT_ACHAT_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade
);

create index PUBLIC.IDX_ETAT_ACHAT_TENANT_ID
    on PUBLIC.ETAT_ACHAT (TENANT_ID);

create table PUBLIC.ETAT_FACTURE
(
    ID          INTEGER auto_increment
        primary key,
    LIBELLE     CHARACTER VARYING(50)  not null,
    DESCRIPTION CHARACTER VARYING,
    TENANT_ID   CHARACTER VARYING(100) not null,
    constraint FK_ETAT_FACTURE_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade
);

create index PUBLIC.IDX_ETAT_FACTURE_TENANT_ID
    on PUBLIC.ETAT_FACTURE (TENANT_ID);

create table PUBLIC.ETAT_PAIEMENT
(
    ID          INTEGER auto_increment
        primary key,
    LIBELLE     CHARACTER VARYING(50)  not null,
    DESCRIPTION CHARACTER VARYING,
    TENANT_ID   CHARACTER VARYING(100) not null,
    constraint FK_ETAT_PAIEMENT_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade
);

create index PUBLIC.IDX_ETAT_PAIEMENT_TENANT_ID
    on PUBLIC.ETAT_PAIEMENT (TENANT_ID);

create table PUBLIC.ETAT_PANIER
(
    ID          INTEGER auto_increment
        primary key,
    LIBELLE     CHARACTER VARYING(50)  not null,
    DESCRIPTION CHARACTER VARYING,
    TENANT_ID   CHARACTER VARYING(100) not null,
    constraint FK_ETAT_PANIER_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade
);

create index PUBLIC.IDX_ETAT_PANIER_TENANT_ID
    on PUBLIC.ETAT_PANIER (TENANT_ID);

create table PUBLIC.ETAT_VENTE
(
    ID          INTEGER auto_increment
        primary key,
    LIBELLE     CHARACTER VARYING(50)  not null,
    DESCRIPTION CHARACTER VARYING,
    TENANT_ID   CHARACTER VARYING(100) not null,
    constraint FK_ETAT_VENTE_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade
);

create index PUBLIC.IDX_ETAT_VENTE_TENANT_ID
    on PUBLIC.ETAT_VENTE (TENANT_ID);

create table PUBLIC.FOURNISSEURS
(
    ID         INTEGER auto_increment
        primary key,
    NOM        CHARACTER VARYING(50)  not null,
    PRENOM     CHARACTER VARYING(50)  not null,
    EMAIL      CHARACTER VARYING(100),
    TELEPHONE  CHARACTER VARYING(20),
    ADRESSE    CHARACTER VARYING,
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    TENANT_ID  CHARACTER VARYING(100) not null,
    constraint FK_FOURNISSEUR_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade
);

create index PUBLIC.IDX_FOURNISSEURS_TENANT_ID
    on PUBLIC.FOURNISSEURS (TENANT_ID);

create table PUBLIC.MOMO_TOKEN
(
    ID           INTEGER auto_increment
        primary key,
    ACCESS_TOKEN CHARACTER VARYING                      not null,
    TOKEN_TYPE   CHARACTER VARYING(20) default 'Bearer' not null,
    EXPIRES_AT   TIMESTAMP                              not null,
    CREATED_AT   TIMESTAMP             default CURRENT_TIMESTAMP,
    TENANT_ID    CHARACTER VARYING(100)                 not null,
    constraint FK_MOMO_TOKEN_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade
);

create table PUBLIC.PANIER
(
    ID         INTEGER auto_increment
        primary key,
    ID_EMPLOYE BIGINT                 not null,
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    ID_ETAT    INTEGER   default 1    not null,
    TENANT_ID  CHARACTER VARYING(100) not null,
    constraint FK_PANIER_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade,
    constraint PANIER_EMPLOYE_FK
        foreign key (ID_EMPLOYE) references PUBLIC.EMPLOYES
            on delete cascade,
    constraint PANIER_ETAT_FK
        foreign key (ID_ETAT) references PUBLIC.ETAT_PANIER
);

create index PUBLIC.IDX_PANIER_TENANT_ID
    on PUBLIC.PANIER (TENANT_ID);

create table PUBLIC.PAYMENT_TRANSACTION
(
    ID           INTEGER auto_increment
        primary key,
    CLIENT_PHONE CHARACTER VARYING(20)  not null,
    AMOUNT       NUMERIC(12, 2)         not null,
    STATUS       CHARACTER VARYING(20) default 'PENDING',
    TIMESTAMP    TIMESTAMP             default CURRENT_TIMESTAMP,
    TENANT_ID    CHARACTER VARYING(100) not null,
    constraint FK_PAY_TRANSAC_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade
);

create table PUBLIC.PRODUITS
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
    QR_CODE        BINARY VARYING,
    EAN13          CHARACTER VARYING(13),
    CATEGORIE_ID   INTEGER,
    ACTIF          BOOLEAN   default FALSE not null,
    TENANT_ID      CHARACTER VARYING(100)  not null,
    DEVISE_ID      BIGINT    default 1     not null,
    constraint PRODUITS_UK
        unique (TENANT_ID, EAN13),
    constraint FK_PRODUIT_DEVISE
        foreign key (DEVISE_ID) references PUBLIC.DEVISE,
    constraint FK_PRODUIT_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade,
    constraint PRODUITS_CAT_FK
        foreign key (CATEGORIE_ID) references PUBLIC.CATEGORIE
            on delete set null,
    check ("PRIX_ACHAT" >= CAST(0 AS NUMERIC(1))),
    check ("PRIX_VENTE" >= "PRIX_ACHAT"),
    check ("SEUIL_LOT" >= 0)
);

create table PUBLIC.LIGNES_ACHATS
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
    TENANT_ID       CHARACTER VARYING(100) not null,
    constraint FK_LIGNES_ACHAT_ACHAT
        foreign key (ACHAT_ID) references PUBLIC.ACHATS
            on delete cascade,
    constraint FK_LIGNES_ACHAT_PRODUIT
        foreign key (PRODUIT_ID) references PUBLIC.PRODUITS
            on delete cascade,
    constraint FK_LIGNES_ACHAT_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade,
    constraint CHK_LOT_PROD
        check (("PRODUIT_ID" IS NOT NULL)
            OR (("LOT_ID" IS NOT NULL)
                AND ("PRODUIT_ID" <> "LOT_ID"))),
    check ("PRIX_ACHAT" >= CAST(0 AS NUMERIC(1))),
    check ("QUANTITE" > 0)
);

create index PUBLIC.IDX_LIGNES_ACHATS_TENANT_ID
    on PUBLIC.LIGNES_ACHATS (TENANT_ID);

create table PUBLIC.MOUVEMENT_STOCK
(
    ID                   INTEGER auto_increment
        primary key,
    REFERENCE            CHARACTER VARYING(50),
    PRODUIT_ID           INTEGER                not null,
    QUANTITE             INTEGER                not null,
    TYPE_MOUVEMENT_ID    INTEGER                not null,
    DATE_MOUVEMENT       TIMESTAMP default CURRENT_TIMESTAMP,
    COMMENTAIRE          CHARACTER VARYING,
    CREATED_AT           TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT           TIMESTAMP default CURRENT_TIMESTAMP,
    ID_EVENEMENT_ORIGINE INTEGER                not null,
    ID_LIGNE_ORIGINE     INTEGER                not null,
    TENANT_ID            CHARACTER VARYING(100) not null,
    constraint FK_MOUVEMENT_STOCK_PROD
        foreign key (PRODUIT_ID) references PUBLIC.PRODUITS,
    constraint FK_MOUVEMENT_STOCK_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade,
    check ("QUANTITE" > 0)
);

create index PUBLIC.IDX_MOUVEMENT_STOCK_TENANT_ID
    on PUBLIC.MOUVEMENT_STOCK (TENANT_ID);

create index PUBLIC.IDX_PRODUITS_TENANT_ID
    on PUBLIC.PRODUITS (TENANT_ID);

create table PUBLIC.TYPES_MOUVEMENT_STOCK
(
    ID             INTEGER auto_increment,
    CODE           CHARACTER VARYING(50)  not null,
    NOM            CHARACTER VARYING(50)  not null,
    DESCRIPTION    CHARACTER VARYING,
    DATE_CREATION  TIMESTAMP default CURRENT_TIMESTAMP,
    TYPE_MOUVEMENT CHARACTER VARYING(50),
    TENANT_ID      CHARACTER VARYING(100) not null,
    constraint PK_TMS
        primary key (ID),
    constraint UK_TMS
        unique (TENANT_ID, CODE)
);

create table PUBLIC.UNITE_VENTE
(
    ID        INTEGER auto_increment
        primary key,
    CODE      CHARACTER VARYING(255) not null,
    LIBELLE   CHARACTER VARYING(255) not null,
    TENANT_ID CHARACTER VARYING(100) not null,
    constraint UNITE_VENTE_UK
        unique (TENANT_ID, ID, CODE),
    constraint FK_UNITE_VENTE_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade
);

create table PUBLIC.FORMAT_VENTE
(
    ID                  INTEGER auto_increment
        primary key,
    PRODUIT_ID          BIGINT                 not null,
    UNITE_VENTE_ID      BIGINT                 not null,
    LIBELLE_FORMAT      CHARACTER VARYING(255) not null,
    QUANTITE_PAR_FORMAT INTEGER                not null,
    PRIX_VENTE          NUMERIC(19, 2)         not null,
    TENANT_ID           CHARACTER VARYING(100) not null,
    constraint FK_FORMAT_VENTE_PRODUIT
        foreign key (PRODUIT_ID) references PUBLIC.PRODUITS,
    constraint FK_FORMAT_VENTE_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade,
    constraint FK_FORMAT_VENTE_UNITE
        foreign key (UNITE_VENTE_ID) references PUBLIC.UNITE_VENTE,
    constraint CHK_PRIX_POSITIF
        check ("PRIX_VENTE" > CAST(0 AS NUMERIC(1))),
    constraint CHK_QUANTITE_POSITIVE
        check ("QUANTITE_PAR_FORMAT" > 0)
);

create table PUBLIC.PANIER_PRODUIT
(
    ID              INTEGER auto_increment
        primary key,
    ID_PANIER       INTEGER                not null,
    ID_PRODUIT      INTEGER                not null,
    QUANTITE        INTEGER                not null,
    PRIX_VENTE      NUMERIC(10, 2),
    TENANT_ID       CHARACTER VARYING(100) not null,
    FORMAT_VENTE_ID INTEGER default 1      not null,
    constraint PANIER_PRODUIT_FORMAT_VENTE_ID_FK
        foreign key (FORMAT_VENTE_ID) references PUBLIC.FORMAT_VENTE,
    check ("QUANTITE" > 0)
);

create table PUBLIC.VENTES
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
    TENANT_ID     CHARACTER VARYING(100) not null
);

create table PUBLIC.FACTURES
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
    TENANT_ID       CHARACTER VARYING(100)              not null,
    constraint FK_FACTURES_ETAT
        foreign key (ETAT_ID) references PUBLIC.ETAT_FACTURE,
    constraint FK_FACTURES_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade,
    constraint FK_FACTURES_VENTE
        foreign key (VENTE_ID) references PUBLIC.VENTES
            on delete cascade,
    check ("MONTANT_TOTAL" >= CAST(0 AS NUMERIC(1)))
);

create index PUBLIC.IDX_FACTURES_TENANT_ID
    on PUBLIC.FACTURES (TENANT_ID);

create table PUBLIC.LIGNES_VENTES
(
    ID_LIGNES_VENTES    INTEGER auto_increment
        primary key,
    VENTE_ID            INTEGER                not null,
    PRODUIT_ID          INTEGER,
    QUANTITE            INTEGER                not null,
    PRIX_VENTE_UNITAIRE NUMERIC(10, 2)         not null,
    ACTIF               BOOLEAN default TRUE   not null,
    TENANT_ID           CHARACTER VARYING(100) not null,
    FORMAT_VENTE_ID     INTEGER default 1,
    constraint FK_LIGNES_VENTES_PRODUIT
        foreign key (PRODUIT_ID) references PUBLIC.PRODUITS
            on delete cascade,
    constraint FK_LIGNES_VENTES_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade,
    constraint FK_LIGNES_VENTES_VENTE
        foreign key (VENTE_ID) references PUBLIC.VENTES
            on delete cascade,
    constraint LIGNES_VENTES_FORMAT_VENTE_ID_FK
        foreign key (FORMAT_VENTE_ID) references PUBLIC.FORMAT_VENTE,
    check ("QUANTITE" > 0),
    check ("PRIX_VENTE_UNITAIRE" > CAST(0 AS NUMERIC(1)))
);

create index PUBLIC.IDX_LIGNES_VENTES_TENANT_ID
    on PUBLIC.LIGNES_VENTES (TENANT_ID);

create table PUBLIC.PAIEMENTS
(
    ID_PAIEMENT   INTEGER auto_increment
        primary key,
    VENTE_ID      INTEGER                not null,
    MONTANT_PAYE  NUMERIC(10, 2)         not null,
    MODE_PAIEMENT CHARACTER VARYING(50),
    REFERENCE     CHARACTER VARYING(100),
    CREATED_AT    TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT    TIMESTAMP,
    ETAT_ID       INTEGER   default 1    not null,
    TENANT_ID     CHARACTER VARYING(100) not null,
    constraint FK_PAIEMENTS_ETAT
        foreign key (ETAT_ID) references PUBLIC.ETAT_PAIEMENT,
    constraint FK_PAIEMENTS_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade,
    constraint FK_PAIEMENTS_VENTE
        foreign key (VENTE_ID) references PUBLIC.VENTES
            on delete cascade
);

create index PUBLIC.IDX_PAIEMENTS_TENANT_ID
    on PUBLIC.PAIEMENTS (TENANT_ID);

create table PUBLIC.PAIMENT
(
    ID_PAIEMENT  INTEGER auto_increment
        primary key,
    VENTE_ID     INTEGER                not null,
    MONTANT      DECFLOAT,
    MODEPAIEMENT CHARACTER VARYING(100),
    CREATEDAT    TIMESTAMP,
    UPDATEDAT    DATE,
    TENANT_ID    CHARACTER VARYING(100) not null,
    constraint FK_PAIMENT_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade,
    constraint FK_PAIMENT_VENTE
        foreign key (VENTE_ID) references PUBLIC.VENTES
);

create index PUBLIC.IDX_PAIMENT_TENANT_ID
    on PUBLIC.PAIMENT (TENANT_ID);

create table PUBLIC.RETOUR
(
    ID_RETOUR INTEGER auto_increment
        primary key,
    LIGNE_ID  INTEGER                not null,
    VENTE_ID  INTEGER                not null,
    QUANTITE  INTEGER,
    RAISON    CHARACTER VARYING,
    CREATEDAT DATE,
    UPDATEDAT DATE,
    TENANT_ID CHARACTER VARYING(100) not null,
    constraint FK_RETOUR_LIGNE
        foreign key (LIGNE_ID) references PUBLIC.LIGNES_VENTES,
    constraint FK_RETOUR_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade,
    constraint FK_RETOUR_VENTE
        foreign key (VENTE_ID) references PUBLIC.VENTES
);

comment on column PUBLIC.RETOUR.LIGNE_ID is 'ligne_vente';

create index PUBLIC.IDX_RETOUR_TENANT_ID
    on PUBLIC.RETOUR (TENANT_ID);

create table PUBLIC.RETOURS
(
    ID_RETOUR  INTEGER auto_increment
        primary key,
    VENTE_ID   INTEGER                not null,
    PRODUIT_ID INTEGER                not null,
    QUANTITE   INTEGER                not null,
    RAISON     CHARACTER VARYING      not null,
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    TENANT_ID  CHARACTER VARYING(100) not null,
    constraint FK_RETOURS_PRODUIT
        foreign key (PRODUIT_ID) references PUBLIC.PRODUITS
            on delete cascade,
    constraint FK_RETOURS_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade,
    constraint FK_RETOURS_VENTE
        foreign key (VENTE_ID) references PUBLIC.VENTES
            on delete cascade,
    check ("QUANTITE" > 0)
);

create index PUBLIC.IDX_RETOURS_TENANT_ID
    on PUBLIC.RETOURS (TENANT_ID);

create table PUBLIC.WALLET
(
    ID        INTEGER auto_increment
        primary key,
    BALANCE   NUMERIC(12, 2) default 0,
    TENANT_ID CHARACTER VARYING(100) not null
        unique,
    constraint FK_WALLET_TENANT
        foreign key (TENANT_ID) references PUBLIC.TENANT
            on delete cascade
);

create table PUBLIC."flyway_schema_history"
(
    "installed_rank" INTEGER                             not null,
    "version"        CHARACTER VARYING(50),
    "description"    CHARACTER VARYING(200)              not null,
    "type"           CHARACTER VARYING(20)               not null,
    "script"         CHARACTER VARYING(1000)             not null,
    "checksum"       INTEGER,
    "installed_by"   CHARACTER VARYING(100)              not null,
    "installed_on"   TIMESTAMP default CURRENT_TIMESTAMP not null,
    "execution_time" INTEGER                             not null,
    "success"        BOOLEAN                             not null,
    constraint "flyway_schema_history_pk"
        primary key ("installed_rank")
);

create index PUBLIC."flyway_schema_history_s_idx"
    on PUBLIC."flyway_schema_history" ("success");

