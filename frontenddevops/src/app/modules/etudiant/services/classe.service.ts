import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClasseService {
  private baseUrl = environment.apiUrls.microserviceclasse +'classes';

  constructor(private http: HttpClient) { }

  getAllClasses(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }
} 