import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { Cours, PageCours } from '../models/cours';

@Injectable({
  providedIn: 'root'
})
export class CoursService {
  private apiUrl = environment.apiUrls.microservicecour + 'cours'

  constructor(private http: HttpClient) { }

  // Créer un nouveau cours
  createCours(cours: Cours): Observable<Cours> {
    return this.http.post<Cours>(this.apiUrl, cours);
  }

  // Mettre à jour un cours
  updateCours(cours: Cours): Observable<Cours> {
    return this.http.put<Cours>(`${this.apiUrl}/${cours.id}`, cours);
  }

  // Supprimer un cours
  deleteCours(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Obtenir un cours par ID
  getCoursById(id: number): Observable<Cours> {
    return this.http.get<Cours>(`${this.apiUrl}/${id}`);
  }

  // Obtenir tous les cours
  getAllCours(): Observable<Cours[]> {
    return this.http.get<Cours[]>(this.apiUrl);
  }

  // Obtenir les cours avec pagination
  getAllCoursPagines(page: number, size: number): Observable<any> {
    return this.http.get(`${this.apiUrl}?page=${page}&size=${size}`);
  }

  // Obtenir les cours par professeur
  getCoursByProfesseur(professeurId: number): Observable<Cours[]> {
    return this.http.get<Cours[]>(`${this.apiUrl}/professeur/${professeurId}`);
  }

  // Obtenir les cours par classe
  getCoursByClasse(classeId: number): Observable<Cours[]> {
    return this.http.get<Cours[]>(`${this.apiUrl}/classe/${classeId}`);
  }

  // Obtenir un cours par code
  getCoursByCode(code: string): Observable<Cours> {
    return this.http.get<Cours>(`${this.apiUrl}/code/${code}`);
  }
}
