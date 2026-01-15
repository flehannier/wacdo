import { Injectable } from '@angular/core';
import { AuthService } from './auth-service';
import { ROLES } from '../components/constants/roles';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private auth: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const currentUrl = this.router.url;
    const token = this.auth.getToken(); 
    const role = this.auth.getRole();

    if (!token || !role) {
      this.router.navigate(['/login']);
      return false;
    }

   
  // 2️⃣ Redirection selon le rôle
  switch (role) {
    case ROLES.USER:
      // Bloque l'accès si l'utilisateur n'est pas admin
      if (currentUrl !== '/login') {
        // On bloque les utilisateurs avec le rôle USER
        this.router.navigate(['/login'], { 
          state: { error: "Votre rôle ne vous permet pas de poursuivre." } 
        });
      }
      return false;

    case ROLES.ADMIN:
      return true;

    default:
      // Si rôle inconnu ou autre, on refuse l'accès
      return false;
  }
  }
}
