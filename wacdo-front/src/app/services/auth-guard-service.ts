import { Injectable } from '@angular/core';
import { AuthService } from './auth-service';
import { Role } from '../constants/roles';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private auth: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const token = this.auth.getToken();
    const role = this.auth.getRole();

    if (!token || !role) {
      return false;
    }

    switch (role) {
      case Role.USER:
        // Bloque l'accès si l'utilisateur n'est pas admin
        // On bloque les utilisateurs avec le rôle USER
        this.router.navigate(['/login'], {
          queryParams: {
            error: 'unauthorized'
          }
        });
      return false;

      case Role.ADMIN:
      return true;

      default:
        // Si rôle inconnu ou autre, on refuse l'accès
      return false;
    }
  }
}
