INSERT INTO PUBLIC.CATEGORIE (NOM, DESCRIPTION, CREATED_AT, UPDATED_AT) VALUES ('Accessoires Téléphone', 'Tous les accessoires pour téléphones mobiles : étuis, chargeurs, câbles, coques, etc.', '2025-01-25 10:21:29.823468', '2025-01-25 10:21:29.823468');
INSERT INTO PUBLIC.CATEGORIE (NOM, DESCRIPTION, CREATED_AT, UPDATED_AT) VALUES ('Maison', 'Articles pour la maison : décoration, meubles, luminaires, etc.', '2025-01-25 10:21:29.823468', '2025-01-25 10:21:29.823468');
INSERT INTO PUBLIC.CATEGORIE (NOM, DESCRIPTION, CREATED_AT, UPDATED_AT) VALUES ('Bazar', 'Produits divers et variés pour le quotidien : articles de loisir, petits gadgets, etc.', '2025-01-25 10:21:29.823468', '2025-01-25 10:21:29.823468');
INSERT INTO PUBLIC.CATEGORIE (NOM, DESCRIPTION, CREATED_AT, UPDATED_AT) VALUES ('Cuisine', 'Équipements et accessoires pour la cuisine : ustensiles, électroménagers, vaisselle, etc.', '2025-01-25 10:21:29.823468', '2025-01-25 10:21:29.823468');
INSERT INTO PUBLIC.CATEGORIE (NOM, DESCRIPTION, CREATED_AT, UPDATED_AT) VALUES ('Télévision', 'Produits liés à la télévision : TV, supports, télécommandes, etc.', '2025-01-25 10:21:29.823468', '2025-01-25 10:21:29.823468');
INSERT INTO PUBLIC.CATEGORIE (NOM, DESCRIPTION, CREATED_AT, UPDATED_AT) VALUES ('Écrans', 'Tous types d’écrans : moniteurs, écrans d’ordinateur, projecteurs, etc.', '2025-01-25 10:21:29.823468', '2025-01-25 10:21:29.823468');
INSERT INTO PUBLIC.CATEGORIE (NOM, DESCRIPTION, CREATED_AT, UPDATED_AT) VALUES ('Matériel Informatique', 'Tout le matériel nécessaire pour l’informatique : ordinateurs, périphériques, composants, etc.', '2025-01-25 10:21:29.823468', '2025-01-25 10:21:29.823468');
INSERT INTO PUBLIC.CATEGORIE (NOM, DESCRIPTION, CREATED_AT, UPDATED_AT) VALUES ('Électronique', 'Produits électroniques divers : objets connectés, gadgets électroniques, appareils audio-vidéo, etc.', '2025-01-25 10:21:29.823468', '2025-01-25 10:21:29.823468');


INSERT INTO PUBLIC.CLIENTS (NOM, PRENOM, EMAIL, TELEPHONE, ADRESSE, CREATED_AT, UPDATED_AT, ACTIF) VALUES ('Client Junior  ', 'Super client', 'client-junior@gmail.com', '0749482336', null, '2025-02-21 21:51:36.000000', '2025-01-12 10:05:41.163419', true);


INSERT INTO PUBLIC.EMPLOYES (NOM, PRENOM, CREATED_AT, UPDATED_AT, PASSWORD, ACTIF) VALUES ('junior', 'junior', '2025-02-10 22:39:42.907528', '2025-02-10 22:39:43.047566', '$2a$10$YLBqIlUvkMk6nOR9tKBPL.jf.H9bT1X8tbGHXx.Y7tVJVuNrgc..q', true);

INSERT INTO PUBLIC.ETAT_PANIER (LIBELLE) VALUES ('EN_COURS');
INSERT INTO PUBLIC.ETAT_PANIER (LIBELLE) VALUES ('VALIDE');
INSERT INTO PUBLIC.ETAT_PANIER (LIBELLE) VALUES ('ANNULE');

INSERT INTO ETAT_VENTE (LIBELLE, DESCRIPTION) VALUES ('CREEE', null);
INSERT INTO ETAT_VENTE (LIBELLE, DESCRIPTION) VALUES ('VALIDE', null);
INSERT INTO ETAT_VENTE (LIBELLE, DESCRIPTION) VALUES ('CONFIRME', null);
INSERT INTO ETAT_VENTE (LIBELLE, DESCRIPTION) VALUES ('ANNULEE', null);
INSERT INTO ETAT_VENTE (LIBELLE, DESCRIPTION) VALUES ('REMBOURSEE', null);

INSERT INTO PUBLIC.ETAT_FACTURE (LIBELLE, DESCRIPTION) VALUES ('CREEE', null);
INSERT INTO PUBLIC.ETAT_FACTURE (LIBELLE, DESCRIPTION) VALUES ('GENEREE', null);




INSERT INTO PUBLIC.ROLES (NOM, DESCRIPTION) VALUES ('ADMIN', 'Administrateur ayant tous les droits');
INSERT INTO PUBLIC.ROLES (NOM, DESCRIPTION) VALUES ('GESTIONNAIRE_STOCK', 'Responsable de la gestion des stocks');
INSERT INTO PUBLIC.ROLES (NOM, DESCRIPTION) VALUES ('VENDEUR', 'Employé chargé des ventes');
INSERT INTO PUBLIC.ROLES (NOM, DESCRIPTION) VALUES ('COMPTABLE', 'Responsable des finances');
INSERT INTO PUBLIC.ROLES (NOM, DESCRIPTION) VALUES ('Role Test', 'erer');

