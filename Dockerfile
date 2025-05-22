# Utiliser une image Java officielle légère basée sur Alpine
FROM eclipse-temurin:17-jdk-alpine

# Définir les options JVM pour allouer 512 Mo au démarrage et 750 Mo max
ENV JAVA_OPTS="-Xms512m -Xmx750m"

# Copier le fichier JAR dans le conteneur
COPY spring.jar /app.jar

# Exposer le port utilisé par l’application
EXPOSE 8080

# Commande d'exécution avec utilisation de JAVA_OPTS
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar
