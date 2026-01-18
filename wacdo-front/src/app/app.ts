import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './components/navbar-component/navbar-component';
import { AuthService } from './services/auth-service';
import { CommonModule } from '@angular/common';
import { Role } from './constants/roles';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

  protected readonly title = signal('wacdo-front');

  constructor(public authService: AuthService) {
  }

  isAllowed(){
    return this.authService.getRole() === Role.ADMIN
  }
}
