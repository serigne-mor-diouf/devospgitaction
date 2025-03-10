export interface Professeur {
    id: number;
    nom: string;
    prenom: string;
    email: string;
    telephone: string;
    departementId?: number;
    specialiteId?: number;
  }
  
  export interface ProfesseurDTO {
    nom: string;
    prenom: string;
    email: string;
    telephone: string;
    departementId?: number;
    specialiteId?: number;
  }