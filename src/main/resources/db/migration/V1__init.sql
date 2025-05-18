-- we don't know how to generate root <with-no-name> (class Root) :(
-- Table des tenants
CREATE TABLE tenant (
                        id VARCHAR(100)  PRIMARY KEY,         -- ex: 'client1', 'client2'
                        name VARCHAR(255) NOT NULL,
                        is_active BOOLEAN DEFAULT TRUE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table AUTHORITY
(
    ID  INTEGER auto_increment
        primary key,
    NOM CHARACTER VARYING(100) not null,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_authority_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE

);

create table CATEGORIE
(
    CATEGORIE_ID INTEGER auto_increment
        primary key,
    NOM          CHARACTER VARYING(255) not null,
    DESCRIPTION  CHARACTER VARYING,
    CREATED_AT   TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT   TIMESTAMP default CURRENT_TIMESTAMP,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_categorie_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE
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
    ACTIF      BOOLEAN   default TRUE not null,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_client_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE
);
alter table CLIENTS
    add constraint CLIENTS_pk
        unique (TENANT_ID, NOM);

create table EMPLOYES
(
    ID_EMPLOYE BIGINT auto_increment
        primary key,
    NOM        CHARACTER VARYING(255) not null  ,
    PRENOM     CHARACTER VARYING(255) not null,
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    PASSWORD   CHARACTER VARYING(255) not null,
    ACTIF      BOOLEAN   default TRUE not null,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_employes_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE
);
alter table EMPLOYES
    add constraint EMPLOYES_PK
        unique (TENANT_ID, NOM);

create table ACHATS
(
    ID_ACHAT      INTEGER auto_increment
        primary key,
    EMPLOYE_ID    INTEGER                not null,
    MONTANT_TOTAL NUMERIC(10, 2)         not null,
    CREATED_AT    TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT    TIMESTAMP default CURRENT_TIMESTAMP,
    ACTIF         BOOLEAN   default TRUE not null,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_achat_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE,
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
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_EMPLOYES_ROLES_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE,
    constraint EMPLOYES_ROLES_PK
        primary key (ID_ROLE, ID_UTILISATEUR),
    constraint EMPLOYES_ROLES_EMPLOYES_ID_EMPLOYE_FK
        foreign key (ID_UTILISATEUR) references EMPLOYES,
    constraint UTILISATEURS_ROLES_ID_UTILISATEUR_FKEY
        foreign key (ID_UTILISATEUR) references EMPLOYES
            on delete cascade
);



create table ETAT_FACTURE
(
    ID          INTEGER auto_increment
        primary key,
    LIBELLE     CHARACTER VARYING(50) not null,
    DESCRIPTION CHARACTER VARYING,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_etat_facture_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE
);

create table ETAT_PAIEMENT
(
    ID          INTEGER auto_increment
        primary key,
    LIBELLE     CHARACTER VARYING(50) not null,
    DESCRIPTION CHARACTER VARYING,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_etat_paiement_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE
);

create table ETAT_PANIER
(
    ID          INTEGER auto_increment
        primary key,
    LIBELLE     CHARACTER VARYING(50) not null,
    DESCRIPTION CHARACTER VARYING,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_etat_panier_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE
);

create table ETAT_VENTE
(
    ID          INTEGER auto_increment
        primary key,
    LIBELLE     CHARACTER VARYING(50) not null,
    DESCRIPTION CHARACTER VARYING,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_etat_vente_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE
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
    UPDATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_fournisseur_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE
);

create table PANIER
(
    ID         INTEGER auto_increment
        primary key,
    ID_EMPLOYE INTEGER             not null,
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    ID_ETAT    INTEGER   default 1 not null,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_panier_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE,
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
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_produit_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE,
    constraint PRODUITS_CATEGORIE_CATEGORIE_ID_FK
        foreign key (CATEGORIE_ID) references CATEGORIE,
    check ("PRIX_ACHAT" >= CAST(0 AS NUMERIC(1))),
    check ("PRIX_VENTE" >= "PRIX_ACHAT"),
    check ("SEUIL_LOT" >= 0)
);

alter table PRODUITS
    add constraint PRODUITS_pk
        unique (TENANT_ID, EAN13);


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
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_lignes_achat_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE,
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
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_mouvement_stock_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE,
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
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_panier_produit_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE,
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
    DESCRIPTION CHARACTER VARYING,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_roles_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE
);


