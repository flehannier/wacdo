import { AccueilComponent } from './components/accueil-component/accueil-component';
import { LoginComponent } from './components/login/login-component';
import { Routes } from '@angular/router';
import { AuthGuard } from './services/auth-guard-service';
import { CollaborateurComponent } from './components/collaborateur-component/collaborateur-component';

export const routes: Routes = [
    { path: "login", component: LoginComponent },
    { path: 'collaborateur', component: CollaborateurComponent, canActivate: [AuthGuard] },    
    { path: '', component: AccueilComponent, canActivate: [AuthGuard] },    
    { path: '**', redirectTo: '' }
];
