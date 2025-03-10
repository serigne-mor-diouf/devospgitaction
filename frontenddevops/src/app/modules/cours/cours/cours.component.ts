import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import Swal from 'sweetalert2';
import { Cours, PageCours, Professeur } from '../models/cours';
import { CoursService } from '../services/cours.service';
import { Classe } from '../../classe/models/classe';
import { ClasseService } from '../../classe/services/classe.service';
import { ProfesseurService } from '../../professeur/services/professeur.service';

@Component({
  selector: 'app-cours',
  templateUrl: './cours.component.html',
  styleUrls: ['./cours.component.scss']
})
export class CoursComponent implements OnInit {
  @Input() isNewCours: boolean = false;
  @Output() coursSelected = new EventEmitter<Cours>();
  
  // Propriétés principales
  cours: Cours[] = [];
  professeurs: Professeur[] = [];
  classes: Classe[] = [];
  selectedCours: Cours | null = null;
  isEditing: boolean = false;

  // Propriétés du formulaire
  coursForm: FormGroup;
  searchTerm: string = '';

  // Propriétés de pagination
  page: number = 1;
  perPage: number = 10;
  totalItems: number = 0;

  // États
  loading: boolean = false;
  Math = Math;

  // Statistiques
  totalEnCours: number = 0;
  totalAVenir: number = 0;
  totalTermine: number = 0;

  constructor(
    private coursService: CoursService,
    private professeurService: ProfesseurService,
    private classeService: ClasseService,
    private modalService: NgbModal,
    private fb: FormBuilder
  ) {
    this.coursForm = this.createForm();
  }

  ngOnInit(): void {
    if (this.isNewCours) {
      this.isEditing = true;
    } else {
      this.loadCours();
    }
    this.loadInitialData();
  }

  // Chargement des données
  loadInitialData() {
    this.loadCours();
    this.loadProfesseurs();
    this.loadClasses();
    this.updateStatistics();
  }

  loadCours() {
    this.coursService.getAllCoursPagines(0, 10).subscribe({
      next: (data) => {
        this.cours = Array.isArray(data) ? data : (data.content || []);
        this.totalItems = Array.isArray(data) ? data.length : (data.totalElements || 0);
        this.updateStatistics();
      },
      error: (error) => {
        console.error('Erreur lors du chargement des cours:', error);
        this.cours = [];
      }
    });
  }

  loadProfesseurs() {
    this.professeurService.getAllProfesseurs().subscribe({
      next: (data) => this.professeurs = data,
      error: () => this.showError('Erreur', 'Impossible de charger les professeurs')
    });
  }

  loadClasses() {
    this.classeService.getAllClasses().subscribe({
      next: (data) => this.classes = data,
      error: () => this.showError('Erreur', 'Impossible de charger les classes')
    });
  }

  // Gestion du formulaire
  createForm(cours?: Cours): FormGroup {
    return this.fb.group({
      nom: [cours?.nom || '', [Validators.required]],
      code: [cours?.code || '', [Validators.required]],
      description: [cours?.description || ''],
      dateDebut: [cours?.dateDebut || '', [Validators.required]],
      dateFin: [cours?.dateFin || '', [Validators.required]],
      volumeHoraire: [cours?.volumeHoraire || '', [Validators.required]],
      coefficient: [cours?.coefficient || '', [Validators.required]],
      professeur: [cours?.professeur?.id || '', [Validators.required]],
      classe: [cours?.classe?.id || '', [Validators.required]]
    });
  }

  // Actions utilisateur
  onSearch() {
    if (this.searchTerm.length >= 3) {
      this.coursService.getCoursByCode(this.searchTerm)
        .subscribe({
          next: (cours) => {
            if (cours) {
              this.cours = [cours];
              this.totalItems = 1;
            }
          },
          error: () => this.loadCours()
        });
    } else if (this.searchTerm.length === 0) {
      this.loadCours();
    }
  }

  openModal(content: any) {
    this.modalService.open(content, {
      size: 'lg',
      centered: true,
      backdrop: 'static'
    });
  }

  openDetailsModal(content: any) {
    this.modalService.open(content, {
      size: 'lg',
      centered: true,
      backdrop: 'static'
    });
  }

  creerModifierCours() {
    if (this.coursForm.valid) {
      const coursData = this.coursForm.value;
      const operation = this.selectedCours ? 
        this.coursService.updateCours({ ...coursData, id: this.selectedCours.id }) :
        this.coursService.createCours(coursData);

      operation.subscribe({
        next: () => {
          this.showSuccess(
            this.selectedCours ? 'Cours modifié' : 'Cours créé',
            this.selectedCours ? 'Le cours a été modifié avec succès' : 'Le cours a été créé avec succès'
          );
          this.modalService.dismissAll();
          this.loadCours();
        },
        error: (error) => {
          this.showError('Erreur', error.error.message || 'Une erreur est survenue');
        }
      });
    }
  }

