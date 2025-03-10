export const environment = {
  production: false,
  // URLs des microservices
  apiUrls: {
    // Gestion des classes
    microserviceclasse: "http://localhost:9004/gestion-classes/",
    
    // Gestion des cours
    microservicecour: "http://localhost:9001/gestion-cours/",
    
    // Gestion des professeurs
    microserviceprofesseur: "http://localhost:9002/gestion-pofesseurs/",

    // Gestion des étudiants
    microserviceetudiant: "http://localhost:9003/gestion-etudiants/",
    
    // Gestion des emplois du temps
    microserviceemploidutemp: "http://localhost:9005/gestion-emplois/"
  }
}; 