-- auto-generated definition
create table ENTREES
(
    ID_ENTREE      INTEGER auto_increment
        primary key,
    ID_PRODUIT     INTEGER        not null
        references PRODUITS
            on delete cascade,
    ID_FOURNISSEUR INTEGER        not null
        references FOURNISSEURS
            on delete cascade,
    QUANTITÉ       INTEGER        not null,
    DATE_ENTREE    TIMESTAMP default CURRENT_TIMESTAMP,
    PRIX_ACHAT     NUMERIC(10, 2) not null,
    ID_UTILISATEUR INTEGER,
    constraint ENTREES_UTILISATEURS_ID_UTILISATEUR_FK
        foreign key (ID_UTILISATEUR) references UTILISATEURS
);

create table MOUVEMENTS_STOCK1
(
    ID_MOUVEMENT   INTEGER auto_increment
        primary key,
    ID_PRODUIT     INTEGER                   not null
        references PRODUITS
            on delete cascade,
    TYPE_MOUVEMENT ENUM ('entree', 'sortie') not null,
    QUANTITÉ       INTEGER                   not null,
    DATE_MOUVEMENT TIMESTAMP default CURRENT_TIMESTAMP,
    ID_UTILISATEUR INTEGER                   not null
        references UTILISATEURS
            on delete cascade
);

create table SORTIES
(
    ID_SORTIE      INTEGER auto_increment
        primary key,
    ID_PRODUIT     INTEGER        not null
        references PRODUITS
            on delete cascade,
    QUANTITÉ       INTEGER        not null,
    DATE_SORTIE    TIMESTAMP default CURRENT_TIMESTAMP,
    PRIX_VENTE     NUMERIC(10, 2) not null,
    CLIENT         CHARACTER VARYING(255),
    ID_UTILISATEUR INTEGER,
    constraint SORTIES_UTILISATEURS_ID_UTILISATEUR_FK
        foreign key (ID_UTILISATEUR) references UTILISATEURS
);


create table FOURNISSEURS
(
    ID_FOURNISSEUR INTEGER auto_increment
        primary key,
    NOM            CHARACTER VARYING(255) not null,
    CONTACT        CHARACTER VARYING(255),
    ADRESSE        CHARACTER VARYING
);

create table ROLE
(
    ID_ROLE     INTEGER auto_increment,
    NOM         CHARACTER VARYING(255) not null,
    DESCRIPTION CHARACTER VARYING(1022),
    primary key (ID_ROLE)
);

create table PRODUITS
(
    ID_PRODUIT    INTEGER auto_increment
        primary key,
    NOM           CHARACTER VARYING(255) not null,
    DESCRIPTION   CHARACTER VARYING,
    PRIX_UNITAIRE NUMERIC(10, 2)         not null,
    STOCK_INITIAL INTEGER   default 0,
    DATE_CREATION TIMESTAMP default CURRENT_TIMESTAMP
);

create table UTILISATEURS
(
    ID_UTILISATEUR INTEGER auto_increment
        primary key,
    NOM            CHARACTER VARYING(255) not null,
    EMAIL          CHARACTER VARYING(255) not null
        unique,
    DATE_CREATION  TIMESTAMP default CURRENT_TIMESTAMP,
    ID_ROLE        INTEGER,
    constraint UTILISATEURS_ROLE_ID_ROLE_FK
        foreign key (ID_ROLE) references ROLE
);

create table CLIENTS
(
    ID_CLIENT    INTEGER auto_increment
        primary key,
    FIRSTNAME           CHARACTER VARYING(255) not null ,
    LASTNAME           CHARACTER VARYING(255) ,
    EMAIL           CHARACTER VARYING(255) ,
    PHONE           CHARACTER VARYING(255) ,
    ADDRESS           CHARACTER VARYING(255) ,
    CITY           CHARACTER VARYING(255) ,
    STATE           CHARACTER VARYING(255) ,
    ZIP           CHARACTER VARYING(255) ,
    COUNTRY           CHARACTER VARYING(255)
);

