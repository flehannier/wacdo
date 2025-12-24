import { Routes } from '@angular/router';
import { Collaborateur } from './pages/collaborateur/collaborateur';
import { Restaurant } from './pages/restaurant/restaurant';
import { CollaborateurForm } from './pages/collaborateur/collaborateur-form/collaborateur-form';
import { CollaborateurSearch } from './pages/collaborateur/collaborateur-search/collaborateur-search';

export const routes: Routes = [
    {path: "collaborateurs", component: Collaborateur},
    {path: "restaurants", component: Restaurant},
    {path: "add-collaborateur", component: CollaborateurForm},
    {path: "search-collaborateur", component: CollaborateurSearch},
    {path: "", redirectTo: "collaborateur", pathMatch: "full"},
    {path: "edit-collaborateur/:id", component: CollaborateurForm},
];
