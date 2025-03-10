export interface Classe {
    id: number;
    code: string;
    nom: string;
    niveau: string;
    filiere: string;
    capaciteMax: number;
    anneeScolaire: string;
    responsableId?: number;
    description?: string;
    dateCreation?: Date;
  }
  
  export interface ClasseDTO {
    code: string;
    nom: string;
    niveau: string;
    filiere: string;
    capaciteMax: number;
    anneeScolaire: string;
    responsableId?: number;
    description?: string;
  }

  export interface ClasseResponse {
    content: Classe[];
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
  }