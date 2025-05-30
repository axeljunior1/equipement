-- Création de la table unite_vente
CREATE TABLE unite_vente
(
    id        serial primary key ,
    code      VARCHAR(255) NOT NULL,
    libelle   VARCHAR(255) NOT NULL,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_unite_vente_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id) ON DELETE CASCADE,
    CONSTRAINT uk_unite_vente_code UNIQUE (code)
);

-- Insertions dans la table unite_vente
INSERT INTO unite_vente (code, libelle, tenant_id)
VALUES ('UNI', 'Unité', 'AxelairCorp');
INSERT INTO unite_vente (code, libelle, tenant_id)
VALUES ('KG', 'Kilogramme', 'AxelairCorp');
INSERT INTO unite_vente (code, libelle, tenant_id)
VALUES ('L', 'Litre', 'AxelairCorp');
INSERT INTO unite_vente (code, libelle, tenant_id)
VALUES ('PAQ', 'Paquet', 'AxelairCorp');
INSERT INTO unite_vente (code, libelle, tenant_id)
VALUES ('BOX', 'Boîte', 'AxelairCorp');
INSERT INTO unite_vente (code, libelle, tenant_id)
VALUES ('CTN', 'Carton', 'AxelairCorp');
INSERT INTO unite_vente (code, libelle, tenant_id)
VALUES ('M', 'Mettre', 'AxelairCorp');
INSERT INTO unite_vente (code, libelle, tenant_id)
VALUES ('M2', 'Mettre carre', 'AxelairCorp');

-- Création de la table format_vente
CREATE TABLE format_vente
(
    id                  serial primary key ,
    produit_id          BIGINT         NOT NULL,
    unite_vente_id      BIGINT         NOT NULL,
    libelle_format      VARCHAR(255)   NOT NULL,
    quantite_par_format INT            NOT NULL,
    prix_vente          DECIMAL(19, 2) NOT NULL,
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_format_vente_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(id) ON DELETE CASCADE,
    CONSTRAINT fk_format_vente_produit FOREIGN KEY (produit_id) REFERENCES PRODUITS (ID_PRODUIT),
    CONSTRAINT fk_format_vente_unite FOREIGN KEY (unite_vente_id) REFERENCES unite_vente (id),
    CONSTRAINT chk_quantite_positive CHECK (quantite_par_format > 0),
    CONSTRAINT chk_prix_positif CHECK (prix_vente > 0)
);




