-- 1. Suppression de la contrainte incohérente
ALTER TABLE LIGNES_ACHATS
    DROP CONSTRAINT IF EXISTS CHK_LOT_PROD;

-- 2. Suppression de la colonne LOT_ID devenue inutile
ALTER TABLE LIGNES_ACHATS
    DROP COLUMN IF EXISTS LOT_ID;
