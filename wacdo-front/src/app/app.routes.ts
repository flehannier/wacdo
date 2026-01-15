import { AccueilComponent } from './components/accueil-component/accueil-component';
import { LoginComponent } from './components/login/login-component';
import { Routes } from '@angular/router';
import { AuthGuard } from './services/auth-guard-service';

export const routes: Routes = [
    { path: "login", component: LoginComponent },
    { path: '', component: AccueilComponent, canActivate: [AuthGuard] },    
    { path: '**', redirectTo: '' }
];
