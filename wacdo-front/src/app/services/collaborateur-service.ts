import { Injectable } from '@angular/core';
import { CollaborateurModel, CollaborateurRequest } from '../models/collaborateur-model';
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
  
  save(item: CollaborateurRequest): Observable<CollaborateurModel>{
    console.log('save vcollab ' + JSON.stringify(item));
    return this.http.post<CollaborateurModel>(environment.apiUrl + "/collaborateur", item);
  }

  getById(id: number): Observable<CollaborateurModel>{
    return this.http.get<CollaborateurModel>(environment.apiUrl + "/collaborateur/"+id);
  }
  
  getByUsername(username: String | null): Observable<CollaborateurModel>{
     if(null === username) throw new Error("Username null");
   
    return this.http.get<CollaborateurModel>(environment.apiUrl + "/collaborateur/byUsername/"+username);
  }

  listCollaborateurs(): Observable<CollaborateurModel[]>{
    return this.http.get<CollaborateurModel[]>(environment.apiUrl + "/collaborateur");
  }
  
  delete(id: number): Observable<CollaborateurModel> | undefined{
      const isConfirmed = confirm("Est-vous certain de vouloir supprimer le collaborateur");
      if(isConfirmed){
       return this.http.delete<CollaborateurModel>(environment.apiUrl + "/collaborateur/" + id);
      }
      return
  }
}
