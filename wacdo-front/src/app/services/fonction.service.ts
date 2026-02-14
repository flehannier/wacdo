import { Injectable } from '@angular/core';
import { FonctionModel } from '../models/fonction-model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class FonctionService {
  
  public fonctions!: FonctionModel[];

  constructor(private http: HttpClient){
  }

  listFonctions(): Observable<FonctionModel[]>{
    return this.http.get<FonctionModel[]>(environment.apiUrl + "/fonction");
  }
  
  save(item: FonctionModel): Observable<FonctionModel>{
    return this.http.post<FonctionModel>(environment.apiUrl + "/fonction", item);
  }
  
  delete(id: number): Observable<FonctionModel> | undefined{
      const isConfirmed = confirm("Etes-vous certain de vouloir supprimer la fonction");
      if(isConfirmed){
        return this.http.delete<FonctionModel>(environment.apiUrl + "/fonction/" + id);
      }
      return
  }
}
