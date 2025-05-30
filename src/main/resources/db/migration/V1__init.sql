-- we don't know how to generate root <with-no-name> (class Root) :(
-- Table des tenants
/* Equivalent PostgreSQL DDL for multi-tenant schema */

-- Table des tenants
drop table if exists tenant cascade;
create table tenant (
                        id varchar(100) primary key,    -- ex: 'client1', 'client2'
                        name varchar(255) not null,
                        is_active boolean default true,
                        created_at timestamp default current_timestamp
);

-- Autorités
drop table if exists authority cascade;
create table authority (
                           id serial primary key,
                           nom varchar(100) not null,
                           tenant_id varchar(100) not null,
                           constraint fk_authority_tenant foreign key (tenant_id)
                               references tenant(id) on delete cascade
);

-- Catégories
drop table if exists categorie cascade;
create table categorie (
                           categorie_id serial primary key,
                           nom varchar(255) not null,
                           description varchar,
                           created_at timestamp default current_timestamp,
                           updated_at timestamp default current_timestamp,
                           tenant_id varchar(100) not null,
                           constraint fk_categorie_tenant foreign key (tenant_id)
                               references tenant(id) on delete cascade
);

-- Clients
drop table if exists clients cascade;
create table clients (
                         id_client serial primary key,
                         nom varchar(50) not null,
                         prenom varchar(50) not null,
                         email varchar(100),
                         telephone varchar(20) not null,
                         adresse varchar,
                         created_at timestamp default current_timestamp,
                         updated_at timestamp default current_timestamp,
                         actif boolean default true not null,
                         tenant_id varchar(100) not null,
                         constraint fk_client_tenant foreign key (tenant_id)
                             references tenant(id) on delete cascade,
                         constraint clients_uk unique (tenant_id, nom)
);

-- Employés
drop table if exists employes cascade;
create table employes (
                          id_employe bigserial primary key,
                          nom varchar(255) not null,
                          prenom varchar(255) not null,
                          created_at timestamp default current_timestamp,
                          updated_at timestamp default current_timestamp,
                          password varchar(255) not null,
                          actif boolean default true not null,
                          tenant_id varchar(100) not null,
                          constraint fk_employes_tenant foreign key (tenant_id)
                              references tenant(id) on delete cascade,
                          constraint employes_uk unique (tenant_id, nom)
);

-- Achats
drop table if exists achats cascade;
create table achats (
                        id_achat serial primary key,
                        employe_id bigint,
                        montant_total numeric(10,2) not null check (montant_total >= 0),
                        created_at timestamp default current_timestamp,
                        updated_at timestamp default current_timestamp,
                        actif boolean default true not null,
                        tenant_id varchar(100) not null,
                        constraint fk_achat_tenant foreign key (tenant_id)
                            references tenant(id) on delete cascade,
                        constraint fk_achats_employe foreign key (employe_id)
                            references employes(id_employe) on delete set null
);

-- Employés_Roles
drop table if exists employes_roles cascade;
create table employes_roles (
                                id_utilisateur bigint not null,
                                id_role bigint not null,
                                tenant_id varchar(100) not null,
                                constraint fk_employes_roles_tenant foreign key (tenant_id)
                                    references tenant(id) on delete cascade,
                                constraint employes_roles_pk primary key (id_role, id_utilisateur)
);
alter table employes_roles
    add constraint fk_er_employe foreign key (id_utilisateur)
        references employes(id_employe) on delete cascade;

-- États de facture
drop table if exists etat_facture cascade;
create table etat_facture (
                              id serial primary key,
                              libelle varchar(50) not null,
                              description varchar,
                              tenant_id varchar(100) not null,
                              constraint fk_etat_facture_tenant foreign key (tenant_id)
                                  references tenant(id) on delete cascade
);

-- États de paiement
drop table if exists etat_paiement cascade;
create table etat_paiement (
                               id serial primary key,
                               libelle varchar(50) not null,
                               description varchar,
                               tenant_id varchar(100) not null,
                               constraint fk_etat_paiement_tenant foreign key (tenant_id)
                                   references tenant(id) on delete cascade
);

