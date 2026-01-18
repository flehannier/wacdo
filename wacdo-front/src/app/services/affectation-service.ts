import { Injectable } from '@angular/core';
import { AffectationModel } from '../models/affectation-model';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class AffectationService {
  public affectations!: AffectationModel[];
  
  constructor(private http: HttpClient){
  }

  listAffectations(): Observable<AffectationModel[]>{
    return this.http.get<AffectationModel[]>(environment.apiUrl + "/affectation");
  }

  delete(id: number): Observable<AffectationModel> | undefined{
    const isConfirmed = confirm("Est-vous certain de vouloir supprimer l'affectation");
    if(isConfirmed){
     return this.http.delete<AffectationModel>(environment.apiUrl + "/affectation/" + id);
    }
    return
  }
}
