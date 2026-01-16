import { HttpHeaders, HttpInterceptorFn, HttpStatusCode } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from './auth-service';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(AuthService);
  const router = inject(Router);
  const token = auth.getToken()

  if (!token) { 
    return next(req)
  }

  const headers = new HttpHeaders({
    Authorization: "Bearer " + token
  })

  const newReq = req.clone({
    headers
  })

  return next(newReq).pipe(
        catchError((err: any) => {
            if (err.status === HttpStatusCode.Unauthorized || err.status === HttpStatusCode.Forbidden) {
                // Token invalide ou expiré ou role interdit → redirection vers login
                auth.logout();
                router.navigate(['/login'], { state: { error: err.error.error } });
            }
            return throwError(() => err);
        })
    )
};