-- États de panier
drop table if exists etat_panier cascade;
create table etat_panier (
                             id serial primary key,
                             libelle varchar(50) not null,
                             description varchar,
                             tenant_id varchar(100) not null,
                             constraint fk_etat_panier_tenant foreign key (tenant_id)
                                 references tenant(id) on delete cascade
);

-- États de vente
drop table if exists etat_vente cascade;
create table etat_vente (
                            id serial primary key,
                            libelle varchar(50) not null,
                            description varchar,
                            tenant_id varchar(100) not null,
                            constraint fk_etat_vente_tenant foreign key (tenant_id)
                                references tenant(id) on delete cascade
);

-- Fournisseurs
drop table if exists fournisseurs cascade;
create table fournisseurs (
                              id serial primary key,
                              nom varchar(50) not null,
                              prenom varchar(50) not null,
                              email varchar(100),
                              telephone varchar(20),
                              adresse varchar,
                              created_at timestamp default current_timestamp,
                              updated_at timestamp default current_timestamp,
                              tenant_id varchar(100) not null,
                              constraint fk_fournisseur_tenant foreign key (tenant_id)
                                  references tenant(id) on delete cascade
);

-- Panier
drop table if exists panier cascade;
create table panier (
                        id serial primary key,
                        id_employe bigint not null,
                        created_at timestamp default current_timestamp,
                        id_etat integer not null default 1,
                        tenant_id varchar(100) not null,
                        constraint fk_panier_tenant foreign key (tenant_id)
                            references tenant(id) on delete cascade,
                        constraint panier_etat_fk foreign key (id_etat)
                            references etat_panier(id) on delete restrict,
                        constraint panier_employe_fk foreign key (id_employe)
                            references employes(id_employe) on delete cascade
);

-- Produits
drop table if exists produits cascade;
create table produits (
                          id_produit serial primary key,
                          reference varchar(50),
                          nom varchar(100) not null,
                          description varchar,
                          prix_achat numeric(10,2) check (prix_achat >= 0),
                          prix_vente numeric(10,2) check (prix_vente >= prix_achat),
                          sous_categorie varchar(50),
                          marque varchar(50),
                          unite_mesure varchar(20),
                          seuil_lot integer default 0 check (seuil_lot >= 0),
                          seuil_produit integer default 10,
                          created_at timestamp default current_timestamp,
                          updated_at timestamp default current_timestamp,
                          image varchar,
                          stock_initial integer default 0,
                          qr_code bytea,
                          ean13 varchar(13),
                          categorie_id integer,
                          actif boolean default false not null,
                          tenant_id varchar(100) not null,
                          constraint fk_produit_tenant foreign key (tenant_id)
                              references tenant(id) on delete cascade,
                          constraint produits_cat_fk foreign key (categorie_id)
                              references categorie(categorie_id) on delete set null,
                          constraint produits_uk unique (tenant_id, ean13)
);

-- Lignes achats
drop table if exists lignes_achats cascade;
create table lignes_achats (
                               id_lignes_achat serial primary key,
                               lot_id integer,
                               prix_achat numeric(10,2) not null check (prix_achat >= 0),
                               quantite integer not null check (quantite > 0),
                               created_at timestamp default current_timestamp,
                               updated_at timestamp default current_timestamp,
                               produit_id integer,
                               achat_id integer,
                               actif boolean default true not null,
                               tenant_id varchar(100) not null,
                               constraint fk_lignes_achat_tenant foreign key (tenant_id)
                                   references tenant(id) on delete cascade,
                               constraint fk_lignes_achat_achat foreign key (achat_id)
                                   references achats(id_achat) on delete cascade,
                               constraint fk_lignes_achat_produit foreign key (produit_id)
                                   references produits(id_produit) on delete cascade
);

-- Mouvements de stock
drop table if exists mouvement_stock cascade;
create table mouvement_stock (
                                 id serial primary key,
                                 reference varchar(50),
                                 produit_id integer not null,
                                 quantite integer not null check (quantite > 0),
                                 type_mouvement_id integer not null,
                                 date_mouvement timestamp default current_timestamp,
                                 commentaire varchar,
                                 created_at timestamp default current_timestamp,
                                 updated_at timestamp default current_timestamp,
                                 id_evenement_origine integer not null,
                                 id_ligne_origine integer not null,
                                 tenant_id varchar(100) not null,
                                 constraint fk_mouvement_stock_tenant foreign key (tenant_id)
                                     references tenant(id) on delete cascade,
                                 constraint fk_mouvement_stock_prod foreign key (produit_id)
                                     references produits(id_produit) on delete restrict
);

