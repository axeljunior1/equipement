# Utilisation d'OpenJDK comme base
FROM openjdk:17

# Définition du répertoire de travail
WORKDIR /app

# Copier uniquement les fichiers nécessaires pour l’installation des dépendances
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Installer les dépendances Maven sans exécuter l'application
RUN ./mvnw dependency:go-offline

# Copier le code source
COPY src ./src

# Construire l’application et générer le JAR
RUN ./mvnw clean package -DskipTests

# Copier le JAR compilé
COPY target/backend.jar backend.jar

# Exécuter le JAR
CMD ["java", "-jar", "backend.jar"]

# Exposer le port de Spring Boot
EXPOSE 8080
