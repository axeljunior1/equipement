
INSERT INTO CATEGORIE (tenant_id, NOM, DESCRIPTION, CREATED_AT, UPDATED_AT) VALUES ('{{tenant_id}}','Accessoires Téléphone', 'Tous les accessoires pour téléphones mobiles : étuis, chargeurs, câbles, coques, etc.', '2025-01-25 10:21:29.823468', '2025-01-25 10:21:29.823468');
INSERT INTO CATEGORIE (tenant_id, NOM, DESCRIPTION, CREATED_AT, UPDATED_AT) VALUES ('{{tenant_id}}','Maison', 'Articles pour la maison : décoration, meubles, luminaires, etc.', '2025-01-25 10:21:29.823468', '2025-01-25 10:21:29.823468');
INSERT INTO CATEGORIE (tenant_id, NOM, DESCRIPTION, CREATED_AT, UPDATED_AT) VALUES ('{{tenant_id}}','Bazar', 'Produits divers et variés pour le quotidien : articles de loisir, petits gadgets, etc.', '2025-01-25 10:21:29.823468', '2025-01-25 10:21:29.823468');
INSERT INTO CATEGORIE (tenant_id, NOM, DESCRIPTION, CREATED_AT, UPDATED_AT) VALUES ('{{tenant_id}}','Cuisine', 'Équipements et accessoires pour la cuisine : ustensiles, électroménagers, vaisselle, etc.', '2025-01-25 10:21:29.823468', '2025-01-25 10:21:29.823468');
INSERT INTO CATEGORIE (tenant_id, NOM, DESCRIPTION, CREATED_AT, UPDATED_AT) VALUES ('{{tenant_id}}','Télévision', 'Produits liés à la télévision : TV, supports, télécommandes, etc.', '2025-01-25 10:21:29.823468', '2025-01-25 10:21:29.823468');
INSERT INTO CATEGORIE (tenant_id, NOM, DESCRIPTION, CREATED_AT, UPDATED_AT) VALUES ('{{tenant_id}}','Écrans', 'Tous types d’écrans : moniteurs, écrans d’ordinateur, projecteurs, etc.', '2025-01-25 10:21:29.823468', '2025-01-25 10:21:29.823468');
INSERT INTO CATEGORIE (tenant_id, NOM, DESCRIPTION, CREATED_AT, UPDATED_AT) VALUES ('{{tenant_id}}','Matériel Informatique', 'Tout le matériel nécessaire pour l’informatique : ordinateurs, périphériques, composants, etc.', '2025-01-25 10:21:29.823468', '2025-01-25 10:21:29.823468');
INSERT INTO CATEGORIE (tenant_id, NOM, DESCRIPTION, CREATED_AT, UPDATED_AT) VALUES ('{{tenant_id}}','Électronique', 'Produits électroniques divers : objets connectés, gadgets électroniques, appareils audio-vidéo, etc.', '2025-01-25 10:21:29.823468', '2025-01-25 10:21:29.823468');


INSERT INTO CLIENTS (tenant_id, NOM, PRENOM, EMAIL, TELEPHONE, ADRESSE, CREATED_AT, UPDATED_AT, ACTIF) VALUES ('{{tenant_id}}','Client {{tenant_id}} ', 'Super client', 'client-{{tenant_id}}@gmail.com', '0749482336', null, '2025-02-21 21:51:36.000000', '2025-01-12 10:05:41.163419', true);



INSERT INTO ETAT_PANIER (tenant_id, LIBELLE) VALUES ('{{tenant_id}}','EN_COURS');
INSERT INTO ETAT_PANIER (tenant_id, LIBELLE) VALUES ('{{tenant_id}}','VALIDE');
INSERT INTO ETAT_PANIER (tenant_id, LIBELLE) VALUES ('{{tenant_id}}','ANNULE');

