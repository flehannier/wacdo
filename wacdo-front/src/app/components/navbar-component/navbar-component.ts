import { Component } from '@angular/core';
import { Route, Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { Role } from '../constants/roles';

@Component({
  standalone: true,
  selector: 'app-navbar-component',
  imports: [RouterLink],
  templateUrl: './navbar-component.html',
  styleUrl: './navbar-component.css',
})
export class NavbarComponent {

  constructor(private authService: AuthService, private router: Router){
  }

  logout(){
    this.authService.logout();
    this.router.navigate(["/login"]);
  }
}
