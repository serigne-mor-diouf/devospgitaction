# Utilisation de l'image de base OpenJDK 11
FROM openjdk:11

# Définir un volume temporaire pour stocker les fichiers
VOLUME /tmp

# Copier le fichier JAR généré dans l'image Docker
COPY target/microservice-emploi-du-temps-0.0.1-SNAPSHOT.jar /emploi-du-temps.jar

# Commande par défaut pour démarrer l'application Spring Boot avec le profil dev
CMD ["java", "-jar", "/emploi-du-temps.jar", "--spring.profiles.active=dev"]

# Exposer le port 9005 pour ce microservice
EXPOSE 9005
