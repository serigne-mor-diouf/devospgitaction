import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InscriptionService } from '../../services/inscription.service';
import { EtudiantService } from '../../services/etudiant.service';
import { Inscription } from '../../models/inscription';

@Component({
  selector: 'app-inscription',
  templateUrl: './inscription.component.html',
  styleUrls: ['./inscription.component.scss']
})
export class InscriptionComponent implements OnInit {
  @Input() inscriptions: Inscription[] = [];
  @Output() edit = new EventEmitter<Inscription>();
  @Output() delete = new EventEmitter<Inscription>();
  @Output() view = new EventEmitter<Inscription>();

  @ViewChild('inscriptionModal') inscriptionModal: any;

  totalInscriptions = 0;
  totalValidees = 0;
  totalEnAttente = 0;
  totalFrais = 0;

  inscriptionForm: FormGroup;
  selectedInscription: Inscription | null = null;
  etudiants: any[] = [];
  anneesAcademiques: any[] = [];
  isEditing = false;
  searchTerm = '';
  page = 1;
  pageSize = 10;
  totalItems = 0;
  selectedAnneeId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private inscriptionService: InscriptionService,
    private etudiantService: EtudiantService,
    private modalService: NgbModal
  ) {
    this.inscriptionForm = this.createForm();
  }

  ngOnInit(): void {
    this.loadInscriptions();
    this.loadEtudiants();
  }

  createForm(): FormGroup {
    return this.fb.group({
      etudiantId: ['', Validators.required],
      dateInscription: ['', Validators.required],
      statut: ['', Validators.required],
      fraisInscription: ['', [Validators.required, Validators.min(0)]],
      fraisScolarite: ['', [Validators.required, Validators.min(0)]],
      montantVerse: ['', [Validators.required, Validators.min(0)]],
      observation: ['']
    });
  }

  loadInscriptions() {
    this.inscriptionService.getAllInscriptionsPaginees(this.page - 1, this.pageSize)
      .subscribe({
        next: (response) => {
          this.inscriptions = response.content;
          this.totalItems = response.totalElements;
        },
        error: (error) => console.error('Erreur lors du chargement des inscriptions:', error)
      });
  }

  loadEtudiants() {
    this.etudiantService.getAllEtudiants()
      .subscribe({
        next: (etudiants) => this.etudiants = etudiants,
        error: (error) => console.error('Erreur lors du chargement des étudiants:', error)
      });
  }

  loadAnneesAcademiques(id: number) {
    this.inscriptionService.getInscriptionsByAnneeAcademique(id).subscribe({
      next: (inscriptions: Inscription[]) => {
        // Extraire les années académiques uniques des inscriptions
        const anneesSet = new Set(inscriptions.map(insc => insc.anneeAcademique));
        this.anneesAcademiques = Array.from(anneesSet);
        
        if (this.anneesAcademiques.length > 0) {
          this.selectedAnneeId = id;
          this.inscriptions = inscriptions;
        }
      },
      error: (error) => {
        console.error('Erreur lors du chargement des années académiques:', error);
        // En cas d'erreur, on peut charger toutes les inscriptions
        this.loadInscriptions();
      }
    });
  }

  openModal(content: any) {
    this.modalService.open(content, {
      size: 'lg',
      centered: true,
      backdrop: 'static'
    });
  }

  onSubmit() {
    if (this.inscriptionForm.valid) {
      const inscriptionData = this.inscriptionForm.value;
      
      if (this.selectedInscription?.id) {
        this.inscriptionService.updateInscription(this.selectedInscription.id, inscriptionData)
          .subscribe({
            next: () => {
              this.loadInscriptions();
              this.resetForm();
              this.modalService.dismissAll();
            },
            error: (error) => console.error('Erreur lors de la mise à jour:', error)
          });
      } else {
        this.inscriptionService.createInscription(inscriptionData)
          .subscribe({
            next: () => {
              this.loadInscriptions();
              this.resetForm();
              this.modalService.dismissAll();
            },
            error: (error) => console.error('Erreur lors de la création:', error)
          });
      }
    }
  }

  editInscription(inscription: Inscription) {
    this.selectedInscription = inscription;
    this.inscriptionForm.patchValue({
      etudiantId: inscription.etudiantId,
      dateInscription: inscription.dateInscription,
      statut: inscription.statut,
      fraisInscription: inscription.fraisInscription,
      fraisScolarite: inscription.fraisScolarite,
      montantVerse: inscription.montantVerse,
      observation: inscription.observation
    });
    this.isEditing = true;
    this.modalService.open(this.inscriptionModal, {
      size: 'lg',
      centered: true
    });
  }

  deleteInscription(inscription: Inscription) {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette inscription ?')) {
      this.inscriptionService.deleteInscription(inscription.id!)
        .subscribe({
          next: () => this.loadInscriptions(),
          error: (error) => console.error('Erreur lors de la suppression:', error)
        });
    }
  }

  calculateSolde(etudiantId: number, anneeAcademiqueId: number) {
    this.inscriptionService.calculateSolde(etudiantId, anneeAcademiqueId)
      .subscribe({
        next: (solde) => {
          // Afficher le solde dans l'interface
        },
        error: (error) => console.error('Erreur lors du calcul du solde:', error)
      });
  }

  resetForm() {
    this.inscriptionForm.reset();
    this.selectedInscription = null;
    this.isEditing = false;
  }

  onSearch() {
    // Implement search logic
  }

  getSolde(inscription: Inscription): number {
    return (inscription.fraisInscription + inscription.fraisScolarite) - inscription.montantVerse;
  }

  exportToExcel() {
    // Implement export logic
  }

  exportToPDF() {
    // Implement export logic
  }

  viewDetails(inscription: Inscription, content: any) {
    this.selectedInscription = inscription;
    this.modalService.open(content, {
      size: 'lg',
      centered: true
    });
  }

  onAnneeChange(anneeId: number) {
    this.selectedAnneeId = anneeId;
    this.inscriptionService.getInscriptionsByAnneeAcademique(anneeId).subscribe({
      next: (inscriptions) => {
        this.inscriptions = inscriptions;
      },
      error: (error) => console.error('Erreur lors du chargement des inscriptions:', error)
    });
  }
} 