-- Panier_Produit
drop table if exists panier_produit cascade;
create table panier_produit (
                                id serial primary key,
                                id_panier integer not null,
                                id_produit integer not null,
                                quantite integer not null check (quantite > 0),
                                prix_vente numeric(10,2),
                                tenant_id varchar(100) not null,
                                constraint fk_panier_produit_tenant foreign key (tenant_id)
                                    references tenant(id) on delete cascade,
                                constraint fk_pp_panier foreign key (id_panier)
                                    references panier(id) on delete cascade,
                                constraint fk_pp_produit foreign key (id_produit)
                                    references produits(id_produit) on delete cascade
);

-- Rôles
drop table if exists roles cascade;
create table roles (
                       id_role serial primary key,
                       nom varchar not null,
                       description varchar,
                       tenant_id varchar(100) not null,
                       constraint fk_roles_tenant foreign key (tenant_id)
                           references tenant(id) on delete cascade
);

-- Role_Authority
drop table if exists role_authority cascade;
create table role_authority (
                                id_role integer not null,
                                id_authority integer not null,
                                tenant_id varchar(100) not null,
                                constraint fk_role_authority_tenant foreign key (tenant_id)
                                    references tenant(id) on delete cascade,
                                constraint role_authority_pk primary key (id_role, id_authority, tenant_id),
                                constraint fk_ra_role foreign key (id_role)
                                    references roles(id_role) on delete cascade,
                                constraint fk_ra_authority foreign key (id_authority)
                                    references authority(id) on delete cascade
);

-- Role_Employe
drop table if exists role_employe cascade;
create table role_employe (
                              id_role integer not null,
                              id_employe integer not null,
                              tenant_id varchar(100) not null,
                              constraint fk_role_employe_tenant foreign key (tenant_id)
                                  references tenant(id) on delete cascade,
                              constraint role_employe_pk primary key (id_role, id_employe, tenant_id),
                              constraint fk_re_role foreign key (id_role)
                                  references roles(id_role) on delete cascade,
                              constraint fk_re_employe foreign key (id_employe)
                                  references employes(id_employe) on delete cascade
);

-- Tarif_Achat
drop table if exists tarif_achat cascade;
create table tarif_achat (
                             id serial,
                             id_produit integer not null,
                             prix_achat numeric(10,2) not null,
                             created_at timestamp default current_timestamp,
                             updated_at timestamp,
                             tenant_id varchar(100) not null,
                             constraint pk_tarif_achat primary key (id),
                             constraint fk_tarif_achat_tenant foreign key (tenant_id)
                                 references tenant(id) on delete cascade,
                             constraint uk_tarif_achat unique (tenant_id, id_produit),
                             constraint fk_ta_produit foreign key (id_produit)
                                 references produits(id_produit) on delete cascade
);

-- Types_Mouvement_Stock
drop table if exists types_mouvement_stock cascade;
create table types_mouvement_stock (
                                       id serial,
                                       code varchar(50) not null,
                                       nom varchar(50) not null,
                                       description varchar,
                                       date_creation timestamp default current_timestamp,
                                       type_mouvement varchar(50),
                                       tenant_id varchar(100) not null,
                                       constraint pk_tms primary key (id),
                                       constraint fk_tms_tenant foreign key (tenant_id)
                                           references tenant(id) on delete cascade,
                                       constraint uk_tms unique (tenant_id, code)
);

