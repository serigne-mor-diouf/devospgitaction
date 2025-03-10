import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EtudiantService } from '../../services/etudiant.service';
import { InscriptionService } from '../../services/inscription.service';
import { NoteService } from '../../services/note.service';
import { Etudiant } from '../../models/etudiant';
import { Inscription } from '../../models/inscription';
import { Note } from '../../models/note';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-etudiant-dashboard',
  templateUrl: './etudiant-dashboard.component.html',
  styleUrls: ['./etudiant-dashboard.component.scss']
})
export class EtudiantDashboardComponent implements OnInit {
  // États généraux
  activeTab = 'etudiants'; // 'etudiants' | 'inscriptions' | 'notes'
  searchTerm = '';
  isLoading = false;

  // Données
  etudiants: Etudiant[] = [];
  inscriptions: Inscription[] = [];
  notes: Note[] = [];
  cours: any[] = [];
  anneesAcademiques: any[] = [];
  selectedEtudiant: Etudiant | null = null;
  selectedInscription: Inscription | null = null;
  selectedNote: Note | null = null;

  // Formulaires
  etudiantForm!: FormGroup;
  inscriptionForm!: FormGroup;
  noteForm!: FormGroup;

  // Pagination
  page = 1;
  pageSize = 10;
  totalItems = 0;

  // Statistiques
  stats = {
    totalEtudiants: 0,
    totalInscrits: 0,
    totalNotes: 0,
    moyenneGenerale: 0
  };

  // Propriétés pour les notifications
  notifications: any[] = [];
  showNotifications = false;

  constructor(
    private fb: FormBuilder,
    private etudiantService: EtudiantService,
    private inscriptionService: InscriptionService,
    private noteService: NoteService,
    private modalService: NgbModal
  ) {
    this.initializeForms();
  }

  ngOnInit(): void {
    this.loadData();
    this.loadNotifications();
  }

