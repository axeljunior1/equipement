@echo off
REM Définir des variables
set CONTAINER_NAME=dev
set DB_USER=user
set DB_NAME=dev
set IMPORT_FILE=export.sql
set SRC_PATH=C:\Users\Axel\IdeaProjects\equipement\src\main\resources\static

REM Informer de l'opération en cours
echo Copie du fichier %IMPORT_FILE% vers le conteneur %CONTAINER_NAME%...

REM Copier le fichier depuis l'hôte vers le conteneur
docker cp %SRC_PATH%\%IMPORT_FILE% %CONTAINER_NAME%:/%IMPORT_FILE%

REM Vérifier si la copie a réussi
if errorlevel 1 (
    echo Echec de la copie du fichier vers le conteneur.
    pause
    exit /b 1
)

REM Appliquer les modifications à la base de données
echo Execution du fichier %IMPORT_FILE% dans la base de donnees %DB_NAME%...
docker exec -it %CONTAINER_NAME% bash -c "psql -U %DB_USER% %DB_NAME% < /%IMPORT_FILE%"

REM Vérifier si l'exécution a réussi
if errorlevel 1 (
    echo Echec de l'application des modifications a la base de donnees.
    pause
    exit /b 1
)

REM Confirmation de la réussite
echo Operation terminee avec succes. Les modifications ont ete appliquees a la base de donnees.

REM Laisser l'utilisateur fermer la console manuellement
pause
exit /b 0
