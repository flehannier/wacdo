import { Component, signal } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { Binding } from "./binding/binding";
import { CollaborateurModel } from './models/collaborateur.model';
import { CollaborateurService } from './services/collaborateur';
import { Collaborateur } from "./collaborateur/collaborateur";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('wacdo-front');
}
