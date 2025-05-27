-- 1. Supprimer la contrainte de clé étrangère
ALTER TABLE devise DROP CONSTRAINT fk_devise_tenant;

-- 2. Supprimer la colonne tenant_id
ALTER TABLE devise DROP COLUMN tenant_id;
