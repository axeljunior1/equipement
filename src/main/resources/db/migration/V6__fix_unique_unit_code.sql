-- 1. Supprimer la contrainte de clé étrangère
ALTER TABLE unite_vente DROP CONSTRAINT uk_unite_vente_code;

alter table unite_vente
    add constraint unite_vente_uk
        unique (TENANT_ID, ID, CODE);