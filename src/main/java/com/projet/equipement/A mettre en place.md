1. Scénarios pour les Achats
Ces scénarios concernent la gestion des stocks lors de l'ajout, de la modification ou de la suppression des achats.

   1. Création d'un Achat
   Enregistrer un nouvel achat avec plusieurs produits.
   Vérifier que chaque produit a un mouvement de stock de type "entrée" avec la quantité correcte.
   Vérifier que le stock total du produit est mis à jour (si calculé dynamiquement).
   2. Modification d'une Ligne d'Achat
   Augmenter la quantité d'un produit dans une ligne d'achat :
   Le mouvement de stock associé doit être mis à jour pour refléter l'augmentation.
   Le stock total doit être ajusté correctement.
   Diminuer la quantité d'un produit dans une ligne d'achat :
   Le mouvement de stock doit refléter la diminution.
   Le stock total doit être ajusté correctement.
   Modifier une ligne d'achat avec une quantité invalide (par exemple, quantité négative) :
   Le système doit refuser cette modification.
   3. Suppression d'une Ligne d'Achat
   Supprimer une ligne d'achat :
   Le mouvement de stock associé doit être supprimé.
   Le stock total du produit doit être ajusté en conséquence.
   4. Suppression d'un Achat Complet
   Supprimer un achat contenant plusieurs lignes :
   Tous les mouvements de stock associés doivent être supprimés.
   Les stocks des produits concernés doivent être ajustés.

2. Scénarios pour les Ventes
Ces scénarios concernent la gestion des sorties de stock lors des ventes.

   1. Création d'une Vente
   Enregistrer une nouvelle vente avec plusieurs produits :
   Vérifier que chaque produit a un mouvement de stock de type "sortie" avec la quantité correcte.
   Vérifier que le stock total du produit est réduit en conséquence.
   2. Validation de Stock avant Vente
   Tenter de vendre une quantité supérieure au stock disponible :
   Le système doit refuser la vente avec un message d'erreur.
   Tenter de vendre une quantité de produit pour un produit inexistant dans le stock :
   Le système doit refuser la vente avec un message d'erreur.
   3. Modification d'une Ligne de Vente
   Augmenter la quantité d'une ligne de vente :
   Vérifier que le stock est suffisant avant d'accepter la modification.
   Le mouvement de stock doit être mis à jour pour refléter l'augmentation.
   Le stock total doit être ajusté.
   Diminuer la quantité d'une ligne de vente :
   Le mouvement de stock doit refléter la diminution.
   Le stock total doit être ajusté.
   4. Suppression d'une Ligne de Vente
   Supprimer une ligne de vente :
   Le mouvement de stock associé doit être supprimé.
   Le stock total du produit doit être ajusté.
   5. Suppression d'une Vente Complète
   Supprimer une vente contenant plusieurs lignes :
   Tous les mouvements de stock associés doivent être supprimés.
   Les stocks des produits concernés doivent être ajustés.
3. Scénarios pour les Mouvements de Stock
Ces scénarios concernent directement les opérations sur la table MouvementsStock.

   1. Cohérence des Mouvements
   Vérifier qu'aucun mouvement de stock ne peut être créé avec une quantité négative.
   Vérifier que les mouvements sont correctement associés aux transactions (achats ou ventes).
   2. Historique des Mouvements
   Récupérer l'historique complet des mouvements pour un produit donné :
   Vérifier que tous les mouvements (entrées et sorties) sont listés dans l'ordre chronologique.
   Récupérer les mouvements sur une période donnée (par exemple, sur le mois courant).
   3. Suppression de Mouvements
   Supprimer un mouvement de stock (via la suppression d'une ligne d'achat ou de vente) :
   Vérifier que le stock total du produit est recalculé correctement.
4. Scénarios de Calcul de Stock
Ces scénarios testent la logique qui permet de calculer le stock disponible pour chaque produit.

4.1 Calcul Dynamique du Stock
Calculer le stock actuel pour un produit donné :
Vérifier que le stock est la somme des "entrées" moins la somme des "sorties".
Calculer le stock pour plusieurs produits (via une requête globale).
4.2 Vérification des Stocks Disponibles
Vérifier le stock disponible pour un produit avant une vente :
Refuser une vente si le stock est insuffisant.
Accepter une vente si le stock est suffisant.
5. Scénarios de Gestion des Erreurs
Ces scénarios testent les limites et les erreurs possibles.

5.1 Erreurs lors de la Création
Tenter de créer un achat ou une vente sans produit (liste vide) :
Le système doit refuser la création.
Tenter de créer un mouvement de stock sans produit associé :
Le système doit refuser cette opération.
5.2 Erreurs lors de la Modification
Tenter de modifier une ligne d'achat ou de vente qui n'existe pas :
Le système doit renvoyer une erreur explicite.
Tenter de modifier une ligne avec une quantité négative :
Le système doit refuser la modification.
5.3 Erreurs lors de la Suppression
Tenter de supprimer un mouvement de stock directement (si non autorisé) :
Le système doit refuser l'opération.
Tenter de supprimer une ligne d'achat ou de vente liée à une transaction inexistante :
Le système doit renvoyer une erreur.
6. Scénarios de Concurrence
Ces scénarios concernent des modifications simultanées dans un environnement multi-utilisateurs.

6.1 Concurrence sur les Achats
Simuler deux utilisateurs modifiant la même ligne d'achat simultanément :
Vérifier que le système gère correctement les conflits (via des mécanismes comme optimistic locking ou pessimistic locking).
6.2 Concurrence sur les Ventes
Simuler deux ventes simultanées pour le même produit :
Vérifier que le stock est correctement ajusté et que la deuxième vente est refusée si le stock devient insuffisant.
7. Scénarios de Performance
Testez les performances du système sous différentes charges.

7.1 Gestion de Grandes Transactions
Tester l'ajout d'un achat ou d'une vente avec plusieurs centaines de produits.
Vérifier que le système maintient une performance acceptable.
7.2 Calcul de Stock à Grande Échelle
Calculer le stock pour des milliers de produits en même temps.
Vérifier que le calcul est rapide et précis.
Résumé
Les tests doivent couvrir :

Création (achats, ventes, mouvements de stock).
Modification (quantité, ajustements de stock).
Suppression (lignes ou transactions complètes).
Calcul et validation des stocks disponibles.
Gestion des erreurs et cas limites.
Concurrence pour éviter les conflits de modification simultanée.
Performance sur de grandes quantités de données.
Ces scénarios vous permettront de garantir la fiabilité et la robustesse de votre système de gestion des stocks.



# Notion de vente
1. crée un vente
   1. creer des lignes dans vente en fonction des produits vendu 
   2. creer un client si il n'existe pas 
   3. verifier la qte restant en stock pour chaque ligne 
   4. 