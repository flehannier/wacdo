import { AccueilComponent } from './components/accueil-component/accueil-component';
import { LoginComponent } from './components/login/login-component';
import { Routes } from '@angular/router';
import { AuthGuard } from './services/auth.guard.service';
import { CollaborateurComponent } from './components/collaborateur-component/collaborateur-component';
import { FonctionComponent } from './components/fonction-component/fonction-component';
import { AffectationComponent } from './components/affectation-component/affectation-component';
import { RestaurantComponent } from './components/restaurant-component/restaurant-component';
import { ProfileComponent } from './components/profile-component/profile-component';
import { RegisterComponent } from './components/register/register-component';

export const routes: Routes = [
    { path: "login", component: LoginComponent },
    { path: 'collaborateur', component: CollaborateurComponent, canActivate: [AuthGuard] },
    { path: 'restaurant', component: RestaurantComponent, canActivate: [AuthGuard] },
    { path: 'fonction', component: FonctionComponent, canActivate: [AuthGuard] },
    { path: 'affectation', component: AffectationComponent, canActivate: [AuthGuard] },
    { path: 'accueil', component: AccueilComponent, canActivate: [AuthGuard] },
    { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
    { path: 'register', component: RegisterComponent},
    { path: '**', redirectTo: 'accueil' }
];
