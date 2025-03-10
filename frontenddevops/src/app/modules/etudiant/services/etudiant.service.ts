import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Etudiant } from '../models/etudiant';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EtudiantService {
  private apiUrl = environment.apiUrls.microserviceetudiant + 'etudiants'

  constructor(private http: HttpClient) { }

  createEtudiant(etudiant: Etudiant): Observable<Etudiant> {
    return this.http.post<Etudiant>(this.apiUrl, etudiant);
  }

  updateEtudiant(id: number, etudiant: Etudiant): Observable<Etudiant> {
    return this.http.put<Etudiant>(`${this.apiUrl}/${id}`, etudiant);
  }

  deleteEtudiant(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getEtudiant(id: number): Observable<Etudiant> {
    return this.http.get<Etudiant>(`${this.apiUrl}/${id}`);
  }

  getEtudiantByMatricule(matricule: string): Observable<Etudiant> {
    return this.http.get<Etudiant>(`${this.apiUrl}/matricule/${matricule}`);
  }

  getAllEtudiants(): Observable<Etudiant[]> {
    return this.http.get<Etudiant[]>(this.apiUrl);
  }

  getAllEtudiantsPagines(page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/pagines?page=${page}&size=${size}`);
  }

  getEtudiantsByClasse(classeId: number): Observable<Etudiant[]> {
    return this.http.get<Etudiant[]>(`${this.apiUrl}/classe/${classeId}`);
  }

  searchEtudiants(keyword: string): Observable<Etudiant[]> {
    return this.http.get<Etudiant[]>(`${this.apiUrl}/search?keyword=${keyword}`);
  }

  getClasses(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/classes`);
  }

  getEtudiants(page: number, pageSize: number, searchTerm?: string): Observable<any> {
    let url = `${this.apiUrl}/pagines?page=${page}&size=${pageSize}`;
    if (searchTerm) {
      url += `&search=${searchTerm}`;
    }
    return this.http.get<any>(url);
  }

  getStats(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}`);
  }

  exportToExcel(): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/export/excel`, { responseType: 'blob' });
  }

  exportToPDF(): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/export/pdf`, { responseType: 'blob' });
  }
}
