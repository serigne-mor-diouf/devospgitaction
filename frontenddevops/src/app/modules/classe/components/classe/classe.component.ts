import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ClasseService } from '../../services/classe.service';
import { Classe, ClasseDTO } from '../../models/classe';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-classe',
  templateUrl: './classe.component.html',
  styleUrls: ['./classe.component.scss']
})
export class ClasseComponent implements OnInit {
  classes: Classe[] = [];
  classeForm: FormGroup;
  selectedClasse: Classe | null = null;
  searchTerm = '';
  page = 1;
  pageSize = 10;
  totalItems = 0;
  isEditing = false;

  // Statistiques
  totalClasses = 0;
  totalEtudiants = 0;
  moyenneEtudiantsParClasse = 0;
  totalFilieres = 0;

  @ViewChild('classeModal') classeModal: any;

  constructor(
    private fb: FormBuilder,
    private classeService: ClasseService,
    private modalService: NgbModal
  ) {
    this.classeForm = this.createForm();
  }

  ngOnInit(): void {
    this.chargerClasses();
  }

  createForm(): FormGroup {
    return this.fb.group({
      code: ['', [Validators.required, Validators.minLength(3)]],
      nom: ['', [Validators.required, Validators.minLength(3)]],
      niveau: ['', Validators.required],
      filiere: ['', Validators.required],
      capaciteMax: ['', [Validators.required, Validators.min(1)]],
      anneeScolaire: ['', Validators.required],
      responsableId: [''],
      description: ['']
    });
  }

  /**
   * Charge toutes les classes avec pagination
   */
  chargerClasses() {
    this.classeService.getAllClassesPaginees(this.page - 1, this.pageSize)
      .subscribe({
        next: (response) => {
          this.classes = response.content;
          this.totalItems = response.totalElements;
          this.calculerStatistiques();
        },
        error: (error) => {
          console.error('Erreur:', error);
          Swal.fire('Erreur!', 'Impossible de charger les classes', 'error');
        }
      });
  }

  /**
   * Calcule les statistiques pour le dashboard
   */
  calculerStatistiques() {
    this.totalClasses = this.classes.length;
    const filieres = new Set(this.classes.map(c => c.filiere));
    this.totalFilieres = filieres.size;
    
    // Calcul du total d'étudiants et moyenne par classe
    let totalEtudiants = 0;
    this.classes.forEach(classe => {
      totalEtudiants += classe.capaciteMax;
    });
    this.totalEtudiants = totalEtudiants;
    this.moyenneEtudiantsParClasse = this.totalClasses ? Math.round(totalEtudiants / this.totalClasses) : 0;
  }

  /**
   * Recherche des classes
   */
  rechercherClasses() {
    if (this.searchTerm.trim()) {
      this.classes = this.classes.filter(classe => 
        classe.nom.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        classe.code.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        classe.filiere.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    } else {
      this.chargerClasses();
    }
  }

  /**
   * Supprime une classe
   */
  supprimerClasse(classe: Classe) {
    Swal.fire({
      title: 'Confirmation',
      text: `Voulez-vous vraiment supprimer la classe ${classe.nom} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui, supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.classeService.deleteClasse(classe.id).subscribe({
          next: () => {
            this.chargerClasses();
            Swal.fire('Succès!', 'Classe supprimée avec succès', 'success');
          },
          error: (error) => {
            console.error('Erreur:', error);
            Swal.fire('Erreur!', 'Impossible de supprimer la classe', 'error');
          }
        });
      }
    });
  }

  /**
   * Modifie une classe existante
   */
  modifierClasse(classe: Classe) {
    this.selectedClasse = classe;
    this.classeForm.patchValue({
      code: classe.code,
      nom: classe.nom,
      niveau: classe.niveau,
      filiere: classe.filiere,
      capaciteMax: classe.capaciteMax,
      anneeScolaire: classe.anneeScolaire,
      responsableId: classe.responsableId,
      description: classe.description
    });
    this.isEditing = true;
    this.modalService.open(this.classeModal, { size: 'lg' });
  }

  /**
   * Exporte les données en Excel
   */
  exporterExcel() {
    // Implémenter l'export Excel
  }

  /**
   * Exporte les données en PDF
   */
  exporterPDF() {
    // Implémenter l'export PDF
  }

  onSubmit() {
    if (this.classeForm.valid) {
      const classeData: ClasseDTO = this.classeForm.value;

      if (this.selectedClasse?.id) {
        Swal.fire({
          title: 'Confirmation',
          text: 'Voulez-vous modifier cette classe ?',
          icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#34c38f',
          cancelButtonColor: '#f46a6a',
          confirmButtonText: 'Oui, modifier !',
          cancelButtonText: 'Annuler'
        }).then((result) => {
          if (result.isConfirmed) {
            this.classeService.updateClasse(this.selectedClasse!.id, classeData)
              .subscribe({
                next: () => {
                  this.chargerClasses();
                  this.resetForm();
                  this.modalService.dismissAll();
                  Swal.fire('Succès !', 'Classe modifiée avec succès !', 'success');
                },
                error: (error) => {
                  console.error('Erreur lors de la modification:', error);
                  Swal.fire('Erreur !', 'Erreur lors de la modification de la classe', 'error');
                }
              });
          }
        });
      } else {
        Swal.fire({
          title: 'Confirmation',
          text: 'Voulez-vous créer cette classe ?',
          icon: 'question',
          showCancelButton: true,
          confirmButtonColor: '#34c38f',
          cancelButtonColor: '#f46a6a',
          confirmButtonText: 'Oui, créer !',
          cancelButtonText: 'Annuler'
        }).then((result) => {
          if (result.isConfirmed) {
            this.classeService.createClasse(classeData)
              .subscribe({
                next: () => {
                  this.chargerClasses();
                  this.resetForm();
                  this.modalService.dismissAll();
                  Swal.fire('Succès !', 'Classe créée avec succès !', 'success');
                },
                error: (error) => {
                  console.error('Erreur lors de la création:', error);
                  Swal.fire('Erreur !', 'Erreur lors de la création de la classe', 'error');
                }
              });
          }
        });
      }
    }
  }

  resetForm() {
    this.classeForm.reset();
    this.selectedClasse = null;
    this.isEditing = false;
  }

  /**
   * Ouvre le modal pour ajouter ou modifier une classe
   */
  openModal(content: any) {
    this.resetForm(); // Reset the form before opening the modal
    this.modalService.open(content, { size: 'lg', centered: true });
  }

  // Autres méthodes...
} 