  private initializeForms() {
    this.etudiantForm = this.fb.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      matricule: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telephone: ['', Validators.required],
      dateNaissance: ['', Validators.required],
      adresse: ['', Validators.required],
      lieuNaissance: ['', Validators.required],
      nationalite: ['', Validators.required],
      genre: ['', Validators.required],
      classeId: ['', Validators.required]
    });

    this.inscriptionForm = this.fb.group({
      etudiantId: ['', Validators.required],
      anneeAcademiqueId: ['', Validators.required],
      dateInscription: ['', Validators.required],
      statut: ['', Validators.required],
      fraisInscription: ['', [Validators.required, Validators.min(0)]],
      fraisScolarite: ['', [Validators.required, Validators.min(0)]],
      montantVerse: ['', [Validators.required, Validators.min(0)]],
      observation: ['']
    });

    this.noteForm = this.fb.group({
      etudiantId: ['', Validators.required],
      coursId: ['', Validators.required],
      note: ['', [Validators.required, Validators.min(0), Validators.max(20)]],
      dateEvaluation: ['', Validators.required],
      type: ['', Validators.required],
      semestre: ['', Validators.required],
      observation: ['']
    });
  }

  loadData() {
    this.loadStats();
    this.loadActiveTabData();
  }

  loadStats() {
    this.etudiantService.getStats().subscribe(stats => {
      this.stats = stats;
    });
  }

  loadActiveTabData() {
    switch (this.activeTab) {
      case 'etudiants':
        this.loadEtudiants();
        break;
      case 'inscriptions':
        this.loadInscriptions();
        break;
      case 'notes':
        this.loadNotes();
        break;
    }
  }

  loadEtudiants() {
    this.etudiantService.getEtudiants(this.page, this.pageSize, this.searchTerm).subscribe(data => {
      this.etudiants = data.items;
      this.totalItems = data.total;
    });
  }

  loadInscriptions() {
    this.inscriptionService.getInscriptions(this.page, this.pageSize, this.searchTerm).subscribe(data => {
      this.inscriptions = data.items;
      this.totalItems = data.total;
    });
  }

  loadNotes() {
    this.noteService.getNotes(this.page, this.pageSize, this.searchTerm).subscribe(data => {
      this.notes = data.items;
      this.totalItems = data.total;
    });
  }

  onSearch() {
    this.page = 1;
    this.loadActiveTabData();
  }

  onPageChange() {
    this.loadActiveTabData();
  }

  openModal(content: any) {
    this.modalService.open(content, { size: 'lg', centered: true });
  }

  // Méthodes de gestion des événements
  handleEdit(entity: any, type: string) {
    switch (type) {
      case 'etudiant':
        this.editEtudiant(entity);
        break;
      case 'inscription':
        this.editInscription(entity);
        break;
      case 'note':
        this.editNote(entity);
        break;
    }
  }

  handleDelete(entity: any, type: string) {
    switch (type) {
      case 'etudiant':
        this.deleteEtudiant(entity);
        break;
      case 'inscription':
        this.deleteInscription(entity);
        break;
      case 'note':
        this.deleteNote(entity);
        break;
    }
  }

  handleView(entity: any, type: string) {
    switch (type) {
      case 'etudiant':
        this.viewEtudiant(entity);
        break;
      case 'inscription':
        this.viewInscription(entity);
        break;
      case 'note':
        this.viewNote(entity);
        break;
    }
  }

  editEtudiant(etudiant: Etudiant) {
    this.selectedEtudiant = etudiant;
    this.etudiantForm.patchValue({
      nom: etudiant.nom,
      prenom: etudiant.prenom,
      matricule: etudiant.matricule,
      email: etudiant.email,
      telephone: etudiant.telephone,
      dateNaissance: etudiant.dateNaissance,
      adresse: etudiant.adresse,
      lieuNaissance: etudiant.lieuNaissance,
      nationalite: etudiant.nationalite,
      genre: etudiant.genre,
      classeId: etudiant.classeId
    });
    this.openModal('etudiantModal');
  }

  deleteEtudiant(etudiant: Etudiant) {
    if (!etudiant?.id) return;

    Swal.fire({
      title: 'Êtes-vous sûr?',
      text: `Voulez-vous vraiment supprimer l'étudiant ${etudiant.prenom} ${etudiant.nom}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui, supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.etudiantService.deleteEtudiant(etudiant.id as number).subscribe({
          next: () => {
            this.loadEtudiants();
            Swal.fire('Supprimé!', 'L\'étudiant a été supprimé avec succès.', 'success');
          },
          error: (error) => {
            console.error('Erreur lors de la suppression:', error);
            Swal.fire('Erreur!', 'Une erreur est survenue lors de la suppression.', 'error');
          }
        });
      }
    });
  }

  viewEtudiant(etudiant: Etudiant) {
    this.selectedEtudiant = etudiant;
    this.openModal('etudiantDetailsModal');
  }

  editInscription(inscription: Inscription) {
    this.inscriptionForm.patchValue({
      etudiantId: inscription.etudiantId,
      anneeAcademiqueId: inscription.anneeAcademiqueId,
      dateInscription: inscription.dateInscription,
      statut: inscription.statut,
      fraisInscription: inscription.fraisInscription,
      fraisScolarite: inscription.fraisScolarite,
      montantVerse: inscription.montantVerse,
      observation: inscription.observation
    });
    this.openModal('inscriptionModal');
  }

  deleteInscription(inscription: Inscription) {
    if (!inscription?.id) return;

    Swal.fire({
      title: 'Êtes-vous sûr?',
      text: 'Voulez-vous vraiment supprimer cette inscription?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui, supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.inscriptionService.deleteInscription(inscription.id as number).subscribe({
          next: () => {
            this.loadInscriptions();
            Swal.fire('Supprimé!', 'L\'inscription a été supprimée avec succès.', 'success');
          },
          error: (error) => {
            console.error('Erreur lors de la suppression:', error);
            Swal.fire('Erreur!', 'Une erreur est survenue lors de la suppression.', 'error');
          }
        });
      }
    });
  }

  viewInscription(inscription: Inscription) {
    if (!inscription?.id) return;
    this.inscriptionService.getInscriptionDetails(inscription.id).subscribe({
      next: (details) => {
        this.selectedInscription = details;
        this.openModal('inscriptionDetailsModal');
      },
      error: (error) => {
        console.error('Erreur lors du chargement des détails:', error);
        Swal.fire('Erreur!', 'Impossible de charger les détails.', 'error');
      }
    });
  }

  editNote(note: Note) {
    this.noteForm.patchValue({
      etudiantId: note.etudiantId,
      coursId: note.coursId,
      note: note.note,
      dateEvaluation: note.dateEvaluation,
      semestre: note.semestre,
      observation: note.observation
    });
    this.openModal('noteModal');
  }

  deleteNote(note: Note) {
    if (!note?.id) return;

    Swal.fire({
      title: 'Êtes-vous sûr?',
      text: 'Voulez-vous vraiment supprimer cette note?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui, supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.noteService.deleteNote(note.id as number).subscribe({
          next: () => {
            this.loadNotes();
            Swal.fire('Supprimé!', 'La note a été supprimée avec succès.', 'success');
          },
          error: (error) => {
            console.error('Erreur lors de la suppression:', error);
            Swal.fire('Erreur!', 'Une erreur est survenue lors de la suppression.', 'error');
          }
        });
      }
    });
  }

  viewNote(note: Note) {
    if (!note?.id) return;
    this.noteService.getNote(note.id).subscribe({
      next: (details) => {
        this.selectedNote = details;
        this.openModal('noteDetailsModal');
      },
      error: (error) => {
        console.error('Erreur lors du chargement des détails:', error);
        Swal.fire('Erreur!', 'Impossible de charger les détails.', 'error');
      }
    });
  }

  exportData(type: 'excel' | 'pdf') {
    this.isLoading = true;
    const service = this.getExportService(type);
    
    service.subscribe({
      next: (response: any) => {
        const blob = new Blob([response], { 
          type: type === 'excel' ? 'application/vnd.ms-excel' : 'application/pdf' 
        });
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = `export_${this.activeTab}_${new Date().getTime()}.${type}`;
        link.click();
        window.URL.revokeObjectURL(url);
        this.isLoading = false;
        Swal.fire('Succès!', `Export ${type.toUpperCase()} réussi.`, 'success');
      },
      error: (error) => {
        console.error('Erreur lors de l\'export:', error);
        this.isLoading = false;
        Swal.fire('Erreur!', `Une erreur est survenue lors de l'export ${type.toUpperCase()}.`, 'error');
      }
    });
  }

  private getExportService(type: 'excel' | 'pdf') {
    switch (this.activeTab) {
      case 'etudiants':
        return type === 'excel' ? 
          this.etudiantService.exportToExcel() : 
          this.etudiantService.exportToPDF();
      case 'inscriptions':
        return type === 'excel' ? 
          this.inscriptionService.exportToExcel() : 
          this.inscriptionService.exportToPDF();
      case 'notes':
        return type === 'excel' ? 
          this.noteService.exportToExcel() : 
          this.noteService.exportToPDF();
      default:
        throw new Error('Tab non reconnu');
    }
  }

  // Méthodes de soumission des formulaires
  submitEtudiant() {
    if (this.etudiantForm.valid) {
      const etudiantData = this.etudiantForm.value;
      if (this.selectedEtudiant?.id) {
        this.updateEtudiant(this.selectedEtudiant.id, etudiantData);
      } else {
        this.createEtudiant(etudiantData);
      }
    }
  }

  submitInscription() {
    if (this.inscriptionForm.valid) {
      const inscriptionData = this.inscriptionForm.value;
      if (this.selectedInscription?.id) {
        this.updateInscription(this.selectedInscription.id, inscriptionData);
      } else {
        this.createInscription(inscriptionData);
      }
    }
  }

  submitNote() {
    if (this.noteForm.valid) {
      const noteData = this.noteForm.value;
      if (this.selectedNote?.id) {
        this.updateNote(this.selectedNote.id, noteData);
      } else {
        this.createNote(noteData);
      }
    }
  }

  // Méthodes CRUD
  createEtudiant(etudiantData: any) {
    this.isLoading = true;
    this.etudiantService.createEtudiant(etudiantData).subscribe({
      next: () => {
        this.loadEtudiants();
        this.resetForms();
        this.modalService.dismissAll();
        this.isLoading = false;
        Swal.fire('Succès!', 'Étudiant créé avec succès.', 'success');
      },
      error: (error) => {
        console.error('Erreur lors de la création:', error);
        this.isLoading = false;
        Swal.fire('Erreur!', 'Une erreur est survenue lors de la création.', 'error');
      }
    });
  }

  updateEtudiant(id: number, etudiantData: any) {
    this.isLoading = true;
    this.etudiantService.updateEtudiant(id, etudiantData).subscribe({
      next: () => {
        this.loadEtudiants();
        this.resetForms();
        this.modalService.dismissAll();
        this.isLoading = false;
        Swal.fire('Succès!', 'Étudiant mis à jour avec succès.', 'success');
      },
      error: (error) => {
        console.error('Erreur lors de la mise à jour:', error);
        this.isLoading = false;
        Swal.fire('Erreur!', 'Une erreur est survenue lors de la mise à jour.', 'error');
      }
    });
  }

  createInscription(inscriptionData: any) {
    this.inscriptionService.createInscription(inscriptionData).subscribe({
      next: () => {
        this.loadInscriptions();
        this.resetForms();
        this.modalService.dismissAll();
        Swal.fire('Succès!', 'Inscription créée avec succès.', 'success');
      },
      error: (error) => {
        console.error('Erreur lors de la création:', error);
        Swal.fire('Erreur!', 'Une erreur est survenue lors de la création de l\'inscription.', 'error');
      }
    });
  }

  updateInscription(id: number, inscriptionData: any) {
    this.inscriptionService.updateInscription(id, inscriptionData).subscribe({
      next: () => {
        this.loadInscriptions();
        this.resetForms();
        this.modalService.dismissAll();
      },
      error: (error) => console.error('Erreur lors de la mise à jour:', error)
    });
  }

  createNote(noteData: any) {
    this.noteService.createNote(noteData).subscribe({
      next: () => {
        this.loadNotes();
        this.resetForms();
        this.modalService.dismissAll();
      },
      error: (error) => console.error('Erreur lors de la création:', error)
    });
  }

  updateNote(id: number, noteData: any) {
    this.noteService.updateNote(id, noteData).subscribe({
      next: () => {
        this.loadNotes();
        this.resetForms();
        this.modalService.dismissAll();
      },
      error: (error) => console.error('Erreur lors de la mise à jour:', error)
    });
  }

  // Méthodes utilitaires
  calculateMoyenneGenerale() {
    if (!this.notes || this.notes.length === 0) return 0;
    
    const validNotes = this.notes.filter(note => 
      typeof note.note === 'number' && !isNaN(note.note)
    );
    
    if (validNotes.length === 0) return 0;
    
    const sum = validNotes.reduce((acc, note) => acc + note.note, 0);
    const moyenne = sum / validNotes.length;
    
    return parseFloat(moyenne.toFixed(2));
  }

  resetForms() {
    this.etudiantForm.reset();
    this.inscriptionForm.reset();
    this.noteForm.reset();
    this.selectedEtudiant = null;
    this.selectedInscription = null;
    this.selectedNote = null;
    this.searchTerm = '';
  }

  onTabChange(tab: string) {
    this.activeTab = tab;
    this.page = 1;
    this.searchTerm = '';
    this.resetForms();
    this.loadActiveTabData();
  }

  private handleError(error: any, message: string) {
    console.error(message, error);
    this.isLoading = false;
    Swal.fire('Erreur!', 'Une erreur est survenue. Veuillez réessayer.', 'error');
  }

  toggleNotifications() {
    this.showNotifications = !this.showNotifications;
  }

  // Méthode pour charger les notifications (à appeler dans ngOnInit)
  loadNotifications() {
    // Exemple de notifications statiques
    this.notifications = [
      {
        message: 'Nouvelle inscription ajoutée',
        date: new Date(),
        type: 'info'
      },
      {
        message: 'Notes du semestre publiées',
        date: new Date(),
        type: 'success'
      }
    ];
  }
} 