-- Ventes
drop table if exists ventes cascade;
create table ventes (
                        id_ventes serial primary key,
                        client_id integer not null,
                        date_vente date,
                        montant_total numeric(10,2) not null,
                        created_at timestamp default current_timestamp,
                        updated_at timestamp default current_timestamp,
                        employe_id integer,
                        actif boolean default true not null,
                        etat_id integer not null default 1,
                        tenant_id varchar(100) not null,
                        constraint fk_ventes_tenant foreign key (tenant_id)
                            references tenant(id) on delete cascade,
                        constraint fk_ventes_client foreign key (client_id)
                            references clients(id_client) on delete set null,
                        constraint fk_ventes_employe foreign key (employe_id)
                            references employes(id_employe) on delete restrict,
                        constraint fk_ventes_etat foreign key (etat_id)
                            references etat_vente(id) on delete restrict
);

/* Equivalent PostgreSQL DDL for multi-tenant schema (inclut tables et vues additionnelles) */

-- Schema public is assumed

-- Table des tenants
drop table if exists tenant cascade;
create table tenant (
                        id varchar(100) primary key,    -- ex: 'client1', 'client2'
                        name varchar(255) not null,
                        is_active boolean default true,
                        created_at timestamp default current_timestamp
);

-- Autorités
drop table if exists authority cascade;
create table authority (
                           id serial primary key,
                           nom varchar(100) not null,
                           tenant_id varchar(100) not null,
                           constraint fk_authority_tenant foreign key (tenant_id)
                               references tenant(id) on delete cascade
);

-- Catégories
drop table if exists categorie cascade;
create table categorie (
                           categorie_id serial primary key,
                           nom varchar(255) not null,
                           description varchar,
                           created_at timestamp default current_timestamp,
                           updated_at timestamp default current_timestamp,
                           tenant_id varchar(100) not null,
                           constraint fk_categorie_tenant foreign key (tenant_id)
                               references tenant(id) on delete cascade
);

-- Clients
drop table if exists clients cascade;
create table clients (
                         id_client serial primary key,
                         nom varchar(50) not null,
                         prenom varchar(50) not null,
                         email varchar(100),
                         telephone varchar(20) not null,
                         adresse varchar,
                         created_at timestamp default current_timestamp,
                         updated_at timestamp default current_timestamp,
                         actif boolean default true not null,
                         tenant_id varchar(100) not null,
                         constraint fk_client_tenant foreign key (tenant_id)
                             references tenant(id) on delete cascade,
                         constraint clients_uk unique (tenant_id, nom)
);

-- Employés
drop table if exists employes cascade;
create table employes (
                          id_employe bigserial primary key,
                          nom varchar(255) not null,
                          prenom varchar(255) not null,
                          created_at timestamp default current_timestamp,
                          updated_at timestamp default current_timestamp,
                          password varchar(255) not null,
                          actif boolean default true not null,
                          tenant_id varchar(100) not null,
                          constraint fk_employes_tenant foreign key (tenant_id)
                              references tenant(id) on delete cascade,
                          constraint employes_uk unique (tenant_id, nom)
);

-- Achats
drop table if exists achats cascade;
create table achats (
                        id_achat serial primary key,
                        employe_id bigint,
                        montant_total numeric(10,2) not null check (montant_total >= 0),
                        created_at timestamp default current_timestamp,
                        updated_at timestamp default current_timestamp,
                        actif boolean default true not null,
                        tenant_id varchar(100) not null,
                        constraint fk_achat_tenant foreign key (tenant_id)
                            references tenant(id) on delete cascade,
                        constraint fk_achats_employe foreign key (employe_id)
                            references employes(id_employe) on delete set null
);

-- Employés_Roles
drop table if exists employes_roles cascade;
create table employes_roles (
                                id_utilisateur bigint not null,
                                id_role bigint not null,
                                tenant_id varchar(100) not null,
                                constraint fk_employes_roles_tenant foreign key (tenant_id)
                                    references tenant(id) on delete cascade,
                                constraint employes_roles_pk primary key (id_role, id_utilisateur),
                                constraint fk_er_employe foreign key (id_utilisateur)
                                    references employes(id_employe) on delete cascade
);

-- États de facture
drop table if exists etat_facture cascade;
create table etat_facture (
                              id serial primary key,
                              libelle varchar(50) not null,
                              description varchar,
                              tenant_id varchar(100) not null,
                              constraint fk_etat_facture_tenant foreign key (tenant_id)
                                  references tenant(id) on delete cascade
);

