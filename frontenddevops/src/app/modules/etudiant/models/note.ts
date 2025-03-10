export interface Note {
    id?: number;
    etudiantId: number;
    coursId: number;
    note: number;
    semestre: string;
    dateEvaluation: string;
    observation?: string;
    statut: 'EN_ATTENTE' | 'VALIDEE' | 'ANNULEE';
    etudiant?: any;
    cours?: any;
    anneeAcademique?: {
        id: number;
        nom: string;
    };
} 