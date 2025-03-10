import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CoursService } from '../services/cours.service';
import { Cours } from '../models/cours';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cours-detail',
  templateUrl: './cours-detail.component.html',
  styleUrls: ['./cours-detail.component.scss']
})
export class CoursDetailComponent implements OnInit {
  @Input() cours!: Cours;
  loading = true;
  error = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private coursService: CoursService
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadCours(parseInt(id));
    } else {
      this.error = true;
      this.loading = false;
      this.showError('Erreur', 'ID du cours non trouvé');
    }
  }

  loadCours(id: number) {
    this.loading = true;
    this.coursService.getCoursById(id).subscribe({
      next: (data) => {
        this.cours = data;
        this.loading = false;
      },
      error: (error) => {
        this.error = true;
        this.loading = false;
        this.showError('Erreur', 'Impossible de charger les détails du cours');
      }
    });
  }

  onEdit() {
    if (!this.cours?.id) return;
    
    this.router.navigate(['/cours/liste'], { 
      queryParams: { edit: this.cours.id }
    });
  }

  onDelete() {
    if (!this.cours) return;

    Swal.fire({
      title: 'Êtes-vous sûr ?',
      text: "Cette action est irréversible !",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Oui, supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.coursService.deleteCours(this.cours.id).subscribe({
            next: () => {
            this.showSuccess('Succès', 'Le cours a été supprimé');
            this.router.navigate(['/cours/liste']);
          },
          error: () => {
            this.showError('Erreur', 'Impossible de supprimer le cours');
          }
        });
      }
    });
  }

  getStatus(cours: Cours): string {
    const now = new Date();
    const debut = new Date(cours.dateDebut);
    const fin = new Date(cours.dateFin);

    if (now < debut) return 'À venir';
    if (now > fin) return 'Terminé';
    return 'En cours';
  }

  private showSuccess(title: string, message: string) {
    Swal.fire(title, message, 'success');
  }

  private showError(title: string, message: string) {
    Swal.fire(title, message, 'error');
  }
} 