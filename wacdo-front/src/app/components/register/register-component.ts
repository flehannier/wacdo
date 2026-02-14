import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CollaborateurService } from '../../services/collaborateur.service';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: 'register-component.html',
  imports: [ReactiveFormsModule, CommonModule],
  styleUrls: ['register-component.css']
})
export class RegisterComponent implements OnInit {

  registerForm!: FormGroup<{
    email: FormControl<string>;
    motDePasse: FormControl<string>;
    confirmMotDePasse: FormControl<string>;
    nom: FormControl<string>;
    prenom: FormControl<string>;
  }>;

  isLoading = false;
  errorMessage: string  | ValidationErrors | null = null;
  successMessage: string | null = null;
  submitted = false;
  isLoadingActivities = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private collaborateurSrvice: CollaborateurService,
  ) {
      this.registerForm = this.fb.group({
        email: this.fb.control<string>('', [Validators.required, Validators.email]),
        motDePasse: this.fb.control<string>('', [Validators.required, Validators.minLength(8)]),
        confirmMotDePasse: this.fb.control<string>('', [Validators.required]),
        nom: this.fb.control<string>('', [Validators.required]),
        prenom: this.fb.control<string>('', [Validators.required]),
      }, { validators: this.passwordMatchValidator });
  }

  ngOnInit(): void {
    // Debug: écouter les changements du formulaire pour voir pourquoi il est invalide
    this.registerForm.statusChanges.subscribe(status => {
      console.log('Statut du formulaire:', status);
      if (status === 'INVALID') {
        console.log('Formulaire invalide. Erreurs:', this.registerForm.errors);
        Object.keys(this.registerForm.controls).forEach(key => {
          const control = this.registerForm.get(key);
          if (control && control.invalid) {
            console.log(`Champ ${key} invalide:`, control.errors);
          }
        });
      }
    });
  }


  passwordMatchValidator(form: FormGroup) {
    const motDePasse = form.get('motDePasse');
    const confirmMotDePasse = form.get('confirmMotDePasse');

    if (!motDePasse || !confirmMotDePasse) {
      return false;
    }

    const motDePasseValue = motDePasse.value;
    const confirmMotDePasseValue = confirmMotDePasse.value;

    // Ne valider que si les deux champs ont une valeur (pas vides, pas null, pas undefined)
    if (motDePasseValue && confirmMotDePasseValue &&
        motDePasseValue.trim() !== '' && confirmMotDePasseValue.trim() !== '') {
      if (motDePasseValue !== confirmMotDePasseValue) {
        // Les mots de passe ne correspondent pas - retourner l'erreur au niveau du formulaire
        return { motDePasseMismatch: true };
      }
    }

    // Si les mots de passe correspondent ou si l'un des champs est vide, pas d'erreur
    return null;
  }

  onSubmit(): void {
    this.submitted = true;
    this.errorMessage = null;
    this.successMessage = null;

    if (this.registerForm.invalid) {
      this.errorMessage = "Formulaire invalide"
      
      return;
    }


    const formValue = this.registerForm.value;
    const data: any = {
      nom: formValue.nom,
      prenom: formValue.prenom,
      email: formValue.email,
      motDePasse: formValue.motDePasse,
    };


    this.collaborateurSrvice.register(data).subscribe({
      next: (response: any) => {
        const message = response?.message || 'Votre demande d\'inscription a été enregistrée. Elle sera examinée par un administrateur.';
        this.successMessage = "Votre inscription à bien été pris en compte."

        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 3000);
      },
      error: (error) => {
        this.isLoading = false;

        if (error.error) {
          if (error.error.message) {
            this.errorMessage = error.error.message;
          } else if (typeof error.error === 'string') {
            this.errorMessage = error.error;
          } else {
            this.errorMessage = 'Une erreur est survenue lors de la création du compte.';
          }
        } else if (error.status === 409 || error.status === 400) {
          this.errorMessage = 'Un utilisateur avec ce nom d\'utilisateur existe déjà.';
        } else if (error.status === 0) {
          this.errorMessage = 'Impossible de se connecter au serveur. Vérifiez votre connexion.';
        } else if (error.status === 500) {
          this.errorMessage = 'Erreur serveur. Veuillez contacter l\'administrateur.';
        } else {
          this.errorMessage = `Une erreur est survenue (${error.status || 'inconnue'}). Veuillez réessayer.`;
        }
      }
    });
  }

  get f() {
    return this.registerForm.controls;
  }
  
  goToLogin(): void {
    this.router.navigate(['/login']);
  }
}