  deleteCours(cours: Cours, event?: Event) {
    if (event) {
      event.stopPropagation();
    }
    if (confirm('Êtes-vous sûr de vouloir supprimer ce cours ?')) {
      this.coursService.deleteCours(cours.id).subscribe({
        next: () => {
          this.showSuccess('Succès', 'Cours supprimé avec succès');
          this.loadCours();
        },
        error: (error) => {
          this.showError('Erreur', error.error.message || 'Erreur lors de la suppression');
        }
      });
    }
  }

  // Méthodes utilitaires
  getProfesseurNom(professeur: any): string {
    if (!professeur) return '';
    return `${professeur.nom} ${professeur.prenom}`;
  }

  getClasseNom(classe: any): string {
    if (!classe) return '';
    return classe.nom;
  }

  getStatusCours(cours: Cours): { text: string; class: string; icon: string } {
    const now = new Date();
    const debut = new Date(cours.dateDebut);
    const fin = new Date(cours.dateFin);

    if (now < debut) {
      return { 
        text: 'À venir', 
        class: 'bg-info',
        icon: 'fa-clock'
      };
    } else if (now > fin) {
      return { 
        text: 'Terminé', 
        class: 'bg-secondary',
        icon: 'fa-check-circle'
      };
    } else {
      return { 
        text: 'En cours', 
        class: 'bg-success',
        icon: 'fa-play-circle'
      };
    }
  }

  updateStatistics() {
    if (!this.cours || !Array.isArray(this.cours)) {
      console.warn('Pas de cours disponibles pour les statistiques');
      return;
    }

    this.totalEnCours = this.cours.filter(c => 
      this.getStatusCours(c).text === 'En cours'
    ).length;
    this.totalAVenir = this.cours.filter(c => 
      this.getStatusCours(c).text === 'À venir'
    ).length;
    this.totalTermine = this.cours.filter(c => 
      this.getStatusCours(c).text === 'Terminé'
    ).length;
  }

  // Notifications
  showSuccess(title: string, message: string) {
    Swal.fire(title, message, 'success');
  }

  showError(title: string, message: string) {
    Swal.fire(title, message, 'error');
  }

  // Pagination
  pageChanged(event: number) {
    this.page = event;
    this.loadCours();
  }

  // Export
  exportToPDF() {
    // À implémenter
    this.showSuccess('Export PDF', 'Fonctionnalité à venir');
  }

  exportToExcel() {
    // À implémenter
    this.showSuccess('Export Excel', 'Fonctionnalité à venir');
  }

  onSelect(cours: Cours) {
    this.selectedCours = cours;
    this.coursSelected.emit(cours);
  }

  viewDetails(cours: Cours, event: Event, content: any) {
    event.stopPropagation();
    this.selectedCours = cours;
    this.modalService.open(content, {
      size: 'lg',
      centered: true,
      backdrop: 'static'
    });
  }

  editCours(cours: Cours, event?: Event) {
    if (event) {
      event.stopPropagation();
    }
    this.selectedCours = cours;
    this.isEditing = true;
    this.coursForm.patchValue({
      code: cours.code,
      nom: cours.nom,
      description: cours.description,
      dateDebut: cours.dateDebut,
      dateFin: cours.dateFin,
      volumeHoraire: cours.volumeHoraire,
      coefficient: cours.coefficient,
      professeur: cours.professeur?.id,
      classe: cours.classe?.id
    });
  }

  saveCours() {
    if (this.coursForm.valid) {
      const coursData = this.coursForm.value;
      if (this.selectedCours?.id) {
        this.coursService.updateCours({ ...coursData, id: this.selectedCours.id }).subscribe({
          next: () => {
            this.loadCours();
            this.isEditing = false;
            this.selectedCours = null;
            this.coursForm.reset();
          },
          error: (error) => console.error('Erreur lors de la mise à jour:', error)
        });
      } else {
        this.coursService.createCours(coursData).subscribe({
          next: () => {
            this.loadCours();
            this.isEditing = false;
            this.coursForm.reset();
          },
          error: (error) => console.error('Erreur lors de la création:', error)
        });
      }
    }
  }

  cancelEdit() {
    this.isEditing = false;
    this.selectedCours = null;
    this.coursForm.reset();
  }

  startNewCours() {
    this.selectedCours = null;
    this.isEditing = true;
    this.coursForm.reset();
  }
}