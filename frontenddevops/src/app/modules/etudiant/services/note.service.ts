import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Note } from '../models/note';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NoteService {
  private baseUrl = environment.apiUrls.microserviceetudiant;
  private apiUrl = `${this.baseUrl}/notes`;

  constructor(private http: HttpClient) { }

  createNote(note: Note): Observable<Note> {
    return this.http.post<Note>(this.apiUrl, note);
  }

  updateNote(id: number, note: Note): Observable<Note> {
    return this.http.put<Note>(`${this.apiUrl}/${id}`, note);
  }

  deleteNote(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getNote(id: number): Observable<Note> {
    return this.http.get<Note>(`${this.apiUrl}/${id}`);
  }

  getNotesByEtudiant(etudiantId: number): Observable<Note[]> {
    return this.http.get<Note[]>(`${this.apiUrl}/etudiant/${etudiantId}`);
  }

  getNotesByCours(coursId: number): Observable<Note[]> {
    return this.http.get<Note[]>(`${this.apiUrl}/cours/${coursId}`);
  }

  getNotesByEtudiantAndSemestre(etudiantId: number, semestre: string): Observable<Note[]> {
    return this.http.get<Note[]>(`${this.apiUrl}/etudiant/${etudiantId}/semestre/${semestre}`);
  }

  calculateMoyenne(etudiantId: number, semestre: string): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/moyenne?etudiantId=${etudiantId}&semestre=${semestre}`);
  }

  getAllNotesPaginees(page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/paginees?page=${page}&size=${size}`);
  }

  searchNotes(searchTerm: string) {
    return this.http.get<any>(`${this.apiUrl}/notes/search?term=${searchTerm}`);
  }

  getNotes(page: number, pageSize: number, searchTerm?: string): Observable<any> {
    let url = `${this.apiUrl}/paginees?page=${page}&size=${pageSize}`;
    if (searchTerm) {
      url += `&search=${searchTerm}`;
    }
    return this.http.get<any>(url);
  }

  getNotesByFilters(page: number, pageSize: number, filters: any): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', pageSize.toString())
      .set('semestre', filters.semestre || '')
      .set('coursId', filters.coursId || '')
      .set('classeId', filters.classeId || '')
      .set('searchTerm', filters.searchTerm || '');

    return this.http.get<any>(`${this.apiUrl}/filter`, { params });
  }

  exportToExcel(): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/export/excel`, { responseType: 'blob' });
  }

  exportToPDF(): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/export/pdf`, { responseType: 'blob' });
  }
} 