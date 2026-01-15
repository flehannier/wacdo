import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { UserModel, UserWithoutRoleAndToken } from '../../models/user-model';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login-component.html',
  styleUrl: './login-component.css',
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errors?: string;

  constructor(private authService: AuthService, private router: Router, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.nonNullable.group({
      email: ['', [Validators.required]],
      motDePasse: ['', [Validators.required]],
    })

    this.errors = history.state?.error || null;
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }
    const formValue = this.loginForm.getRawValue();
    const user: UserWithoutRoleAndToken = {
      email: formValue.email,
      motDePasse: formValue.motDePasse
    };

    this.authService.login(user).subscribe({
      next: (data) => {
        let jwt = (data.body as UserModel).accesToken;
        console.log('Token : ' + jwt);

        if (jwt) {
          this.authService.saveToken(jwt, (data.body as UserModel).role);
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
