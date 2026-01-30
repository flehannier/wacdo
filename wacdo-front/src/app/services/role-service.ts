import { Injectable } from '@angular/core';
import { RestaurantModel } from '../models/restaurant-model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { RoleModel } from '../models/role-model';

@Injectable({
  providedIn: 'root',
})
export class RoleService {
  
  public role!: RoleModel[];

  constructor(private http: HttpClient){
  }

  listRoles(): Observable<RoleModel[]>{
    return this.http.get<RoleModel[]>(environment.apiUrl + "/role");
  }
}
