import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Inscription } from '../models/inscription';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InscriptionService {
  private baseUrl = environment.apiUrls.microserviceetudiant;
  private apiUrl = `${this.baseUrl}inscriptions`;
  //private anneeUrl = `${this.apiUrl}/annee-academique`;

  constructor(private http: HttpClient) { }

  // Méthodes CRUD de base
  createInscription(inscription: Inscription): Observable<Inscription> {
    return this.http.post<Inscription>(this.apiUrl, inscription);
  }

  updateInscription(id: number, inscription: Inscription): Observable<Inscription> {
    return this.http.put<Inscription>(`${this.apiUrl}/${id}`, inscription);
  }

  deleteInscription(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getInscription(id: number): Observable<Inscription> {
    return this.http.get<Inscription>(`${this.apiUrl}/${id}`);
  }

  // Méthode pour la pagination et la recherche
  getInscriptions(page: number, pageSize: number, searchTerm?: string): Observable<any> {
    let url = `${this.apiUrl}/paginees?page=${page}&size=${pageSize}`;
    if (searchTerm) {
      url += `&search=${searchTerm}`;
    }
    return this.http.get<any>(url);
  }

  // Méthodes spécifiques
  getInscriptionsByEtudiant(etudiantId: number): Observable<Inscription[]> {
    return this.http.get<Inscription[]>(`${this.apiUrl}/etudiant/${etudiantId}`);
  }

  getInscriptionsByAnneeAcademique(anneeId: number): Observable<Inscription[]> {
    return this.http.get<Inscription[]>(`${this.apiUrl}/annee-academique/${anneeId}`);
  }

  getAllInscriptionsPaginees(page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/paginees?page=${page}&size=${size}`);
  }

  calculateSolde(etudiantId: number, anneeAcademiqueId: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/solde?etudiantId=${etudiantId}&anneeAcademiqueId=${anneeAcademiqueId}`);
  }

  searchInscriptions(searchTerm: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/search?term=${searchTerm}`);
  }

  getInscriptionDetails(id: number): Observable<Inscription> {
    return this.http.get<Inscription>(`${this.apiUrl}/${id}/details`);
  }

  exportToExcel(): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/export/excel`, { responseType: 'blob' });
  }

  exportToPDF(): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/export/pdf`, { responseType: 'blob' });
  }

  getAnneesAcademiques(id: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${id}`);
  }


} 