-- États de paiement
drop table if exists etat_paiement cascade;
create table etat_paiement (
                               id serial primary key,
                               libelle varchar(50) not null,
                               description varchar,
                               tenant_id varchar(100) not null,
                               constraint fk_etat_paiement_tenant foreign key (tenant_id)
                                   references tenant(id) on delete cascade
);

-- États de panier
drop table if exists etat_panier cascade;
create table etat_panier (
                             id serial primary key,
                             libelle varchar(50) not null,
                             description varchar,
                             tenant_id varchar(100) not null,
                             constraint fk_etat_panier_tenant foreign key (tenant_id)
                                 references tenant(id) on delete cascade
);

-- États de vente
drop table if exists etat_vente cascade;
create table etat_vente (
                            id serial primary key,
                            libelle varchar(50) not null,
                            description varchar,
                            tenant_id varchar(100) not null,
                            constraint fk_etat_vente_tenant foreign key (tenant_id)
                                references tenant(id) on delete cascade
);

-- Fournisseurs
drop table if exists fournisseurs cascade;
create table fournisseurs (
                              id serial primary key,
                              nom varchar(50) not null,
                              prenom varchar(50) not null,
                              email varchar(100),
                              telephone varchar(20),
                              adresse varchar,
                              created_at timestamp default current_timestamp,
                              updated_at timestamp default current_timestamp,
                              tenant_id varchar(100) not null,
                              constraint fk_fournisseur_tenant foreign key (tenant_id)
                                  references tenant(id) on delete cascade
);

-- Panier
drop table if exists panier cascade;
create table panier (
                        id serial primary key,
                        id_employe bigint not null,
                        created_at timestamp default current_timestamp,
                        id_etat integer not null default 1,
                        tenant_id varchar(100) not null,
                        constraint fk_panier_tenant foreign key (tenant_id)
                            references tenant(id) on delete cascade,
                        constraint panier_etat_fk foreign key (id_etat)
                            references etat_panier(id) on delete restrict,
                        constraint panier_employe_fk foreign key (id_employe)
                            references employes(id_employe) on delete cascade
);

-- Produits
drop table if exists produits cascade;
create table produits (
                          id_produit serial primary key,
                          reference varchar(50),
                          nom varchar(100) not null,
                          description varchar,
                          prix_achat numeric(10,2) check (prix_achat >= 0),
                          prix_vente numeric(10,2) check (prix_vente >= prix_achat),
                          sous_categorie varchar(50),
                          marque varchar(50),
                          unite_mesure varchar(20),
                          seuil_lot integer default 0 check (seuil_lot >= 0),
                          seuil_produit integer default 10,
                          created_at timestamp default current_timestamp,
                          updated_at timestamp default current_timestamp,
                          image varchar,
                          stock_initial integer default 0,
                          qr_code bytea,
                          ean13 varchar(13),
                          categorie_id integer,
                          actif boolean default false not null,
                          tenant_id varchar(100) not null,
                          constraint fk_produit_tenant foreign key (tenant_id)
                              references tenant(id) on delete cascade,
                          constraint produits_cat_fk foreign key (categorie_id)
                              references categorie(categorie_id) on delete set null,
                          constraint produits_uk unique (tenant_id, ean13)
);

-- Lignes achats
drop table if exists lignes_achats cascade;
create table lignes_achats (
                               id_lignes_achat serial primary key,
                               lot_id integer,
                               prix_achat numeric(10,2) not null check (prix_achat >= 0),
                               quantite integer not null check (quantite > 0),
                               created_at timestamp default current_timestamp,
                               updated_at timestamp default current_timestamp,
                               produit_id integer,
                               achat_id integer,
                               actif boolean default true not null,
                               tenant_id varchar(100) not null,
                               constraint fk_lignes_achat_tenant foreign key (tenant_id)
                                   references tenant(id) on delete cascade,
                               constraint fk_lignes_achat_achat foreign key (achat_id)
                                   references achats(id_achat) on delete cascade,
                               constraint fk_lignes_achat_produit foreign key (produit_id)
                                   references produits(id_produit) on delete cascade,
                               constraint chk_lot_prod check (produit_id is not null or (lot_id is not null and produit_id <> lot_id))
);

