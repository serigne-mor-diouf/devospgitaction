import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-workspace',
  templateUrl: './workspace.component.html',
  styleUrls: ['./workspace.component.scss']
})
export class WorkspaceComponent {
  modules = [
    {
      title: 'Gestion des Classes',
      icon: 'fas fa-school',
      route: '/classes',
      color: '#4CAF50',
      description: 'Gérer les classes, effectifs et niveaux',
      features: [
        'Organisation des classes par niveau',
        'Suivi des effectifs en temps réel',
        'Attribution des professeurs principaux',
        'Gestion des emplois du temps par classe'
      ]
    },
    {
      title: 'Gestion des Cours',
      icon: 'fas fa-book',
      route: '/cours',
      color: '#2196F3',
      description: 'Planifier et gérer les cours',
      features: [
        'Création et planification des cours',
        'Attribution des enseignants',
        'Suivi des progressions pédagogiques',
        'Gestion des supports de cours'
      ]
    },
    {
      title: 'Gestion des Professeurs',
      icon: 'fas fa-chalkboard-teacher',
      route: '/professeurs',
      color: '#FF9800',
      description: 'Gérer les enseignants et leurs attributions',
      features: [
        'Gestion des emplois du temps',
        'Suivi des heures d\'enseignement',
        'Attribution des matières',
        'Planning des disponibilités'
      ]
    },
    {
      title: 'Gestion des Étudiants',
      icon: 'fas fa-user-graduate',
      route: '/etudiants',
      color: '#9C27B0',
      description: 'Suivre les étudiants et leurs parcours',
      features: [
        'Inscription et suivi des étudiants',
        'Gestion des notes et absences',
        'Suivi des parcours académiques',
        'Communication avec les étudiants'
      ]
    },
    {
      title: 'Emploi du Temps',
      icon: 'fas fa-calendar-alt',
      route: '/emploi-du-temps',
      color: '#E91E63',
      description: 'Planifier les horaires et salles',
      features: [
        'Planning hebdomadaire interactif',
        'Gestion des salles et ressources',
        'Détection des conflits horaires',
        'Vue par classe/professeur/salle'
      ]
    }
  ];

  constructor(private router: Router) {}

  navigateToModule(route: string) {
    this.router.navigate([route]);
  }
} 