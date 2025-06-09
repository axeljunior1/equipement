
CREATE TABLE mode_paiement
(
    id          SERIAL PRIMARY KEY,
    code        VARCHAR(50)  NOT NULL,
    description TEXT,
    active      BOOLEAN DEFAULT TRUE,
    tenant_id   VARCHAR(100) NOT NULL,

    CONSTRAINT fk_mode_paiement_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id) ON DELETE CASCADE,
    CONSTRAINT uc_mode_paiement UNIQUE (code, tenant_id) -- pour éviter les doublons dans un même tenant
);

DROP TABLE IF EXISTS paiement CASCADE;

INSERT INTO ETAT_PAIEMENT (LIBELLE, DESCRIPTION, TENANT_ID)
VALUES ('ERREUR', 'Erreur lors du paiement', 'AxelairCorp');

INSERT INTO ETAT_PAIEMENT (LIBELLE, DESCRIPTION, TENANT_ID)
VALUES ('PENDING', 'En cours ', 'AxelairCorp');


INSERT INTO mode_paiement (code, description, tenant_id)
VALUES ('Espèces', 'Paiement en espèces', 'AxelairCorp'),
       ('Carte bancaire', 'Paiement par carte (Visa, Mastercard, etc.)', 'AxelairCorp'),
       ('Mobile Money', 'Paiement via services mobiles (Orange Money, MTN MoMo, etc.)', 'AxelairCorp'),
       ('Virement bancaire', 'Paiement par transfert bancaire', 'AxelairCorp');

ALTER TABLE PAIEMENTS
    ADD COLUMN ID_MODE_PAIEMENT INTEGER default 1;

-- Ajouter la contrainte de clé étrangère
ALTER TABLE PAIEMENTS
    ADD CONSTRAINT FK_PAIEMENTS_MODE_PAIEMENT
        FOREIGN KEY (ID_MODE_PAIEMENT)
            REFERENCES PUBLIC.MODE_PAIEMENT (id);
