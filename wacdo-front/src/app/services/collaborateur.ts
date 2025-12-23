import { Injectable } from '@angular/core';
import { CollaborateurModel } from '../models/collaborateur.model';
import { RestaurantModel } from '../models/restaurant.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { RestaurantWrapper } from '../models/RestaurantWrapper.model';
const httpOption = {
  headers: new HttpHeaders({'Content-type': 'application/json'})
}

@Injectable({
  providedIn: 'root',
})
export class CollaborateurService {
  public collaborateurs!: CollaborateurModel[];
  public restaurants!: RestaurantModel[];

  constructor(private http: HttpClient){
  /*  this.restaurants = [{ 
        id: 1,
        adress: "",
        codePostal: 35470,
        nom: "resto",
        ville: ""
      },{ 
        id: 2,
        adress: "",
        codePostal: 35470,
        nom: "resto 2",
        ville: ""
      }];

    this.collaborateurs = [{
      id: 1, 
      nom: 'Test', 
      prenom :'test', 
      email :'', 
      datePremiereEmbauche: new Date(), 
      estAdmin : true,
      restaurant: this.restaurants[1]
    },{
      id: 2, 
      nom: 'Test 2', 
      prenom :'test 2', 
      email :'', 
      datePremiereEmbauche: new Date(), 
      estAdmin : true,
      restaurant: this.restaurants[2]    
      }]*/
  }

  listCollaborateur(): Observable<CollaborateurModel[]>{
    return this.http.get<CollaborateurModel[]>(environment.apiUrl + "/collaborateur");
   //  return this.collaborateurs;
  }
  
  listRestaurants():  Observable<RestaurantWrapper>{
    return this.http.get<RestaurantWrapper>(environment.restUrl+'/restaurant');
  }
  
 deleteCollaborateur(collaborateur: CollaborateurModel){
  let conf = confirm("est vous sur ?")
  if (!conf) return;
  
    return this.http.delete<CollaborateurModel>(`${environment.apiUrl}/collaborateur/${collaborateur.id}`, httpOption);
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

  addCollaborateur(collaborateur: CollaborateurModel): Observable<CollaborateurModel>{
   // this.collaborateurs.push(collaborateur)
    return this.http.post<CollaborateurModel>(environment.apiUrl + "/collaborateur", collaborateur, httpOption);
  }
  
  updateCollaborateur(collaborateur: CollaborateurModel): Observable<CollaborateurModel>{
  /*const index = this.collaborateurs.indexOf(collaborateur, 0);
     if (index > -1){
      this.collaborateurs.splice(index, 1);
      this.collaborateurs.splice(index, 0, collaborateur);
     }*/
console.log(collaborateur)
    return this.http.put<CollaborateurModel>(environment.apiUrl + "/collaborateur", collaborateur, httpOption);
  }
  
 editRestaurent(id: number): RestaurantModel | undefined{
  return this.restaurants.find(c => c.id === id) ;
 }
  
  searchByRestaurant(id: number): Observable<RestaurantModel[]>
  {
    return this.http.get<RestaurantModel[]>(environment.apiUrl + "/collaborateur/restaurant/" + id);
  }
  
  searchByNom(nom: string): Observable<RestaurantModel[]>
  {
    return this.http.get<RestaurantModel[]>(environment.apiUrl + "/collaborateur/byName/" + nom);
  }

   addRestaurant(restaurant: RestaurantModel): Observable<RestaurantModel>{
    return this.http.post<RestaurantModel>(environment.apiUrlRestaurant, restaurant, httpOption);
  }
}
