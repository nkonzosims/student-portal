import { HttpInterceptorFn } from '@angular/common/http';
import {AuthService} from '../services/auth/auth.service';
import {inject} from '@angular/core';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const authService = inject(AuthService);
  const token = authService.userToken();

  const excludeUrls = /\/api\/auth/;

  if (token && !excludeUrls.test(req.url)) {

    console.log('Adding token to request', token);
    const cloned = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });
    return next(cloned);
  } else {
    return next(req);
  }
};