INSERT INTO ETAT_VENTE (tenant_id, LIBELLE, DESCRIPTION) VALUES ('{{tenant_id}}','CREEE', null);
INSERT INTO ETAT_VENTE (tenant_id, LIBELLE, DESCRIPTION) VALUES ('{{tenant_id}}','VALIDE', null);
INSERT INTO ETAT_VENTE (tenant_id, LIBELLE, DESCRIPTION) VALUES ('{{tenant_id}}','CONFIRME', null);
INSERT INTO ETAT_VENTE (tenant_id, LIBELLE, DESCRIPTION)
VALUES ('{{tenant_id}}','EN_ATTENTE_PAIEMENT', null);

INSERT INTO ETAT_VENTE (tenant_id, LIBELLE, DESCRIPTION)
VALUES ('{{tenant_id}}','PAIEMENT_PARTIEL', null);

INSERT INTO ETAT_VENTE (tenant_id, LIBELLE, DESCRIPTION)
VALUES ('{{tenant_id}}','VENTE_A_CREDIT', null);

INSERT INTO ETAT_VENTE (tenant_id, LIBELLE, DESCRIPTION)
VALUES ('{{tenant_id}}','PAYEE', null);

INSERT INTO ETAT_VENTE (tenant_id, LIBELLE, DESCRIPTION)
VALUES ('{{tenant_id}}','FERMEE', null);

INSERT INTO ETAT_VENTE (tenant_id, LIBELLE, DESCRIPTION)
VALUES ('{{tenant_id}}','ANNULEE', null);

INSERT INTO ETAT_FACTURE (tenant_id, LIBELLE, DESCRIPTION) VALUES ('{{tenant_id}}','CREEE', null);
INSERT INTO ETAT_FACTURE (tenant_id, LIBELLE, DESCRIPTION) VALUES ('{{tenant_id}}','GENEREE', null);




INSERT INTO ROLES (tenant_id, NOM, DESCRIPTION) VALUES ('{{tenant_id}}','ADMIN', 'Administrateur ayant tous les droits');
INSERT INTO ROLES (tenant_id, NOM, DESCRIPTION) VALUES ('{{tenant_id}}','GESTIONNAIRE_STOCK', 'Responsable de la gestion des stocks');
INSERT INTO ROLES (tenant_id, NOM, DESCRIPTION) VALUES ('{{tenant_id}}','VENDEUR', 'Employé chargé des ventes');
INSERT INTO ROLES (tenant_id, NOM, DESCRIPTION) VALUES ('{{tenant_id}}','COMPTABLE', 'Responsable des finances');
INSERT INTO ROLES (tenant_id, NOM, DESCRIPTION) VALUES ('{{tenant_id}}','Role Test', 'erer');

INSERT INTO public.authority (tenant_id, nom) VALUES ('{{tenant_id}}','CREER_PRODUIT');
INSERT INTO public.authority (tenant_id, nom) VALUES ('{{tenant_id}}','SUPPRIMER_PRODUIT');
INSERT INTO public.authority (tenant_id, nom) VALUES ('{{tenant_id}}','VOIR_PRODUIT');
INSERT INTO public.authority (tenant_id, nom) VALUES ('{{tenant_id}}','GERER_STOCK');
INSERT INTO public.authority (tenant_id, nom) VALUES ('{{tenant_id}}','VALIDER_VENTE');
INSERT INTO public.authority (tenant_id, nom) VALUES ('{{tenant_id}}','GENERER_RAPPORT');

INSERT INTO ROLE_authority (tenant_id, ID_ROLE, ID_AUTHORITY) VALUES ('{{tenant_id}}',5, 6);
INSERT INTO ROLE_authority (tenant_id, ID_ROLE, ID_AUTHORITY) VALUES ('{{tenant_id}}',5, 4);

-- select id from roles where nom = 'ADMIN' and tenant_id = '{{tenant_id}}'
INSERT INTO ROLE_EMPLOYE (tenant_id, ID_ROLE, ID_EMPLOYE) VALUES ('{{tenant_id}}',(
    select ID_ROLE from roles where nom = 'ADMIN' and tenant_id = '{{tenant_id}}'

)  , (select ID_EMPLOYE from employes where nom= '{{tenant_id}}' and tenant_id = '{{tenant_id}}'  ));