INSERT INTO public.authority (nom) VALUES ('CREER_PRODUIT');
INSERT INTO public.authority (nom) VALUES ('SUPPRIMER_PRODUIT');
INSERT INTO public.authority (nom) VALUES ('VOIR_PRODUIT');
INSERT INTO public.authority (nom) VALUES ('GERER_STOCK');
INSERT INTO public.authority (nom) VALUES ('VALIDER_VENTE');
INSERT INTO public.authority (nom) VALUES ('GENERER_RAPPORT');

INSERT INTO PUBLIC.ROLE_AUTHORITY (ID_ROLE, ID_AUTHORITY) VALUES (5, 6);
INSERT INTO PUBLIC.ROLE_AUTHORITY (ID_ROLE, ID_AUTHORITY) VALUES (5, 4);
INSERT INTO PUBLIC.ROLE_AUTHORITY (ID_ROLE, ID_AUTHORITY) VALUES (5, 5);
INSERT INTO PUBLIC.ROLE_AUTHORITY (ID_ROLE, ID_AUTHORITY) VALUES (1, 5);
INSERT INTO PUBLIC.ROLE_AUTHORITY (ID_ROLE, ID_AUTHORITY) VALUES (1, 1);
INSERT INTO PUBLIC.ROLE_AUTHORITY (ID_ROLE, ID_AUTHORITY) VALUES (1, 3);
INSERT INTO PUBLIC.ROLE_AUTHORITY (ID_ROLE, ID_AUTHORITY) VALUES (1, 6);
INSERT INTO PUBLIC.ROLE_AUTHORITY (ID_ROLE, ID_AUTHORITY) VALUES (1, 2);
INSERT INTO PUBLIC.ROLE_AUTHORITY (ID_ROLE, ID_AUTHORITY) VALUES (1, 4);


INSERT INTO PUBLIC.ROLE_EMPLOYE (ID_ROLE, ID_EMPLOYE) VALUES (1, 1);
INSERT INTO PUBLIC.ROLE_EMPLOYE (ID_ROLE, ID_EMPLOYE) VALUES (2, 1);
INSERT INTO PUBLIC.ROLE_EMPLOYE (ID_ROLE, ID_EMPLOYE) VALUES (3, 1);
INSERT INTO PUBLIC.ROLE_EMPLOYE (ID_ROLE, ID_EMPLOYE) VALUES (4, 1);



INSERT INTO PUBLIC.TYPES_MOUVEMENT_STOCK (CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('DESTRUCTION_PRODUIT', 'Destruction de produit', 'Sortie de stock pour destruction de produits périmés ou endommagés', '2025-01-29 03:37:46.183956', 'SORTIE');
INSERT INTO PUBLIC.TYPES_MOUVEMENT_STOCK (CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('PERTE_VOL', 'Perte ou vol', 'Sortie de stock en raison d’une perte ou d’un vol', '2025-01-29 03:37:46.183956', 'SORTIE');
INSERT INTO PUBLIC.TYPES_MOUVEMENT_STOCK (CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('RETOUR_FOURNISSEUR', 'Retour fournisseur', 'Renvoi d’un produit défectueux ou non conforme au fournisseur', '2025-01-29 03:37:46.183956', 'SORTIE');
INSERT INTO PUBLIC.TYPES_MOUVEMENT_STOCK (CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('TRANSFERT_ENTREPOT', 'Transfert entre entrepôts', 'Mouvement de stock d’un entrepôt à un autre', '2025-01-29 03:37:46.183956', 'SORTIE');
INSERT INTO PUBLIC.TYPES_MOUVEMENT_STOCK (CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('DON_ASSOCIATION', 'Don à une association', 'Sortie de stock pour don à une organisation caritative', '2025-01-29 03:37:46.183956', 'SORTIE');
INSERT INTO PUBLIC.TYPES_MOUVEMENT_STOCK (CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('ACHAT_MARCHANDISE', 'Achat de marchandises', 'Entrée de stock suite à un achat auprès d’un fournisseur', '2025-01-29 03:37:46.183956', 'ENTREE');
INSERT INTO PUBLIC.TYPES_MOUVEMENT_STOCK (CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('INVENTAIRE_AJUSTEMENT', 'Ajustement d’inventaire', 'Correction du stock après un inventaire physique', '2025-01-29 03:37:46.183956', 'ENTREE');
INSERT INTO PUBLIC.TYPES_MOUVEMENT_STOCK (CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('RETOUR_CLIENT', 'Retour client', 'Retour en stock des produits renvoyés par un client', '2025-01-29 03:37:46.183956', 'ENTREE');
INSERT INTO PUBLIC.TYPES_MOUVEMENT_STOCK (CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('RECEPTION_TRANSFERT', 'Réception de transfert', 'Entrée de stock suite à un transfert provenant d’un autre entrepôt', '2025-01-29 03:37:46.183956', 'ENTREE');
INSERT INTO PUBLIC.TYPES_MOUVEMENT_STOCK (CODE, NOM, DESCRIPTION, DATE_CREATION, TYPE_MOUVEMENT) VALUES ('VENTE_PRODUIT', 'Vente de produits', 'Sortie de stock pour la vente de produits aux clients', '2025-01-29 03:37:46.183956', 'SORTIE');
