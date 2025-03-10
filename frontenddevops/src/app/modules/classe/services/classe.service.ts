import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Classe, ClasseDTO, ClasseResponse } from '../models/classe';

@Injectable({
  providedIn: 'root'
})
export class ClasseService {
  private apiUrl = environment.apiUrls.microserviceclasse + 'classes';

  constructor(private http: HttpClient) { }

  getAllClasses(): Observable<Classe[]> {
    return this.http.get<Classe[]>(this.apiUrl);
  }

  getAllClassesPaginees(page: number, size: number): Observable<ClasseResponse> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<ClasseResponse>(`${this.apiUrl}/paginees`, { params });
  }

  getClasseById(id: number): Observable<Classe> {
    return this.http.get<Classe>(`${this.apiUrl}/${id}`);
  }

  getClasseByCode(code: string): Observable<Classe> {
    return this.http.get<Classe>(`${this.apiUrl}/code/${code}`);
  }

  createClasse(classe: ClasseDTO): Observable<Classe> {
    return this.http.post<Classe>(this.apiUrl, classe);
  }

  updateClasse(id: number, classe: ClasseDTO): Observable<Classe> {
    return this.http.put<Classe>(`${this.apiUrl}/${id}`, classe);
  }

  deleteClasse(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getClassesByNiveau(niveau: string): Observable<Classe[]> {
    return this.http.get<Classe[]>(`${this.apiUrl}/niveau/${niveau}`);
  }

  getClassesByFiliere(filiere: string): Observable<Classe[]> {
    return this.http.get<Classe[]>(`${this.apiUrl}/filiere/${filiere}`);
  }

  getClassesByAnneeScolaire(anneeScolaire: string): Observable<Classe[]> {
    return this.http.get<Classe[]>(`${this.apiUrl}/annee-scolaire/${anneeScolaire}`);
  }

  getClassesByResponsable(responsableId: number): Observable<Classe[]> {
    return this.http.get<Classe[]>(`${this.apiUrl}/responsable/${responsableId}`);
  }

  getStats(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}`);
  }
}