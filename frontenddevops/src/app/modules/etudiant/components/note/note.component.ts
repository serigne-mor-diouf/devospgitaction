import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NoteService } from '../../services/note.service';
import { EtudiantService } from '../../services/etudiant.service';
import { CoursService } from '../../services/cours.service';
import { Note } from '../../models/note';
import { ClasseService } from 'src/app/modules/classe/services/classe.service';
import { Cours } from 'src/app/modules/cours/models/cours';

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.scss']
})
export class NoteComponent implements OnInit {
  @Input() notes: Note[] = [];
  @Output() edit = new EventEmitter<Note>();
  @Output() delete = new EventEmitter<Note>();
  @Output() view = new EventEmitter<Note>();

  // Propriétés pour les statistiques
  totalNotes = 0;
  moyenneGenerale = 0;
  tauxReussite = 0;
  notesEnAttente = 0;

  // Propriétés pour les filtres
  selectedSemestre = '';
  selectedCours: any = null;
  selectedClasse: any = null;
  searchTerm = '';
  viewMode = 'list'; // 'list' ou 'grid'

  // Données
  etudiants: any[] = [];
  coursList: any[] = [];
  classes: any[] = [];
  selectedNote: Note | null = null;

  // Formulaire
  noteForm: FormGroup;

  // Pagination
  page = 1;
  pageSize = 10;
  totalItems = 0;

  @ViewChild('bulletinModal') bulletinModal: any;
  @ViewChild('noteModal') noteModal: any;

  isEditing = false;

  constructor(
    private fb: FormBuilder,
    private noteService: NoteService,
    private etudiantService: EtudiantService,
    private coursService: CoursService,
    private classeService: ClasseService,
    private modalService: NgbModal
  ) {
    this.noteForm = this.createForm();
  }

  ngOnInit(): void {
    this.loadInitialData();
  }

  createForm(): FormGroup {
    return this.fb.group({
      etudiantId: ['', Validators.required],
      coursId: ['', Validators.required],
      note: ['', [Validators.required, Validators.min(0), Validators.max(20)]],
      semestre: ['S1', Validators.required],
      dateEvaluation: ['', Validators.required],
      observation: ['']
    });
  }

  loadInitialData() {
    this.loadNotes();
    this.loadCours();
    this.loadClasses();
    this.loadEtudiants();
    this.calculateStatistics();
  }

  loadNotes() {
    this.noteService.getAllNotesPaginees(this.page - 1, this.pageSize).subscribe({
      next: (data) => {
        this.notes = data.content;
        this.totalItems = data.totalElements;
      },
      error: (error: Error) => console.error('Erreur lors du chargement des notes:', error)
    });
  }

  loadCours() {
    if (this.selectedClasse) {
      this.coursService.getCoursByClasse(this.selectedClasse)
        .subscribe({
          next: (cours: Cours[]) => {
            this.coursList = cours;
          },
          error: (error: Error) => console.error('Erreur lors du chargement des cours:', error)
        });
    } else {
      this.coursService.getAllCours()
        .subscribe({
          next: (cours: Cours[]) => {
            this.coursList = cours;
          },
          error: (error: Error) => console.error('Erreur lors du chargement des cours:', error)
        });
    }
  }

  loadClasses() {
    this.classeService.getAllClasses()
      .subscribe({
        next: (classes: any[]) => {
          this.classes = classes;
        },
        error: (error: Error) => console.error('Erreur lors du chargement des classes:', error)
      });
  }

  loadEtudiants() {
    this.etudiantService.getAllEtudiants()
      .subscribe({
        next: (etudiants) => this.etudiants = etudiants,
        error: (error) => console.error('Erreur lors du chargement des étudiants:', error)
      });
  }

  calculateStatistics() {
    if (this.notes.length > 0) {
      this.totalNotes = this.notes.length;
      
      // Calcul de la moyenne générale
      const somme = this.notes.reduce((acc, note) => acc + note.note, 0);
      this.moyenneGenerale = somme / this.totalNotes;
      
      // Calcul du taux de réussite
      const notesReussies = this.notes.filter(note => note.note >= 10).length;
      this.tauxReussite = (notesReussies / this.totalNotes) * 100;
      
      // Calcul des notes en attente
      this.notesEnAttente = this.notes.filter(note => note.statut === 'EN_ATTENTE').length;
    } else {
      this.moyenneGenerale = 0;
      this.tauxReussite = 0;
      this.notesEnAttente = 0;
    }
  }

  filterNotes() {
    const filters = {
      semestre: this.selectedSemestre,
      coursId: this.selectedCours,
      classeId: this.selectedClasse,
      searchTerm: this.searchTerm
    };

    this.noteService.getNotesByFilters(this.page, this.pageSize, filters)
      .subscribe({
        next: (data: any) => {
          this.notes = data.items;
          this.totalItems = data.total;
          this.calculateStatistics();
        },
        error: (error) => console.error('Erreur lors du filtrage des notes:', error)
      });
  }

  onSearch() {
    this.page = 1;
    this.loadNotes();
  }

  resetFilters() {
    this.selectedSemestre = '';
    this.selectedCours = null;
    this.selectedClasse = null;
    this.searchTerm = '';
    this.loadNotes();
  }

  openModal(content: any) {
    this.modalService.open(content, {
      size: 'lg',
      centered: true,
      backdrop: 'static'
    });
  }

  viewDetails(note: Note, content: any) {
    this.selectedNote = note;
    this.modalService.open(content, {
      size: 'lg',
      centered: true
    });
  }

  editNote(note: Note) {
    this.selectedNote = note;
    this.noteForm.patchValue({
      etudiantId: note.etudiantId,
      coursId: note.coursId,
      note: note.note,
      observation: note.observation
    });
    this.isEditing = true;
    this.modalService.open(this.noteModal, {
      size: 'lg',
      centered: true
    });
  }

  deleteNote(note: Note) {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette note ?')) {
      this.noteService.deleteNote(note.id!).subscribe({
        next: () => this.loadNotes(),
        error: (error) => console.error('Erreur lors de la suppression:', error)
      });
    }
  }

  calculateMoyenne(notes: Note[]): number {
    if (!notes || notes.length === 0) return 0;
    const sum = notes.reduce((acc, note) => acc + note.note, 0);
    return sum / notes.length;
  }

  exportToExcel() {
    // Implémenter l'export Excel
  }

  exportToPDF() {
    // Implémenter l'export PDF
  }

  onSubmit() {
    if (this.noteForm.valid) {
      const noteData = this.noteForm.value;
      if (this.selectedNote?.id) {
        this.noteService.updateNote(this.selectedNote.id, noteData).subscribe({
          next: () => {
            this.loadNotes();
            this.modalService.dismissAll();
          },
          error: (error) => console.error('Erreur lors de la mise à jour:', error)
        });
      } else {
        this.noteService.createNote(noteData).subscribe({
          next: () => {
            this.loadNotes();
            this.modalService.dismissAll();
          },
          error: (error) => console.error('Erreur lors de la création:', error)
        });
      }
    }
  }

  // Méthode pour gérer le changement de classe
  onClasseChange() {
    this.selectedCours = null;
    this.loadCours();
    this.filterNotes();
  }

  // Ajout de la méthode pour gérer l'impression du bulletin
  imprimerBulletin(note: Note) {
    // Implémenter la logique d'impression
    console.log('Impression du bulletin pour:', note);
  }
} 