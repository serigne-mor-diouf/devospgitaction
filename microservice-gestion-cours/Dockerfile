# Utilisation de l'image de base OpenJDK 17
FROM openjdk:17-jdk-slim

# Définir un volume temporaire pour stocker les fichiers
VOLUME /tmp

# Ajouter le fichier JAR dans l'image Docker
COPY target/microservice-cours-0.0.1-SNAPSHOT.jar /microservice-cours.jar

# Commande par défaut pour démarrer l'application Spring Boot
CMD ["java", "-jar", "/microservice-cours.jar", "--spring.profiles.active=dev"]

# Exposer le port 9004 pour ce microservice
EXPOSE 9001
