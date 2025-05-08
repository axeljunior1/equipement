FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY backend.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
