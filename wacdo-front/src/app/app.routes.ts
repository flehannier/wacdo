import { Routes } from '@angular/router';
import { Collaborateur } from './collaborateur/collaborateur';
import { CollaborateurForm } from './collaborateur/collaborateur-form/collaborateur-form';
import { CollaborateurSearch } from './collaborateur/collaborateur-search/collaborateur-search';
import { Restaurant } from './restaurant/restaurant';

export const routes: Routes = [
    {path: "collaborateurs", component: Collaborateur},
    {path: "restaurants", component: Restaurant},
    {path: "add-collaborateur", component: CollaborateurForm},
    {path: "search-collaborateur", component: CollaborateurSearch},
    {path: "", redirectTo: "collaborateur", pathMatch: "full"},
    {path: "edit-collaborateur/:id", component: CollaborateurForm},
];
