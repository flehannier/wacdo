import { Injectable } from '@angular/core';
import { CollaborateurModel } from '../models/collaborateur.model';
import { RestaurantModel } from '../models/restaurant.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { RestaurantWrapper } from '../models/RestaurantWrapper.model';
import { AuthService } from './auth-service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class CollaborateurService {
  private collaborateurs!: CollaborateurModel[];
  private restaurants!: RestaurantModel[];

  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) { }

  private authHeaders(): HttpHeaders {
    if (!this.authService.getToken()) {
      this.router.navigate(["/login"]);
    }
    return new HttpHeaders({
      Authorization: 'Bearer ' + this.authService.getToken()
    });
  }

  listCollaborateur(): Observable<CollaborateurModel[]> {
    return this.http.get<CollaborateurModel[]>(environment.apiUrl + "/collaborateur", { headers: this.authHeaders() });
    //  return this.collaborateurs;
  }

  listRestaurants(): Observable<RestaurantModel[]> {
    return this.http.get<RestaurantModel[]>(environment.apiUrl + '/restaurant', { headers: this.authHeaders() });
  }

  deleteCollaborateur(collaborateur: CollaborateurModel) {
    let conf = confirm("est vous sur ?")
    if (!conf) return;

    return this.http.delete<CollaborateurModel>(`${environment.apiUrl}/collaborateur/${collaborateur.id}`, { headers: this.authHeaders() });
    /*const index = this.collaborateurs.indexOf(collaborateur, 0);
       if (index > -1){
        this.collaborateurs.splice(index, 1);
       }*/
  }

  editCollaborateur(id: number): Observable<CollaborateurModel | undefined> {
    return this.listCollaborateur().pipe(
      map(collaborateurs =>
        collaborateurs.find(c => c.id === id)
      )
    );
  }

  addCollaborateur(collaborateur: CollaborateurModel): Observable<CollaborateurModel> {
    // this.collaborateurs.push(collaborateur)
    return this.http.post<CollaborateurModel>(environment.apiUrl + "/collaborateur", collaborateur, { headers: this.authHeaders() });
  }

  updateCollaborateur(collaborateur: CollaborateurModel): Observable<CollaborateurModel> {
    /*const index = this.collaborateurs.indexOf(collaborateur, 0);
       if (index > -1){
        this.collaborateurs.splice(index, 1);
        this.collaborateurs.splice(index, 0, collaborateur);
       }*/
    console.log(collaborateur)
    return this.http.put<CollaborateurModel>(environment.apiUrl + "/collaborateur", collaborateur, { headers: this.authHeaders() });
  }

  editRestaurent(id: number): RestaurantModel | undefined {
    return this.restaurants.find(c => c.id === id);
  }

  searchByRestaurant(id: number): Observable<RestaurantModel[]> {
    return this.http.get<RestaurantModel[]>(environment.apiUrl + "/collaborateur/restaurant/" + id);
  }

  searchByNom(nom: string): Observable<RestaurantModel[]> {
    return this.http.get<RestaurantModel[]>(environment.apiUrl + "/collaborateur/byName/" + nom);
  }

  addRestaurant(restaurant: RestaurantModel): Observable<RestaurantModel> {
    return this.http.post<RestaurantModel>(environment.apiUrlRestaurant, restaurant, { headers: this.authHeaders() });
  }
}
