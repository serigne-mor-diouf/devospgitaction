#!/bin/bash

# Charger les variables d'environnement
set -a
source .env
set +a

# Création du répertoire de base
mkdir -p /opt/render/project/src/

# Copie de tous les fichiers du projet
cp -r . /opt/render/project/src/

# Construction des microservices
cd /opt/render/project/src/

# Liste des microservices
services=(
    "microservice-gestion-cours"
    "microservice-gestion-prof"
    "microservice-gestion-etudiant"
    "microservice-gestion-classe"
    "microservice-gestion-emploi-du-temp"
)

# Build de chaque microservice
for service in "${services[@]}"; do
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
    npm install
    npm run build
    cd ..
fi

echo "All microservices built successfully!" 