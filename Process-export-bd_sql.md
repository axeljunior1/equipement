# Procedure export base de donnée au format sql

1. `docker exec -it <nom_du_conteneur> bash` pour se positioner sur le conteneur docker
2. `pg_dump -U root dev > export.sql` pour faire l'export, mais il reste sur le conteneur => dev est le nom de la base de donnée root est le user
2. `docker cp Postgresql:export.sql .` pour copier le fichier depuis le serveur vers le repertoire local 

# Procedure import base de donnée au format sql 

pg_dump -U root -h <hôte> -p <port> -d <nom_base> -f <chemin/vers/export.sql>*


docker exec -it Postgre bash

pg_dump -U root dev > export.sql

docker cp Postgres:/backup.sql .

docker exec -it Postgresql bash
pg_dump -U root dev > export.sql
docker cp Postgresql:export.sql C:\work\Dossier\equipement\src\main\resources\static  -- tu inverse pour faire l'inverse😂

A la creation d'un produit, je veux:
* creer un numero unique et le stocker avec ce produit, 
* il est utilisé pour generer le qrCode du produit

