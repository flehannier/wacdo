import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../models/user.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private isLoggedIn = false;
  constructor(private router: Router, private http: HttpClient) {
  }

  login(user: User) {
    return this.http.post<User>(environment.apiUrl + "/auth/login", user, { observe: 'response' });
  }

  saveToken(token: string) {
    localStorage.setItem("token", token);
    this.isLoggedIn = true;
  }

  getToken() {
    return localStorage.getItem("token") || null;
  }

  logout() {
    localStorage.removeItem("token");
    this.isLoggedIn = false;
    this.router.navigate(["/login"]);
  }
}
