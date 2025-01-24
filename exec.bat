@echo off
REM Définir des variables
set CONTAINER_NAME=Postgresql
set DB_USER=root
set DB_NAME=dev
set EXPORT_FILE=export.sql
set DEST_PATH=C:\work\Dossier\equipement\src\main\resources\static

REM Informer de l'opération en cours
echo Export de la base de donnees %DB_NAME% depuis le conteneur %CONTAINER_NAME%...

REM Exécuter la commande pg_dump dans le conteneur Docker
docker exec -it %CONTAINER_NAME% bash -c "pg_dump -U %DB_USER% %DB_NAME% > /%EXPORT_FILE%"

REM Vérifier si l'export a réussi
if errorlevel 1 (
    echo Echec de l'export de la base de donnees.
    exit /b 1
)

REM Copier le fichier exporté depuis le conteneur vers l'hôte
echo Copie du fichier %EXPORT_FILE% depuis le conteneur vers %DEST_PATH%...
docker cp %CONTAINER_NAME%:/%EXPORT_FILE% %DEST_PATH%

REM Vérifier si la copie a réussi
if errorlevel 1 (
    echo Echec de la copie du fichier.
    exit /b 1
)

REM Confirmation de la réussite
echo Operation terminee avec succes. Le fichier est disponible dans %DEST_PATH%.
exit /b 0
