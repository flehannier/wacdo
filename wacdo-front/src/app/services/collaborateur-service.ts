import { Injectable } from '@angular/core';
import { CollaborateurModel } from '../models/collaborateur-model';
import { RestaurantModel } from '../models/restaurant-model';
import { map, Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class CollaborateurService {
  public collaborateurs!: CollaborateurModel[];
  public restaurants!: RestaurantModel[];

  constructor(private http: HttpClient){
  }

  listCollaborateur(): Observable<CollaborateurModel[]>{
    return this.http.get<CollaborateurModel[]>(environment.apiUrl + "/collaborateur");
  }
}
