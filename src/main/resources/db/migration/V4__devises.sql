CREATE TABLE devise
(
    id        serial PRIMARY KEY,
    code      VARCHAR(3)   NOT NULL UNIQUE, -- Exemple: USD, EUR
    nom       VARCHAR(50)  NOT NULL,
    symbole   VARCHAR(5),
    tenant_id VARCHAR(100) NOT NULL,

    CONSTRAINT fk_devise_tenant FOREIGN KEY (tenant_id) REFERENCES tenant (id) ON DELETE CASCADE
);


INSERT INTO devise (code, nom, symbole, tenant_id)
VALUES ('USD', 'Dollar américain', '$', 'AxelairCorp'),
       ('EUR', 'Euro', '€', 'AxelairCorp'),
       ('GBP', 'Livre sterling', '£', 'AxelairCorp'),
       ('JPY', 'Yen japonais', '¥', 'AxelairCorp'),
       ('CNY', 'Yuan chinois', '¥', 'AxelairCorp'),
       ('XOF', 'Franc CFA BCEAO', 'F', 'AxelairCorp'),
       ('CAD', 'Dollar canadien', '$', 'AxelairCorp'),
       ('CHF', 'Franc suisse', 'CHF', 'AxelairCorp'),
       ('AUD', 'Dollar australien', '$', 'AxelairCorp'),
       ('MAD', 'Dirham marocain', 'د.م', 'AxelairCorp'),
       ('XAF', 'Franc CFA BEAC', 'F', 'AxelairCorp') ,
       ('DZD', 'Dinar algérien', 'د.ج', 'AxelairCorp');

-- 1. Ajouter la colonne avec valeur par défaut 1 et non nullable
ALTER TABLE PRODUITS
    ADD COLUMN devise_id BIGINT NOT NULL DEFAULT 1;

-- 2. Ajouter la contrainte de clé étrangère
ALTER TABLE PRODUITS
    ADD CONSTRAINT fk_produit_devise
        FOREIGN KEY (devise_id) REFERENCES devise(id);


