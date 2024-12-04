import {Component, inject} from '@angular/core';
import {FormBuilder, ReactiveFormsModule} from '@angular/forms';
import {AuthService, LoginInt} from '../../../services/auth/auth.service';
import {Router, RouterLink} from '@angular/router';
import {NgClass} from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink,
    NgClass
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  formBuilder = inject(FormBuilder);
  authService = inject(AuthService);
  router = inject(Router);

  loginForm = this.formBuilder.group({
    email: [''],
    password: ['']
  });

  login() {

    const loginData: LoginInt = this.loginForm.value as LoginInt;


    this.authService.login(loginData).subscribe((res: LoggedInUserSession) => {

        this.authService.userToken.set(res.token);
        const expiryTime = new Date().getTime() + res.expiresIn;
        sessionStorage.setItem('tokenExpiryPeriod', expiryTime.toString());
        this.router.navigateByUrl('/dashboard');
        },
      error => {
        console.log('Login error', error);
      })
  }
}

export interface LoggedInUserSession {
  token: string;
  expiresIn: number;
}
