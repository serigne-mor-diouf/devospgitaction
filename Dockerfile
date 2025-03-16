# Utiliser une image de base avec OpenJDK
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR généré
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Exposer le port de l'application
EXPOSE 8080

# Définir le point d'entrée
ENTRYPOINT ["java", "-jar", "app.jar"]