-- Mouvements de stock
drop table if exists mouvement_stock cascade;
create table mouvement_stock (
                                 id serial primary key,
                                 reference varchar(50),
                                 produit_id integer not null,
                                 quantite integer not null check (quantite > 0),
                                 type_mouvement_id integer not null,
                                 date_mouvement timestamp default current_timestamp,
                                 commentaire varchar,
                                 created_at timestamp default current_timestamp,
                                 updated_at timestamp default current_timestamp,
                                 id_evenement_origine integer not null,
                                 id_ligne_origine integer not null,
                                 tenant_id varchar(100) not null,
                                 constraint fk_mouvement_stock_tenant foreign key (tenant_id)
                                     references tenant(id) on delete cascade,
                                 constraint fk_mouvement_stock_prod foreign key (produit_id)
                                     references produits(id_produit) on delete restrict
);

-- Lignes Ventes
drop table if exists lignes_ventes cascade;
create table lignes_ventes (
                               id_lignes_ventes serial primary key,
                               vente_id integer not null,
                               produit_id integer,
                               quantite integer not null check (quantite > 0),
                               prix_vente_unitaire numeric(10,2) not null check (prix_vente_unitaire > 0),
                               actif boolean default true not null,
                               tenant_id varchar(100) not null,
                               constraint fk_lignes_ventes_tenant foreign key (tenant_id)
                                   references tenant(id) on delete cascade,
                               constraint fk_lignes_ventes_produit foreign key (produit_id)
                                   references produits(id_produit) on delete cascade,
                               constraint fk_lignes_ventes_vente foreign key (vente_id)
                                   references ventes(id_ventes) on delete cascade
);

-- Factures
drop table if exists factures cascade;
create table factures (
                          id_facture serial primary key,
                          vente_id integer not null,
                          numero_facture varchar(50) not null,
                          date_facture timestamp default current_timestamp not null,
                          montant_total numeric(10,2) not null check (montant_total >= 0),
                          created_at timestamp default current_timestamp,
                          updated_at timestamp default current_timestamp,
                          etat_id integer not null default 1,
                          montant_restant numeric(10,2),
                          tenant_id varchar(100) not null,
                          constraint fk_factures_tenant foreign key (tenant_id)
                              references tenant(id) on delete cascade,
                          constraint fk_factures_etat foreign key (etat_id)
                              references etat_facture(id) on delete restrict,
                          constraint fk_factures_vente foreign key (vente_id)
                              references ventes(id_ventes) on delete cascade
);

-- Paiements
drop table if exists paiements cascade;
create table paiements (
                           id_paiement serial primary key,
                           vente_id integer not null,
                           montant_paye numeric(10,2) not null,
                           mode_paiement varchar(50),
                           reference varchar(100),
                           created_at timestamp default current_timestamp,
                           updated_at timestamp,
                           etat_id integer not null default 1,
                           tenant_id varchar(100) not null,
                           constraint fk_paiements_tenant foreign key (tenant_id)
                               references tenant(id) on delete cascade,
                           constraint fk_paiements_etat foreign key (etat_id)
                               references etat_paiement(id) on delete restrict,
                           constraint fk_paiements_vente foreign key (vente_id)
                               references ventes(id_ventes) on delete cascade
);

-- Retours
drop table if exists retours cascade;
create table retours (
                         id_retour serial primary key,
                         vente_id integer not null,
                         produit_id integer not null,
                         quantite integer not null check (quantite > 0),
                         raison varchar not null,
                         created_at timestamp default current_timestamp,
                         updated_at timestamp default current_timestamp,
                         tenant_id varchar(100) not null,
                         constraint fk_retours_tenant foreign key (tenant_id)
                             references tenant(id) on delete cascade,
                         constraint fk_retours_produit foreign key (produit_id)
                             references produits(id_produit) on delete cascade,
                         constraint fk_retours_vente foreign key (vente_id)
                             references ventes(id_ventes) on delete cascade
);

