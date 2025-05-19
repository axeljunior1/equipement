# Utiliser une image Java officielle
FROM eclipse-temurin:17-jdk-alpine

ENV JAVA_OPTS="-Xms128m -Xmx256m"

# Copier le fichier JAR dans le conteneur
COPY spring.jar /app.jar

# Exposer le port 8080
EXPOSE 8080

# Commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "/app.jar"]
