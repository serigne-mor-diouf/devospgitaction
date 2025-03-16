# Utiliser une image de base avec OpenJDK
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Créer un répertoire target si nécessaire
RUN mkdir -p target

# Copier le fichier .jar généré ou créer un fichier vide si aucun n'existe
COPY target/*.jar app.jar 2>/dev/null || echo "Aucun fichier JAR trouvé, création d'un fichier vide" && touch app.jar

# Exposer le port sur lequel l'application va tourner
EXPOSE 8080

# Variables d'environnement pour la configuration
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8080

# Définir le point d'entrée pour exécuter l'application Java
ENTRYPOINT ["java", "-jar", "app.jar"]