create table ROLE_AUTHORITY
(
    ID_ROLE      INTEGER not null,
    ID_AUTHORITY INTEGER not null,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_role_authority_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE,
    constraint ROLE_AUTHORITY_AUTHORITY_ID_FK
        foreign key (ID_AUTHORITY) references AUTHORITY,
    constraint ROLE_AUTHORITY_ROLES_ID_ROLE_FK
        foreign key (ID_ROLE) references ROLES
);
alter table ROLE_AUTHORITY
    add constraint ROLE_AUTHORITY_pk
        unique ( ID_ROLE, ID_AUTHORITY, TENANT_ID);

create table ROLE_EMPLOYE
(
    ID_ROLE    INTEGER not null,
    ID_EMPLOYE INTEGER not null,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_role_employe_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE,
    constraint ROLE_EMPLOYE_ID_EMPLOYE_FK
        foreign key (ID_EMPLOYE) references EMPLOYES,
    constraint ROLE_EMPLOYE_ROLES_ID_ROLE_FK
        foreign key (ID_ROLE) references ROLES
);
alter table ROLE_EMPLOYE
    add constraint ROLE_EMPLOYE_pk
        unique ( ID_ROLE, ID_EMPLOYE, TENANT_ID);

create table TARIF_ACHAT
(
    ID         INTEGER auto_increment,
    ID_PRODUIT INTEGER        not null,
    PRIX_ACHAT NUMERIC(10, 2) not null,
    CREATED_AT TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_tarif_achat_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE,
    constraint TARIF_ACHAT_PK
        primary key (ID),
    constraint TARIF_ACHAT_PRODUITS_ID_PRODUIT_FK
        foreign key (ID_PRODUIT) references PRODUITS
);

alter table TARIF_ACHAT
    add constraint TARIF_ACHAT_UK
        unique (tenant_id, ID_PRODUIT);

create table TYPES_MOUVEMENT_STOCK
(
    ID             INTEGER auto_increment,
    CODE           CHARACTER VARYING(50) not null,
    NOM            CHARACTER VARYING(50) not null,
    DESCRIPTION    CHARACTER VARYING,
    DATE_CREATION  TIMESTAMP default CURRENT_TIMESTAMP,
    TYPE_MOUVEMENT CHARACTER VARYING(50),
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_types_mouvement_stock_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE
);
alter table TYPES_MOUVEMENT_STOCK
    add constraint TYPES_MOUVEMENT_STOCK_pk
        unique (TENANT_ID, CODE);

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
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_ventes_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE,
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
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_factures_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE,
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
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_lignes_ventes_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE,
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
    VENTE_ID      INTEGER        not null,
    MONTANT_PAYE  NUMERIC(10, 2) not null,
    MODE_PAIEMENT CHARACTER VARYING(50),
    REFERENCE     CHARACTER VARYING(100),
    CREATED_AT    TIMESTAMP default CURRENT_TIMESTAMP,
    UPDATED_AT    TIMESTAMP,
    ETAT_ID       INTEGER   default 1 not null,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_paiement_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE,
    constraint PAIEMENTS_ETAT_PAIEMENT_ID_FK
        foreign key (ETAT_ID) references ETAT_PAIEMENT,
    constraint PAIEMENTS_VENTES_ID_VENTES_FK
        foreign key (VENTE_ID) references VENTES
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
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_retours_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE,
    constraint RETOURS_PRODUIT_ID_FKEY
        foreign key (PRODUIT_ID) references PRODUITS
            on delete cascade,
    constraint RETOURS_VENTE_ID_FKEY
        foreign key (VENTE_ID) references VENTES
            on delete cascade,
    check ("QUANTITE" > 0)
);

create table PUBLIC.RETOUR
(
    ID_RETOUR INTEGER auto_increment,
    LIGNE_ID  INTEGER not null,
    VENTE_ID  INTEGER not null,
    quantite  integer,
    RAISON    CHARACTER VARYING,
    CREATEDAT DATE,
    UPDATEDAT DATE,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_retour_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE,

    constraint RETOUR_PK
        primary key (ID_RETOUR),
    constraint RETOUR_LIGNES_VENTES_ID_LIGNES_VENTES_FK
        foreign key (LIGNE_ID) references LIGNES_VENTES,
    constraint RETOUR_VENTES_ID_VENTES_FK
        foreign key (VENTE_ID) references VENTES
);

comment on column PUBLIC.RETOUR.LIGNE_ID is 'ligne_vente';


