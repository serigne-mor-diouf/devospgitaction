export interface Inscription {
    id?: number;
    etudiantId: number;
    anneeAcademiqueId: number;
    dateInscription: string;
    statut: string;
    fraisInscription: number;
    fraisScolarite: number;
    montantVerse: number;
    observation: string;
    etudiant?: any;
    anneeAcademique?: any;
} 