import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Professeur, ProfesseurDTO } from '../models/professeur';

@Injectable({
  providedIn: 'root'
})
export class ProfesseurService {
  private apiUrl = environment.apiUrls.microserviceprofesseur + 'professeurs';
  constructor(private http: HttpClient) { }

  getAllProfesseurs(): Observable<Professeur[]> {
    return this.http.get<Professeur[]>(this.apiUrl);
  }

  getProfesseurById(id: number): Observable<Professeur> {
    return this.http.get<Professeur>(`${this.apiUrl}/${id}`);
  }

  createProfesseur(professeur: ProfesseurDTO): Observable<Professeur> {
    return this.http.post<Professeur>(this.apiUrl, professeur);
  }

  updateProfesseur(id: number, professeur: ProfesseurDTO): Observable<Professeur> {
    return this.http.put<Professeur>(`${this.apiUrl}/${id}`, professeur);
  }

  deleteProfesseur(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getProfesseursByDepartement(departementId: number): Observable<Professeur[]> {
    return this.http.get<Professeur[]>(`${this.apiUrl}/departement/${departementId}`);
  }

  getProfesseursBySpecialite(specialiteId: number): Observable<Professeur[]> {
    return this.http.get<Professeur[]>(`${this.apiUrl}/specialite/${specialiteId}`);
  }
}