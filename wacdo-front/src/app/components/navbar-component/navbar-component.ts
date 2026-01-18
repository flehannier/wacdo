import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../services/auth-service';

@Component({
  standalone: true,
  selector: 'app-navbar-component',
  imports: [RouterLink, RouterLinkActive],
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
