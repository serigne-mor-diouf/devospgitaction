import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EtudiantService } from '../../services/etudiant.service';
import { Etudiant } from '../../models/etudiant';
import { ClasseService } from '../../services/classe.service';
import { Classe } from '../../models/classe';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-etudiant',
  templateUrl: './etudiant.component.html',
  styleUrls: ['./etudiant.component.scss']
})
export class EtudiantComponent implements OnInit {
  @Input() etudiants: Etudiant[] = [];
  @Output() edit = new EventEmitter<Etudiant>();
  @Output() delete = new EventEmitter<Etudiant>();
  @Output() view = new EventEmitter<Etudiant>();

  @ViewChild('inscriptionModal') inscriptionModal: any;
  @ViewChild('successModal') successModal: any;

  totalEtudiants = 0;
  totalActifs = 0;
  totalNouveaux = 0;
  moyenneParClasse = 0;
  classes: Classe[] = [];

  etudiantForm: FormGroup;
  selectedEtudiant: Etudiant | null = null;
  isEditing = false;
  searchTerm = '';
  totalItems = 0;
  page = 1;
  pageSize = 10;
  successMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private etudiantService: EtudiantService,
    private classeService: ClasseService,
    private modalService: NgbModal
  ) {
    this.etudiantForm = this.createForm();
  }

  ngOnInit(): void {
    this.loadEtudiants();
    this.loadClasses();
  }

  loadClasses() {
    this.classeService.getAllClasses().subscribe({
      next: (classes: Classe[]) => {
        this.classes = classes;
        console.log('Classes chargées:', classes);
      },
      error: (error: Error) => {
        console.error('Erreur lors du chargement des classes:', error);
      }
    });
  }

  createForm(): FormGroup {
    return this.fb.group({
      nom: ['', [Validators.required, Validators.minLength(2)]],
      prenom: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email, Validators.pattern('^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$')]],
      telephone: ['', [Validators.required, Validators.pattern('^[0-9]{9,}$')]],
      dateNaissance: ['', Validators.required],
      lieuNaissance: ['', Validators.required],
      adresse: ['', Validators.required],
      nationalite: ['', Validators.required],
      genre: ['', Validators.required],
      classeId: ['', Validators.required]
    });
  }

  loadEtudiants() {
    this.etudiantService.getAllEtudiantsPagines(this.page - 1, this.pageSize)
      .subscribe({
        next: (response) => {
          this.etudiants = response.content;
          this.totalItems = response.totalElements;
        },
        error: (error) => console.error('Erreur lors du chargement des étudiants:', error)
      });
  }

  onSearch() {
    if (this.searchTerm.trim()) {
      this.etudiantService.searchEtudiants(this.searchTerm)
        .subscribe({
          next: (etudiants) => this.etudiants = etudiants,
          error: (error) => console.error('Erreur lors de la recherche:', error)
        });
    } else {
      this.loadEtudiants();
    }
  }

  openModal(content: any) {
    this.resetForm();
    this.modalService.open(content, {
      size: 'lg',
      centered: true,
      backdrop: 'static'
    });
  }

  viewDetails(etudiant: Etudiant, content: any) {
    this.selectedEtudiant = etudiant;
    this.modalService.open(content, {
      size: 'lg',
      centered: true
    });
  }

  editEtudiant(etudiant: Etudiant) {
    this.selectedEtudiant = etudiant;
    this.etudiantForm.patchValue({
      nom: etudiant.nom,
      prenom: etudiant.prenom,
      email: etudiant.email,
      telephone: etudiant.telephone,
      dateNaissance: etudiant.dateNaissance,
      lieuNaissance: etudiant.lieuNaissance,
      adresse: etudiant.adresse,
      nationalite: etudiant.nationalite,
      genre: etudiant.genre,
      classeId: etudiant.classeId
    });
    this.isEditing = true;
    this.modalService.open(this.inscriptionModal, {
      size: 'lg',
      centered: true
    });
  }

  deleteEtudiant(etudiant: Etudiant) {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet étudiant ?')) {
      this.etudiantService.deleteEtudiant(etudiant.id!)
        .subscribe({
          next: () => {
            this.loadEtudiants();
          },
          error: (error) => console.error('Erreur lors de la suppression:', error)
        });
    }
  }

  onSubmit() {
    if (this.etudiantForm.valid) {
      const etudiantData = {
        ...this.etudiantForm.value,
        matricule: this.generateMatricule(),
      };

      if (this.selectedEtudiant?.id) {
        // Confirmation de modification
        Swal.fire({
          title: 'Êtes-vous sûr ?',
          text: 'Voulez-vous modifier les informations de cet étudiant ?',
          icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#34c38f',
          cancelButtonColor: '#f46a6a',
          confirmButtonText: 'Oui, modifier !',
          cancelButtonText: 'Non, annuler'
        }).then((result) => {
          if (result.isConfirmed && this.selectedEtudiant && this.selectedEtudiant.id) {
            this.etudiantService.updateEtudiant(this.selectedEtudiant.id, etudiantData)
              .subscribe({
                next: () => {
                  this.loadEtudiants();
                  this.resetForm();
                  this.modalService.dismissAll();
                  Swal.fire('Succès !', 'Étudiant modifié avec succès !', 'success');
                },
                error: (error) => {
                  console.error('Erreur lors de la mise à jour:', error);
                  Swal.fire('Erreur !', 'Erreur lors de la modification de l\'étudiant', 'error');
                }
              });
          }
        });
      } else {
        // Confirmation de création
        Swal.fire({
          title: 'Confirmation',
          text: 'Voulez-vous créer ce nouvel étudiant ?',
          icon: 'question',
          showCancelButton: true,
          confirmButtonColor: '#34c38f',
          cancelButtonColor: '#f46a6a',
          confirmButtonText: 'Oui, créer !',
          cancelButtonText: 'Non, annuler'
        }).then((result) => {
          if (result.isConfirmed) {
            this.etudiantService.createEtudiant(etudiantData)
              .subscribe({
                next: () => {
                  this.loadEtudiants();
                  this.resetForm();
                  this.modalService.dismissAll();
                  Swal.fire('Succès !', 'Étudiant créé avec succès !', 'success');
                },
                error: (error) => {
                  console.error('Erreur lors de la création:', error);
                  Swal.fire('Erreur !', 'Erreur lors de la création de l\'étudiant', 'error');
                }
              });
          }
        });
      }
    } else {
      Swal.fire('Attention !', 'Veuillez remplir tous les champs obligatoires', 'warning');
    }
  }

  resetForm() {
    this.etudiantForm.reset();
    this.selectedEtudiant = null;
    this.isEditing = false;
  }

  exportToExcel() {
    // Implémenter
  }

  exportToPDF() {
    // Implémenter
  }

  // Méthode pour générer un matricule unique
  private generateMatricule(): string {
    const year = new Date().getFullYear().toString().substr(-2);
    const random = Math.floor(Math.random() * 10000).toString().padStart(4, '0');
    return `ETU${year}${random}`;
  }

  // Méthode pour vérifier si le formulaire est valide
  isFormValid(): boolean {
    const form = this.etudiantForm;
    return form.valid && 
           form.get('nom')?.value?.trim() !== '' &&
           form.get('prenom')?.value?.trim() !== '' &&
           form.get('email')?.value?.trim() !== '' &&
           form.get('telephone')?.value?.trim() !== '' &&
           form.get('dateNaissance')?.value !== '' &&
           form.get('lieuNaissance')?.value?.trim() !== '' &&
           form.get('adresse')?.value?.trim() !== '' &&
           form.get('nationalite')?.value?.trim() !== '' &&
           form.get('genre')?.value !== '' &&
           form.get('classeId')?.value !== '';
  }

  showSuccessModal(message: string) {
    const modalRef = this.modalService.open(this.successModal, {
      centered: true,
      backdrop: 'static'
    });
    this.successMessage = message;
    setTimeout(() => {
      modalRef.close();
    }, 2000);
  }
} 