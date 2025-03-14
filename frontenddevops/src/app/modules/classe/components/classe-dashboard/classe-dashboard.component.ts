import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { ClasseService } from '../../services/classe.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
Chart.register(...registerables);

@Component({
  selector: 'app-classe-dashboard',
  templateUrl: './classe-dashboard.component.html',
  //styleUrls: ['./classe-dashboard.component.scss']
})
export class ClasseDashboardComponent implements OnInit, AfterViewInit {
  @ViewChild('niveauChart') niveauChart!: ElementRef;
  @ViewChild('effectifsChart') effectifsChart!: ElementRef;

  // Propriétés pour la recherche et le filtrage
  searchTerm = '';
  selectedNiveau = '';
  selectedFiliere = '';
  
  // Données
  classes: any[] = [];
  niveaux = ['Licence 1', 'Licence 2', 'Licence 3', 'Master 1', 'Master 2'];
  filieres: string[] = [];

  // Pagination
  page = 1;
  pageSize = 10;
  totalItems = 0;

  // Stats
  totalClasses = 0;
  totalEtudiants = 0;
  moyenneEtudiantsParClasse = 0;
  totalFilieres = 0;

  menuItems = [
    { icon: 'fas fa-tachometer-alt', label: 'Tableau de bord', route: '/classes' },
    { icon: 'fas fa-list', label: 'Liste des classes', route: '/classes/liste' },
    { icon: 'fas fa-plus-circle', label: 'Nouvelle classe', route: '/classes/nouvelle' },
    { icon: 'fas fa-chart-bar', label: 'Statistiques', route: '/classes/stats' },
    { icon: 'fas fa-cog', label: 'Paramètres', route: '/classes/parametres' }
  ];

  classeForm!: FormGroup;

  constructor(
    private classeService: ClasseService,
    private modalService: NgbModal,
    private fb: FormBuilder
  ) {
    this.initForm();
  }

  ngOnInit() {
    this.loadStats();
    this.loadClasses();
  }

  ngAfterViewInit() {
    this.initCharts();
  }

  // Méthodes de recherche et filtrage
  onSearch() {
    this.loadClasses();
  }

  filterClasses() {
    this.loadClasses();
  }

  // Méthodes de chargement des données
  loadClasses() {
    this.classeService.getAllClassesPaginees(this.page - 1, this.pageSize)
      .subscribe(response => {
        this.classes = response.content;
        this.totalItems = response.totalElements;
      });
  }

  // Méthodes d'export
  exportData(format: 'excel' | 'pdf') {
    // Implémenter l'export
    console.log(`Exporting to ${format}`);
  }

  // Méthodes de gestion des graphiques
  refreshChart(chartType: string) {
    if (chartType === 'niveau') {
      this.initNiveauChart();
    } else {
      this.initEffectifsChart();
    }
  }

  // Méthodes de gestion des classes
  viewClasse(classe: any) {
    // Implémenter l'affichage
  }

  editClasse(classe: any) {
    // Implémenter l'édition
  }

  deleteClasse(classe: any) {
    // Implémenter la suppression
  }

  // Méthode de pagination
  onPageChange() {
    this.loadClasses();
  }

  loadStats() {
    this.classeService.getStats().subscribe(stats => {
      this.totalClasses = stats.totalClasses;
      this.totalEtudiants = stats.totalEtudiants;
      this.moyenneEtudiantsParClasse = stats.moyenneEtudiantsParClasse;
      this.totalFilieres = stats.totalFilieres;
    });
  }

  initCharts() {
    this.initNiveauChart();
    this.initEffectifsChart();
  }

  private initNiveauChart() {
    new Chart(this.niveauChart.nativeElement, {
      type: 'doughnut',
      data: {
        labels: ['Licence 1', 'Licence 2', 'Licence 3', 'Master 1', 'Master 2'],
        datasets: [{
          data: [30, 25, 20, 15, 10],
          backgroundColor: [
            '#4CAF50',
            '#2196F3',
            '#FF9800',
            '#9C27B0',
            '#F44336'
          ]
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'bottom'
          }
        }
      }
    });
  }

  private initEffectifsChart() {
    new Chart(this.effectifsChart.nativeElement, {
      type: 'line',
      data: {
        labels: ['Sept', 'Oct', 'Nov', 'Dec', 'Jan', 'Fev'],
        datasets: [{
          label: 'Effectifs',
          data: [320, 330, 345, 350, 355, 360],
          borderColor: '#2196F3',
          tension: 0.4,
          fill: true,
          backgroundColor: 'rgba(33, 150, 243, 0.1)'
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'bottom'
          }
        },
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }

  openModal(content?: any) {
    this.modalService.open(content, {
      size: 'lg',
      centered: true,
      backdrop: 'static'
    });
  }

  private initForm() {
    this.classeForm = this.fb.group({
      code: ['', Validators.required],
      nom: ['', Validators.required],
      niveau: ['', Validators.required],
      filiere: ['', Validators.required],
      capaciteMax: ['', [Validators.required, Validators.min(1)]]
    });
  }

  openNewClasseModal(content: any) {
    this.classeForm.reset();
    this.modalService.open(content, {
      size: 'lg',
      centered: true,
      backdrop: 'static'
    });
  }

  saveClasse() {
    if (this.classeForm.valid) {
      this.classeService.createClasse(this.classeForm.value).subscribe({
        next: () => {
          this.modalService.dismissAll();
          this.loadClasses();
          // Ajouter une notification de succès
        },
        error: (error) => {
          console.error('Erreur lors de la création:', error);
          // Ajouter une notification d'erreur
        }
      });
    }
  }
} 