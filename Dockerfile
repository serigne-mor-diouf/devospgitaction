# FROM openjdk:17-jdk-slim

# WORKDIR /app

# # Copier le fichier .jar généré dans le répertoire build/libs/
# COPY build/libs/*.jar app.jar

# # Exposer le port 8080
# EXPOSE 8080

# # Lancer l'application
# ENTRYPOINT ["java", "-jar", "app.jar"]
