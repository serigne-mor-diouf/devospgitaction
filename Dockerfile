# Étape 1 : Définir une image de base avec Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Définir le répertoire de travail
WORKDIR /app

# Copier tous les fichiers du projet (y compris pom.xml) dans le répertoire de travail
COPY . .

# Compiler le projet
RUN mvn clean package -DskipTests

# Étape 2 : Construire l'image finale avec OpenJDK
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copier le fichier JAR généré depuis l'étape de build
COPY --from=builder /app/target/*.jar app.jar

# Exposer le port dynamique de l'application
EXPOSE 8080

# Démarrer l'application
CMD ["java", "-jar", "app.jar"]
