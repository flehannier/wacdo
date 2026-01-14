import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { User } from '../../models/user.model';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login implements OnInit {

  loginForm!: FormGroup;
  errors?: string;

  constructor(private authService: AuthService, private router: Router, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.nonNullable.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
    })
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }
    const formValue = this.loginForm.getRawValue();
    const user: User = {
      email: formValue.username,
      motDePasse: formValue.password
    };

    this.authService.login(user).subscribe({
      next: (data) => {
        let jwt = data.body?.token;
        if (jwt) {
          this.authService.saveToken(jwt);
          this.router.navigate(["/collaborateurs"]);
        }
      },
      error: (data) => {
        console.log(data);
        this.errors = data.error.error;
      }
    });
  }

  onReset() {
    this.loginForm.reset();
  }
}
