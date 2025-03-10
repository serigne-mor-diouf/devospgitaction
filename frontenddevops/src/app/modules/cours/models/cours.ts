export interface Professeur {
    id: number;
    nom: string;
    prenom: string;
    // Ajoutez d'autres propriétés du professeur si nécessaire
  }
  
  export interface Cours {
    id: number;
    code: string;
    nom: string;
    description?: string;
    dateDebut: Date | string;
    dateFin: Date | string;
    volumeHoraire: number;
    coefficient: number;
    professeur?: {
      id: number;
      nom: string;
      prenom: string;
      email: string;
    };
    classe?: {
      id: number;
      nom: string;
      niveau: string;
    };
  }
  
  export interface PageCours {
    content: Cours[];
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
  }