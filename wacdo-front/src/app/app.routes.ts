import { Routes } from '@angular/router';
import { Restaurant } from './components/restaurant/restaurant';
import { CollaborateurForm } from './components/collaborateur/collaborateur-form/collaborateur-form';
import { CollaborateurSearch } from './components/collaborateur/collaborateur-search/collaborateur-search';
import { Collaborateur } from './components/collaborateur/collaborateur';
import { Login } from './components/login/login';

export const routes: Routes = [
    { path: "collaborateurs", component: Collaborateur },
    { path: "restaurants", component: Restaurant },
    { path: "add-collaborateur", component: CollaborateurForm },
    { path: "search-collaborateur", component: CollaborateurSearch },
    { path: "", redirectTo: "collaborateur", pathMatch: "full" },
    { path: "edit-collaborateur/:id", component: CollaborateurForm },
    { path: "login", component: Login },
];
