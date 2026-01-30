import { Injectable } from '@angular/core';
import { RestaurantModel } from '../models/restaurant-model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class RestaurantService {
  
  public fonctions!: RestaurantModel[];

  constructor(private http: HttpClient){
  }

  listRestaurants(): Observable<RestaurantModel[]>{
    return this.http.get<RestaurantModel[]>(environment.apiUrl + "/restaurant");
  }
  
  save(item: RestaurantModel): Observable<RestaurantModel>{
    return this.http.post<RestaurantModel>(environment.apiUrl + "/restaurant", item);
  }
}
