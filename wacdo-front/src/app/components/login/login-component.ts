import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import { UserModel, UserWithoutRoleAndToken } from '../../models/user-model';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login-component.html',
  styleUrl: './login-component.css',
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errors?: string;

  constructor(private authService: AuthService, private router: Router, private route: ActivatedRoute, private formBuilder: FormBuilder) {

  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.nonNullable.group({
      email: ['', [Validators.required, Validators.email]],
      motDePasse: ['', [Validators.required]],
    })

     this.route.queryParams.subscribe(params => {
      if (params['error'] === 'unauthorized') {
        this.errors = 'Votre rôle ne vous permet pas de poursuivre.';

        // Nettoyage de l’URL
        this.router.navigate([], {
          relativeTo: this.route,
          queryParams: {},
          replaceUrl: true
        });
      }
    });
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }
    const formValue = this.loginForm.getRawValue();
    const user: UserWithoutRoleAndToken = {
      username: formValue.email,
      motDePasse: formValue.motDePasse
    };

    this.authService.login(user).subscribe({
      next: (data) => {
        let jwt = (data.body as UserModel).accesToken;
        console.log('Token : ' + jwt);

        if (jwt) {
          this.authService.saveToken(jwt, (data.body as UserModel).username, (data.body as UserModel).role);
          this.router.navigate(["/"]);
        }
      },
      error: (data) => {
        this.errors = data.error.error || data.message;
      }
    });
  }

  onReset() {
    this.loginForm.reset();
  }
}
