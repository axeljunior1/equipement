drop table if exists RETOUR cascade;
drop table if exists RETOURS cascade;
drop table if exists TYPE_RETOUR cascade;
drop table if exists etat_retour cascade;
drop table if exists LIGNE_RETOUR cascade;




CREATE TABLE type_retour
(
    id        SERIAL PRIMARY KEY,
    libelle varchar(50) not null,
    description varchar,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_type_retour_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id) ON DELETE CASCADE,
    CONSTRAINT uc_type_retour UNIQUE (id, tenant_id)
);


create table etat_retour (
                               id serial primary key,
                               libelle varchar(50) not null,
                               description varchar,
                               tenant_id varchar(100) not null,
                               CONSTRAINT uc_etat_retour UNIQUE (id, tenant_id),
                               constraint fk_etat_retour_tenant foreign key (tenant_id)
                                   references tenant(id) on delete cascade
);

CREATE TABLE retour
(
    id             SERIAL PRIMARY KEY,
    vente_id       BIGINT       NOT NULL,
    type_retour_id BIGINT       NOT NULL,
    etat_retour_id BIGINT       NOT NULL,
    date_creation  DATETIME     NOT NULL,
    tenant_id      VARCHAR(100) NOT NULL,
    CONSTRAINT uc_retour UNIQUE (vente_id, type_retour_id, etat_retour_id, tenant_id),

    CONSTRAINT fk_retour_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id) ON DELETE CASCADE,
    CONSTRAINT fk_retour_vente FOREIGN KEY (vente_id) REFERENCES ventes (id_ventes),
    CONSTRAINT fk_retour_type FOREIGN KEY (type_retour_id) REFERENCES type_retour (id),
    CONSTRAINT fk_retour_etat FOREIGN KEY (etat_retour_id) REFERENCES etat_retour (id)
);

CREATE TABLE ligne_retour
(
    id             SERIAL PRIMARY KEY,
    retour_id      BIGINT       NOT NULL,
    ligne_vente_id BIGINT       NOT NULL,
    quantite       INT          NOT NULL,
    tenant_id      VARCHAR(100) NOT NULL,
    CONSTRAINT uc_ligne_retour UNIQUE (retour_id, ligne_vente_id, tenant_id),

    CONSTRAINT fk_ligne_retour_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id) ON DELETE CASCADE,
    CONSTRAINT fk_ligne_retour_retour FOREIGN KEY (retour_id) REFERENCES retour (id),
    CONSTRAINT fk_ligne_retour_lignevente FOREIGN KEY (ligne_vente_id) REFERENCES lignes_ventes (id_lignes_ventes)
);

-- Valeurs pour le tenant 'AxelairCorp' (à adapter selon ton environnement)
INSERT INTO type_retour (libelle, description, tenant_id)
VALUES
    ('REMBOURSEMENT', 'Retour avec remboursement client', 'AxelairCorp'),
    ('AVOIR', 'Retour avec création d’un avoir client', 'AxelairCorp');


INSERT INTO etat_retour (libelle, description, tenant_id)
VALUES
    ('EN_ATTENTE_VALIDATION', 'Retour en attente de validation manuelle', 'AxelairCorp'),
    ('VALIDE', 'Retour validé', 'AxelairCorp'),
    ('TRAITE', 'Retour traité (paiement ou avoir effectué)', 'AxelairCorp'),
    ('REJETE', 'Retour refusé ou invalidé', 'AxelairCorp');

