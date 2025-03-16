# Étape 1 : Construire l'application avec Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copier le projet Maven dans le conteneur
COPY . .

# Compiler le projet
RUN mvn clean package -DskipTests

# Étape 2 : Construire l'image finale avec OpenJDK
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copier le fichier JAR généré depuis l'étape de build
COPY --from=builder /app/target/*.jar app.jar

# Exposer le port dynamique de l'application
EXPOSE ${PORT}

# Démarrer l'application
CMD ["java", "-jar", "app.jar"]
