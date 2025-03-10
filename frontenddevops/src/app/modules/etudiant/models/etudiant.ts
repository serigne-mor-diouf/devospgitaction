export interface Etudiant {
    id?: number;
    nom: string;
    prenom: string;
    matricule: string;
    email: string;
    telephone: string;
    dateNaissance: string;
    adresse: string;
    lieuNaissance: string;
    nationalite: string;
    genre: string;
    classeId: number;
    classe?: any;
}