INSERT INTO TYPES_MOUVEMENT_STOCK (tenant_id, CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('{{tenant_id}}','DESTRUCTION_PRODUIT', 'Destruction de produit', 'Sortie de stock pour destruction de produits périmés ou endommagés', '2025-01-29 03:37:46.183956', 'SORTIE');
INSERT INTO TYPES_MOUVEMENT_STOCK (tenant_id, CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('{{tenant_id}}','PERTE_VOL', 'Perte ou vol', 'Sortie de stock en raison d’une perte ou d’un vol', '2025-01-29 03:37:46.183956', 'SORTIE');
INSERT INTO TYPES_MOUVEMENT_STOCK (tenant_id, CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('{{tenant_id}}','RETOUR_FOURNISSEUR', 'Retour fournisseur', 'Renvoi d’un produit défectueux ou non conforme au fournisseur', '2025-01-29 03:37:46.183956', 'SORTIE');
INSERT INTO TYPES_MOUVEMENT_STOCK (tenant_id, CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('{{tenant_id}}','TRANSFERT_ENTREPOT', 'Transfert entre entrepôts', 'Mouvement de stock d’un entrepôt à un autre', '2025-01-29 03:37:46.183956', 'SORTIE');
INSERT INTO TYPES_MOUVEMENT_STOCK (tenant_id, CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('{{tenant_id}}','DON_ASSOCIATION', 'Don à une association', 'Sortie de stock pour don à une organisation caritative', '2025-01-29 03:37:46.183956', 'SORTIE');
INSERT INTO TYPES_MOUVEMENT_STOCK (tenant_id, CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('{{tenant_id}}','ACHAT_MARCHANDISE', 'Achat de marchandises', 'Entrée de stock suite à un achat auprès d’un fournisseur', '2025-01-29 03:37:46.183956', 'ENTREE');
INSERT INTO TYPES_MOUVEMENT_STOCK (tenant_id, CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('{{tenant_id}}','INVENTAIRE_AJUSTEMENT', 'Ajustement d’inventaire', 'Correction du stock après un inventaire physique', '2025-01-29 03:37:46.183956', 'ENTREE');
INSERT INTO TYPES_MOUVEMENT_STOCK (tenant_id, CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('{{tenant_id}}','RETOUR_CLIENT', 'Retour client', 'Retour en stock des produits renvoyés par un client', '2025-01-29 03:37:46.183956', 'ENTREE');
INSERT INTO TYPES_MOUVEMENT_STOCK (tenant_id, CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('{{tenant_id}}','RECEPTION_TRANSFERT', 'Réception de transfert', 'Entrée de stock suite à un transfert provenant d’un autre entrepôt', '2025-01-29 03:37:46.183956', 'ENTREE');
INSERT INTO TYPES_MOUVEMENT_STOCK (tenant_id, CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('{{tenant_id}}','VENTE_PRODUIT', 'Vente de produits', 'Sortie de stock pour la vente de produits aux clients', '2025-01-29 03:37:46.183956', 'SORTIE');


INSERT INTO ETAT_PAIEMENT (tenant_id, LIBELLE, DESCRIPTION)
VALUES ('{{tenant_id}}','EN_ATTENTE_PAIEMENT', null);

INSERT INTO ETAT_PAIEMENT (tenant_id, LIBELLE, DESCRIPTION)
VALUES ('{{tenant_id}}','PAIEMENT_PARTIEL', null);

INSERT INTO ETAT_PAIEMENT (tenant_id, LIBELLE, DESCRIPTION)
VALUES ('{{tenant_id}}','VENTE_A_CREDIT', null);

INSERT INTO ETAT_PAIEMENT (tenant_id, LIBELLE, DESCRIPTION)
VALUES ('{{tenant_id}}','PAYEE', null);

INSERT INTO ETAT_PAIEMENT (tenant_id, LIBELLE, DESCRIPTION)
VALUES ('{{tenant_id}}','FERMEE', null);

INSERT INTO ETAT_PAIEMENT (tenant_id, LIBELLE, DESCRIPTION)
VALUES ('{{tenant_id}}','ANNULEE', null);

INSERT INTO devise (code, nom, symbole, tenant_id)
VALUES ('USD', 'Dollar américain', '$', '{{tenant_id}}'),
       ('EUR', 'Euro', '€', '{{tenant_id}}'),
       ('GBP', 'Livre sterling', '£', '{{tenant_id}}'),
       ('JPY', 'Yen japonais', '¥', '{{tenant_id}}'),
       ('CNY', 'Yuan chinois', '¥', '{{tenant_id}}'),
       ('XOF', 'Franc CFA BCEAO', 'F', '{{tenant_id}}'),
       ('CAD', 'Dollar canadien', '$', '{{tenant_id}}'),
       ('CHF', 'Franc suisse', 'CHF', '{{tenant_id}}'),
       ('AUD', 'Dollar australien', '$', '{{tenant_id}}'),
       ('MAD', 'Dirham marocain', 'د.م', '{{tenant_id}}'),
       ('XAF', 'Franc CFA BEAC', 'F', '{{tenant_id}}') ,
       ('DZD', 'Dinar algérien', 'د.ج', '{{tenant_id}}');


INSERT INTO PRODUITS (tenant_id, REFERENCE, NOM, DESCRIPTION, PRIX_ACHAT, PRIX_VENTE, SOUS_CATEGORIE, MARQUE, UNITE_MESURE,
                             SEUIL_LOT, SEUIL_PRODUIT, CREATED_AT, UPDATED_AT, IMAGE, STOCK_INITIAL, QR_CODE, EAN13,
                             CATEGORIE_ID, ACTIF, devise_id)
VALUES ('{{tenant_id}}',null, 'Produit {{tenant_id}}', 'test', 2.00, 6.00, null, null, null, 0, null, '2025-05-03 14:56:34.955467',
        '2025-05-03 14:56:34.955467', '', 5,
        '89504E470D0A1A0A0000000D49484452000000640000006401000000005899A8F9000000BA49444154785EE5D2B10D84300C0550A30CC00291320A2B5D1638740B5C5672E735905880742E22F98C0E080D263D569A57D9DF0EC8A916789A103A2C2F80F15624994B94D22088E423FA36CDB95118DA2459F4D5C92EA5F9C89FD35E4A8BC3B1334BE83285B4CF6289CA7BD037DD0B7D27058EC90CD1D44BE9A541ACF95CA2AD83252D94C4F33F9F25DD19CB07F74D185AEF0070A4B50451DC32D44B5B42F982DB3AD8A2397168906484C875B24BADFF0546DE3A58AAF568FD00EB151EFCC96903A20000000049454E44AE426082',
        '9996804452255', 2, true, (select id from devise where tenant_id = '{{tenant_id}}') and code = 'XAF' );

INSERT INTO TARIF_ACHAT (tenant_id,ID_PRODUIT, PRIX_ACHAT, CREATED_AT, UPDATED_AT) VALUES ('{{tenant_id}}', select ID_PRODUIT
                                                                                           from produits where nom = 'Produit {{tenant_id}}' and tenant_id = '{{tenant_id}}' , 2.00, '2025-05-03 14:28:26.161295', null);


INSERT INTO ETAT_ACHAT (tenant_id, LIBELLE, DESCRIPTION) VALUES ('{{tenant_id}}','CREEE', null);
INSERT INTO ETAT_ACHAT (tenant_id, LIBELLE, DESCRIPTION) VALUES ('{{tenant_id}}','VALIDE', null);
INSERT INTO ETAT_ACHAT (tenant_id, LIBELLE, DESCRIPTION) VALUES ('{{tenant_id}}','CONFIRME', null);
INSERT INTO ETAT_ACHAT (tenant_id, LIBELLE, DESCRIPTION) VALUES ('{{tenant_id}}','ANNULEE', null);
INSERT INTO ETAT_ACHAT (tenant_id, LIBELLE, DESCRIPTION) VALUES ('{{tenant_id}}','REMBOURSEE', null);