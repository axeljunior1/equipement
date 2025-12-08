------------------------------------------------------------
-- 1. AJOUT DES NOUVELLES COLONNES POUR L'ORIGINE DU MOUVEMENT
------------------------------------------------------------

ALTER TABLE MOUVEMENT_STOCK
    ADD COLUMN SOURCE_TYPE VARCHAR(50);

ALTER TABLE MOUVEMENT_STOCK
    ADD COLUMN SOURCE_ID BIGINT;

------------------------------------------------------------
-- 2. SUPPRESSION DES COLONNES INCOHÉRENTES / INUTILISABLES
------------------------------------------------------------

ALTER TABLE MOUVEMENT_STOCK
    DROP COLUMN IF EXISTS ID_EVENEMENT_ORIGINE;

ALTER TABLE MOUVEMENT_STOCK
    DROP COLUMN IF EXISTS ID_LIGNE_ORIGINE;

------------------------------------------------------------
-- 3. CONTRAINTE CHECK POUR SÉCURISER LE TYPE D'ORIGINE
-- Syntaxe valide pour H2 : CHECK (colonne IN (...))
------------------------------------------------------------

ALTER TABLE MOUVEMENT_STOCK
    ADD CONSTRAINT CHK_MVT_SOURCE_TYPE
        CHECK (SOURCE_TYPE IN (
                               'ACHAT',
                               'VENTE',
                               'RETOUR_CLIENT',
                               'RETOUR_FOURNISSEUR',
                               'INVENTAIRE',
                               'CORRECTION'
            ));
