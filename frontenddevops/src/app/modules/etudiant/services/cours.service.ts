import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CoursService {
  private apiUrl = `${environment.apiUrls.microservicecour}cours`;

  constructor(private http: HttpClient) { }

  getAllCours(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getCoursById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  getCoursByClasse(classeId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/classe/${classeId}`);
  }

  createCours(cours: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, cours);
  }

  updateCours(id: number, cours: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, cours);
  }

  deleteCours(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
} 