# Utiliser une image de base avec OpenJDK
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier .jar généré dans le répertoire target/
COPY target/*.jar app.jar

# Exposer le port sur lequel l'application va tourner
EXPOSE 8080

# Définir le point d'entrée pour exécuter l'application Java
ENTRYPOINT ["java", "-jar", "app.jar"]
