import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CoursService } from '../services/cours.service';
import { Router } from '@angular/router';
import { Cours } from '../models/cours';
import { catchError, map } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-dashboard-cours',
  templateUrl: './dashboard-cours.component.html',
  styleUrls: ['./dashboard-cours.component.scss']
})
export class DashboardCoursComponent implements OnInit {
  currentView: 'dashboard' | 'liste' | 'nouveau' | 'detail' = 'dashboard';
  totalCours: number = 0;
  coursEnCours: number = 0;
  coursAVenir: number = 0;
  coursTermines: number = 0;
  recentCours: Cours[] = [];
  filteredCours: Cours[] = [];
  searchTerm: string = '';
  selectedCours: Cours | null = null;
  
  constructor(
    private coursService: CoursService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadDashboardData();
  }

  // Méthodes pour vérifier le statut des cours
  isEnCours(cours: Cours): boolean {
    const now = new Date();
    const debut = new Date(cours.dateDebut);
    const fin = new Date(cours.dateFin);
    return debut <= now && fin >= now;
  }

  isAVenir(cours: Cours): boolean {
    const now = new Date();
    const debut = new Date(cours.dateDebut);
    return debut > now;
  }

  isTermine(cours: Cours): boolean {
    const now = new Date();
    const fin = new Date(cours.dateFin);
    return fin < now;
  }

  getStatus(cours: Cours): string {
    if (this.isEnCours(cours)) return 'En cours';
    if (this.isAVenir(cours)) return 'À venir';
    return 'Terminé';
  }

  getStatusClass(cours: Cours): string {
    if (this.isEnCours(cours)) return 'bg-success';
    if (this.isAVenir(cours)) return 'bg-info';
    return 'bg-secondary';
  }

  switchView(view: 'dashboard' | 'liste' | 'nouveau' | 'detail') {
    this.currentView = view;
    if (view === 'dashboard') {
      this.loadDashboardData();
    }
  }

  loadDashboardData() {
    this.coursService.getAllCoursPagines(0, 10).pipe(
      map(response => {
        // Si la réponse est un tableau direct
        if (Array.isArray(response)) {
          return {
            content: response,
            totalElements: response.length
          };
        }
        // Si la réponse est un objet avec content
        if (response && typeof response === 'object') {
          return {
            content: response.content || [],
            totalElements: response.totalElements || 0
          };
        }
        // Fallback
        return {
          content: [],
          totalElements: 0
        };
      }),
      catchError(error => {
        console.error('Erreur lors du chargement des cours:', error);
        return of({
          content: [],
          totalElements: 0
        });
      })
    ).subscribe(data => {
      this.recentCours = data.content;
      this.filteredCours = this.recentCours;
      this.totalCours = data.totalElements;
      
      const now = new Date();
      
      this.coursEnCours = this.recentCours.filter(cours => 
        new Date(cours.dateDebut) <= now && new Date(cours.dateFin) >= now
      ).length;
      
      this.coursAVenir = this.recentCours.filter(cours => 
        new Date(cours.dateDebut) > now
      ).length;
      
      this.coursTermines = this.recentCours.filter(cours => 
        new Date(cours.dateFin) < now
      ).length;
    });
  }

  filterCours() {
    if (!this.searchTerm.trim()) {
      this.filteredCours = this.recentCours;
      return;
    }

    const search = this.searchTerm.toLowerCase().trim();
    this.filteredCours = this.recentCours.filter(cours => 
      cours.nom.toLowerCase().includes(search) ||
      cours.code.toLowerCase().includes(search)
    );
  }

  openNewCours() {
    this.currentView = 'liste';
    this.router.navigate(['/cours/liste'], { queryParams: { new: true } });
  }

  editCours(cours: Cours) {
    this.currentView = 'liste';
    this.router.navigate(['/cours/liste'], { queryParams: { edit: cours.id } });
  }

  onCoursSelected(cours: Cours) {
    this.selectedCours = cours;
    this.currentView = 'detail';
  }
}