-- Table retour (public.*?)
drop table if exists retour cascade;
create table retour (
                        id_retour serial primary key,
                        ligne_id integer not null,
                        vente_id integer not null,
                        quantite integer,
                        raison varchar,
                        createdat date,
                        updatedat date,
                        tenant_id varchar(100) not null,
                        constraint fk_retour_tenant foreign key (tenant_id)
                            references tenant(id) on delete cascade,
                        constraint fk_retour_ligne foreign key (ligne_id)
                            references lignes_ventes(id_lignes_ventes),
                        constraint fk_retour_vente foreign key (vente_id)
                            references ventes(id_ventes)
);
comment on column retour.ligne_id is 'ligne_vente';

-- Table paiment alias (public.PAIMENT)
drop table if exists paiment cascade;
create table paiment (
                         id_paiement serial primary key,
                         vente_id integer not null,
                         montant numeric,
                         modepaiement varchar(100),
                         createdat timestamp,
                         updatedat date,
                         tenant_id varchar(100) not null,
                         constraint fk_paiment_tenant foreign key (tenant_id)
                             references tenant(id) on delete cascade,
                         constraint fk_paiment_vente foreign key (vente_id)
                             references ventes(id_ventes)
);

-- Etat achat
drop table if exists etat_achat cascade;
create table etat_achat (
                            id serial primary key,
                            libelle varchar(50) not null,
                            description text,
                            tenant_id varchar(100) not null,
                            constraint fk_etat_achat_tenant foreign key (tenant_id)
                                references tenant(id) on delete cascade
);

-- Vue: vue_stock_courant
drop view if exists vue_stock_courant cascade;
create view vue_stock_courant as
select
    p.id_produit,
    p.nom,
    p.stock_initial,
    ((p.stock_initial + coalesce((
                                     select sum(ms.quantite)
                                     from mouvement_stock ms
                                              join types_mouvement_stock tms on ms.type_mouvement_id = tms.id
                                     where ms.produit_id = p.id_produit
                                       and tms.type_mouvement = 'ENTREE'
                                       and ms.tenant_id = p.tenant_id
                                     ), 0))
        - coalesce((
                       select sum(ms.quantite)
                       from mouvement_stock ms
                                join types_mouvement_stock tms on ms.type_mouvement_id = tms.id
                       where ms.produit_id = p.id_produit
                         and tms.type_mouvement = 'SORTIE'
                         and ms.tenant_id = p.tenant_id
                       ), 0)
        ) as stock_courant,
    p.tenant_id
from produits p;

-- Indexes sur tenant_id pour chaque table
create index if not exists idx_authority_tenant_id on authority(tenant_id);
create index if not exists idx_categorie_tenant_id on categorie(tenant_id);
create index if not exists idx_clients_tenant_id on clients(tenant_id);
create index if not exists idx_employes_tenant_id on employes(tenant_id);
create index if not exists idx_achats_tenant_id on achats(tenant_id);
create index if not exists idx_employes_roles_tenant_id on employes_roles(tenant_id);
create index if not exists idx_etat_facture_tenant_id on etat_facture(tenant_id);
create index if not exists idx_etat_paiement_tenant_id on etat_paiement(tenant_id);
create index if not exists idx_etat_panier_tenant_id on etat_panier(tenant_id);
create index if not exists idx_etat_vente_tenant_id on etat_vente(tenant_id);
create index if not exists idx_fournisseurs_tenant_id on fournisseurs(tenant_id);
create index if not exists idx_panier_tenant_id on panier(tenant_id);
create index if not exists idx_produits_tenant_id on produits(tenant_id);
create index if not exists idx_lignes_achats_tenant_id on lignes_achats(tenant_id);
create index if not exists idx_mouvement_stock_tenant_id on mouvement_stock(tenant_id);
create index if not exists idx_lignes_ventes_tenant_id on lignes_ventes(tenant_id);
create index if not exists idx_factures_tenant_id on factures(tenant_id);
create index if not exists idx_paiements_tenant_id on paiements(tenant_id);
create index if not exists idx_retours_tenant_id on retours(tenant_id);
create index if not exists idx_retour_tenant_id on retour(tenant_id);
create index if not exists idx_paiment_tenant_id on paiment(tenant_id);
create index if not exists idx_etat_achat_tenant_id on etat_achat(tenant_id);
