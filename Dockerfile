# Utiliser une image de base avec OpenJDK
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Vérifier si le fichier JAR existe avant de le copier
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Exposer le port sur lequel l'application va tourner
EXPOSE 8080

# Variables d'environnement pour la configuration
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8080

# Définir le point d'entrée pour exécuter l'application Java
ENTRYPOINT ["java", "-jar", "app.jar"]