create table PUBLIC.PAIMENT
(
    ID_PAIEMENT  INTEGER auto_increment,
    VENTE_ID     INTEGER not null,
    MONTANT      NUMERIC,
    MODEPAIEMENT varchar(100),
    CREATEDAT    TIMESTAMP,
    UPDATEDAT    DATE,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_paiment_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE,

    constraint PAIMENT_PK
        primary key (ID_PAIEMENT),
    constraint PAIMENT_VENTES_ID_VENTES_FK
        foreign key (VENTE_ID) references VENTES
);


create table PUBLIC.ETAT_ACHAT
(
    ID          INTEGER auto_increment
        primary key,
    LIBELLE     varchar(50) not null,
    DESCRIPTION text,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_etat_achat_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE

);


CREATE OR REPLACE VIEW vue_stock_courant AS
SELECT
    p.id_produit,
    p.nom,
    p.stock_initial,
    (
        (p.stock_initial + COALESCE(
                (SELECT SUM(ms.quantite)
                 FROM mouvement_stock ms
                          JOIN types_mouvement_stock tms
                               ON ms.type_mouvement_id = tms.id
                 WHERE ms.produit_id = p.id_produit
                   AND tms.type_mouvement = 'ENTREE'
                   AND ms.tenant_id = p.tenant_id), 0)
            ) -
        COALESCE(
                (SELECT SUM(ms.quantite)
                 FROM mouvement_stock ms
                          JOIN types_mouvement_stock tms
                               ON ms.type_mouvement_id = tms.id
                 WHERE ms.produit_id = p.id_produit
                   AND tms.type_mouvement = 'SORTIE'
                   AND ms.tenant_id = p.tenant_id), 0)
        ) AS stock_courant,
    p.tenant_id
FROM produits p;


-- Ajouter un index sur tenant_id pour chaque table ayant un tenant_id

CREATE INDEX idx_achats_tenant_id ON ACHATS(tenant_id);
CREATE INDEX idx_authority_tenant_id ON AUTHORITY(tenant_id);
CREATE INDEX idx_categorie_tenant_id ON CATEGORIE(tenant_id);
CREATE INDEX idx_clients_tenant_id ON CLIENTS(tenant_id);
CREATE INDEX idx_employees_tenant_id ON EMPLOYES(tenant_id);
CREATE INDEX idx_employees_roles_tenant_id ON EMPLOYES_ROLES(tenant_id);
CREATE INDEX idx_etat_achat_tenant_id ON ETAT_ACHAT(tenant_id);
CREATE INDEX idx_etat_facture_tenant_id ON ETAT_FACTURE(tenant_id);
CREATE INDEX idx_etat_paiement_tenant_id ON ETAT_PAIEMENT(tenant_id);
CREATE INDEX idx_etat_panier_tenant_id ON ETAT_PANIER(tenant_id);
CREATE INDEX idx_etat_vente_tenant_id ON ETAT_VENTE(tenant_id);
CREATE INDEX idx_factures_tenant_id ON FACTURES(tenant_id);
CREATE INDEX idx_fournisseurs_tenant_id ON FOURNISSEURS(tenant_id);
CREATE INDEX idx_lignes_achats_tenant_id ON LIGNES_ACHATS(tenant_id);
CREATE INDEX idx_lignes_ventes_tenant_id ON LIGNES_VENTES(tenant_id);
CREATE INDEX idx_mouvement_stock_tenant_id ON MOUVEMENT_STOCK(tenant_id);
CREATE INDEX idx_paiment_tenant_id ON PAIMENT(tenant_id);
CREATE INDEX idx_paiements_tenant_id ON PAIEMENTS(tenant_id);
CREATE INDEX idx_panier_tenant_id ON PANIER(tenant_id);
CREATE INDEX idx_panier_produit_tenant_id ON PANIER_PRODUIT(tenant_id);
CREATE INDEX idx_produits_tenant_id ON PRODUITS(tenant_id);
CREATE INDEX idx_retour_tenant_id ON RETOUR(tenant_id);
CREATE INDEX idx_retours_tenant_id ON RETOURS(tenant_id);
CREATE INDEX idx_roles_tenant_id ON ROLES(tenant_id);
CREATE INDEX idx_role_authority_tenant_id ON ROLE_AUTHORITY(tenant_id);
CREATE INDEX idx_role_employe_tenant_id ON ROLE_EMPLOYE(tenant_id);
CREATE INDEX idx_tarif_achat_tenant_id ON TARIF_ACHAT(tenant_id);
CREATE INDEX idx_types_mouvement_stock_tenant_id ON TYPES_MOUVEMENT_STOCK(tenant_id);
CREATE INDEX idx_ventes_tenant_id ON VENTES(tenant_id);

