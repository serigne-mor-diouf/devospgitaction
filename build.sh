#!/bin/bash

# Afficher le répertoire courant
echo "Current directory: $(pwd)"
ls -la

# Configuration de l'environnement
export SPRING_PROFILES_ACTIVE=prod

# Build des microservices
for service in microservice-gestion-*; do
    if [ -d "$service" ]; then
        echo "Building $service..."
        cd $service
        ./mvnw clean package -DskipTests
        cd ..
    fi
done

# Build du frontend
if [ -d "frontenddevops" ]; then
    echo "Building frontend..."
    cd frontenddevops
    npm install --legacy-peer-deps
    npm run build
    cd ..
fi

echo "Build completed" 