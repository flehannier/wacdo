import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { UserWithoutRoleAndToken } from '../models/user-model';
import { environment } from '../../environments/environment';
import { Role} from '../constants/roles';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  userIsLogged: boolean;

  constructor(private router: Router, private http: HttpClient) {
    this.userIsLogged = !!this.getToken() && this.getRole() === Role.ADMIN;
  }

  login(user: UserWithoutRoleAndToken) {
    return this.http.post<UserWithoutRoleAndToken>(environment.apiUrl + "/auth/login", user, { observe: 'response' });
  }

  saveToken(token: string, role?: string) {
    if(!role || !token) {
      this.userIsLogged = false;
      return;
    }

    localStorage.setItem("token", token);
    localStorage.setItem("role", role);
    
    this.userIsLogged = true;
  }

  getToken() {
    return localStorage.getItem("token") || null;
  }

  getRole() {
    return localStorage.getItem("role") || null;
  }

  logout() {    
    localStorage.removeItem("token");
    localStorage.removeItem("role");
     this.userIsLogged = false;

    this.router.navigate(["/login"]);
  }
}
